package com.minhnv.luxuryhomestay.ui.main.social.story;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
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

public class PostStoryActivity extends BaseActivity<PostStoryViewModel> implements PostStoryNavigator {
    private static final String TAG = "PostStoryActivity";
    private ImageView imgStoryPost;
    private EditText edStoryPost;
    private Button btnPostStory;
    private SlidrInterface slide;
    private int RESULT_CODE = 12;
    String realPath = "";
    private File file;

    public static Intent newIntent(Context context) {
        return new Intent(context, PostStoryActivity.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        setContentView(R.layout.activity_post_story);
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(this, factory).get(PostStoryViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbarStory);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Đăng tin của bạn");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        imgStoryPost = findViewById(R.id.imgStoryPost);
        btnPostStory = findViewById(R.id.btnPostStory);
        edStoryPost = findViewById(R.id.includeTitleStoryPost).findViewById(R.id.edValue);
        uploadImage();
        btnPostStory.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
            }
            showLoading();
            postStory();
        });
    }

    private void uploadImage() {
        imgStoryPost.setOnClickListener(img -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, RESULT_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgStoryPost.setImageBitmap(bitmap);
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

    private void postStory() {
        String title = edStoryPost.getText().toString().trim();

        file = new File(realPath);
        if (title.isEmpty() || file.length() == 0) {
            hideLoading();
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        } else {
            String filePath = file.getAbsolutePath();
            String[] nameImage = filePath.split("\\.");

            filePath = nameImage[0] + System.currentTimeMillis() + "." + nameImage[0];

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            try {
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filePath, requestBody);

                DataClient dataClient = ApiUtils.getData();
                Call<String> call = dataClient.UploadPhot(body);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                        String message = response.body();
                        assert message != null;
                        if (message.length() > 0) {
                            DataClient postStory = ApiUtils.getData();
                            String img = ApiUtils.baseUrl + "image/" + message;
                            Call<String> callb = postStory.postStory(title, img);
                            callb.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                                    String result = response.body();
                                    assert result != null;
                                    if (result.equals("Success")) {
                                        hideLoading();
                                        CustomToast.makeTake(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG, CustomToast.SUCCESS).show();
                                    } else if (result.equals("Failed")) {
                                        CustomToast.makeTake(getApplicationContext(), "Thêm không thành công", Toast.LENGTH_LONG, CustomToast.ERROR).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                                    AppLogger.d(TAG, t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                        AppLogger.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            } catch (IllegalArgumentException e) {
                e.fillInStackTrace();
            }
        }

    }

}
