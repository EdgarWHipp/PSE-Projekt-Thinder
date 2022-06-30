package com.hfad.thinder.util;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.databinding.BindingAdapter;

import com.hfad.thinder.R;

import com.hfad.thinder.viewmodels.ConfirmPasswordStates;

public class BindingAdapters {
    /**
     * Given a view and a boolean the function sets the visibility of the view according to the given boolean
     *
     * @param view
     * @param visible
     */
    @BindingAdapter({"android:goneUnless"})
    public static void goneUnless(View view, Boolean visible) {
        view.setVisibility((visible ? View.VISIBLE : View.GONE));
    }

    @BindingAdapter("setConfirmPasswordBackgroundColor")
    public static void setConfirmPasswordBackgroundColor(View view, ConfirmPasswordStates state) {
        int color = R.color.grey_400;
        switch (state) {
            case LOADING:
                color = R.color.grey_400;
                break;
            case FAILURE:
                color = R.color.red_500;
                break;
            case SUCCESSFUL:
                color = R.color.green_300;
        }
        view.setBackgroundResource(color);
    }

    @BindingAdapter("setConfirmPasswordBackgroundImage")
    public static void setConfirmPasswordBackgroundImage(ImageView imageView, ConfirmPasswordStates state) {
        int image = R.drawable.ic_cancel;
        switch (state) {
            case LOADING:
                break;
            case FAILURE:
                image = R.drawable.ic_cancel;
                break;
            case SUCCESSFUL:
                image = R.drawable.ic_check;
        }
        imageView.setBackgroundResource(image);
    }

    @BindingAdapter("setConfirmPasswordText")
    public static void setConfirmPasswordText(TextView textView, ConfirmPasswordStates state) {
        int text = R.string.verification_failure;
        switch (state) {
            case LOADING:
                text = R.string.verification_ongoing;
                break;
            case FAILURE:
                text = R.string.verification_failure;
                break;
            case SUCCESSFUL:
                text = R.string.verification_successful;
        }
        textView.setText(text);
    }
}
