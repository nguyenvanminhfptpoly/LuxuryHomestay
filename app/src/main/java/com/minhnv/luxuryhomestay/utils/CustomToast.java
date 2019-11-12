package com.minhnv.luxuryhomestay.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.minhnv.luxuryhomestay.R;


public class CustomToast extends Toast {
    public static int SUCCESS = 0;
    public static int ERROR = 1;
    public static int WAITING = 2;


    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeTake(Context context, String message, int duration, int type){
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast,null, false);
        TextView textView = view.findViewById(R.id.tvMessageToast);
        ImageView imageView = view.findViewById(R.id.imgToast);
        ConstraintLayout constraintLayout = view.findViewById(R.id.bgToast);
        textView.setText(message);
        if(type == 0){
            constraintLayout.setBackgroundResource(R.drawable.toast_success);
            imageView.setImageResource(R.drawable.ic_success);
        }else if(type == 1){
            constraintLayout.setBackgroundResource(R.drawable.toast_error);
            imageView.setImageResource(R.drawable.ic_error);
        }else if(type == 2){
            constraintLayout.setBackgroundResource(R.drawable.toast_wait);
            imageView.setImageResource(R.drawable.ic_wait);
        }
        toast.setView(view);
        return toast;
    }

}
