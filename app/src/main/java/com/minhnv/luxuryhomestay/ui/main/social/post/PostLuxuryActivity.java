package com.minhnv.luxuryhomestay.ui.main.social.post;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.remote.DataClient;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.utils.ApiUtils;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostLuxuryActivity extends BaseActivity<PostLuxuryViewModel> implements PostLuxuryNavigator {
    private static final String TAG = "PostLuxuryActivity";
    private ImageView imgUploadImage;
    private int Image = 123;
    String realPath = "", username, address, detail;
    EditText edAddressHs, edUsername, edDetailHs;
    private SlidrInterface slide;
    File fileLuxury;
    public static Intent newIntent(Context context) {
        return new Intent(context, PostLuxuryActivity.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        setContentView(R.layout.activity_post_luxury);
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(this, factory).get(PostLuxuryViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarPostLuxury);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Đăng Bài");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        imgUploadImage = findViewById(R.id.imgUploadImage);
        edAddressHs = findViewById(R.id.includeAddress).findViewById(R.id.edValue);
        edUsername = findViewById(R.id.includeTitleStoryPost).findViewById(R.id.edValue);
        edDetailHs = findViewById(R.id.includeDetail).findViewById(R.id.edValue);
        edUsername.setText(appPreferenceHelper.getCurrentAddress());
        uploadImage();

        Button btnPostLuxury = findViewById(R.id.btnPostStory);

        btnPostLuxury.setOnClickListener(view -> {
            if(SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                return;
            }
            showLoading();
            postLuxury();
        });
    }


    private void uploadImage() {
        imgUploadImage.setOnClickListener(img -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, Image);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUploadImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


    private void postLuxury() {
        username = edUsername.getText().toString().trim();
        address = edAddressHs.getText().toString().trim();
        detail = edDetailHs.getText().toString().trim();

         fileLuxury = new File(realPath);
        if(username.isEmpty() || address.isEmpty() || detail.isEmpty() || fileLuxury.length() == 0) {
            hideLoading();
            Toast.makeText(this, getString(R.string.validate_booking), Toast.LENGTH_SHORT).show();
        }else {
            String fileLuxuryAbsolutePath = fileLuxury.getAbsolutePath();
            String[] split = fileLuxuryAbsolutePath.split("\\.");
            fileLuxuryAbsolutePath = split[0] + System.currentTimeMillis() + "." + split[0];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileLuxury);

            MultipartBody.Part uploadedFile = MultipartBody.Part.createFormData("uploaded_file", fileLuxuryAbsolutePath, requestBody);

            DataClient dataClient = ApiUtils.getData();
            Call<String> call = dataClient.UploadPhot(uploadedFile);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                    String body = response.body();
                    assert body != null;
                    if (body.length() > 0) {
                        DataClient insertData = ApiUtils.getData();
                        Call<String> callb = insertData.insertData(ApiUtils.baseUrl + "image/" + body, detail, username, address);
                        callb.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                                String result = response.body();
                                assert result != null;
                                if (result.equals("Success")) {
                                    hideLoading();
                                    CustomToast.makeTake(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG,CustomToast.SUCCESS).show();
                                }else if(result.equals("Failed")){
                                    hideLoading();
                                    CustomToast.makeTake(getApplicationContext(),"Thêm không thành công",Toast.LENGTH_LONG,CustomToast.ERROR).show();
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                                if (!isNetworkConnected()) {
                                    backToLogin();
                                }
                                hideLoading();
                                AppLogger.d(TAG, "onFailure: "+t.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    AppLogger.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }
}
