package com.minhnv.luxuryhomestay.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.minhnv.luxuryhomestay.R;

import java.text.DecimalFormat;

public class CommonUtils {

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static boolean isPhoneValid(String phone){
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static String FormatCredits(Double credit ){
        DecimalFormat format = new DecimalFormat("#,### đ/đêm");
        String price = format.format(credit);
        return price;
    }





}
