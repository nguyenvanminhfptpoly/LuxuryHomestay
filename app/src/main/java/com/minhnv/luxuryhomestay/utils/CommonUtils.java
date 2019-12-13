package com.minhnv.luxuryhomestay.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;

import androidx.appcompat.app.AlertDialog;

import com.minhnv.luxuryhomestay.R;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    public static AlertDialog.Builder createDialog(Context context, String title, String message, String confirm, String cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(confirm, (dialogInterface, i) -> {

        });
        builder.setNegativeButton(cancel, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder;
    }


    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static int parseIntSafely(String content, int defaultValue) {
        if (content == null) return defaultValue;
        try {
            return Integer.parseInt(content);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }



    public static String stripNonDigits(
            final CharSequence input /* inspired by seh's comment */){
        final StringBuilder sb = new StringBuilder(
                input.length() /* also inspired by seh's comment */);
        for(int i = 0; i < input.length(); i++){
            final char c = input.charAt(i);
            if(c > 47 && c < 58){
                sb.append(c);
            }
        }
        return sb.toString();
    }



}
