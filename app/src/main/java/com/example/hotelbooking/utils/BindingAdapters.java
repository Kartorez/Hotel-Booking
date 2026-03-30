package com.example.hotelbooking.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView view, int resId) {
        if (resId != 0) {
            view.setImageResource(resId);
        }
    }
}