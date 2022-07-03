package com.hfad.thinder.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.hfad.thinder.R;

import com.hfad.thinder.viewmodels.VerifyTokenStates;

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

    @BindingAdapter("setVerifyTokenBackgroundColor")
    public static void setVerifyTokenBackgroundColor(View view, VerifyTokenStates state) {
        int color = R.color.grey_400;
        switch (state) {
            case IDLE:
                color = R.color.grey_400;
                break;
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

    @BindingAdapter("setVerifyTokenBackgroundImage")
    public static void setVerifyTokenBackgroundImage(ImageView imageView, VerifyTokenStates state) {
        int image = R.drawable.ic_cancel;
        switch (state) {
            case IDLE:
                break;
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

    @BindingAdapter("setVerifyTokenText")
    public static void setVerifyTokenText(TextView textView, VerifyTokenStates state) {
        int text = R.string.verification_failure;
        switch (state) {
            case IDLE:
                text = R.string.look_at_mail;
                break;
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
