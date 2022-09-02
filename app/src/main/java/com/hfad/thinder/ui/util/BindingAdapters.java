package com.hfad.thinder.ui.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.user.VerifyTokenStates;

/**
 * Binding adapters allow the programmer to access a view's methods from within an xml layout file
 */
public class BindingAdapters {
    /**
     * Given a view and a boolean the function sets the visibility of the view according to the given boolean
     *
     * @param view      view to change visibility of
     * @param visible   true = visible | false = invisible
     */
    @BindingAdapter({"android:goneUnless"})
    public static void goneUnless(View view, Boolean visible) {
        view.setVisibility((visible ? View.VISIBLE : View.GONE));
    }

    /**
     * Given an {@link ImageView} and a {@link Bitmap} sets the Bitmap to the ImageView
     *
     * @param iv        ImageView to set Bitmap to
     * @param bitmap    Bitmap to be displayed
     */
    @BindingAdapter("imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    /**
     * Given an {@link Integer} resource String id and a {@link TextView} displays the String
     * corresponding to the given resource String id in the TextView. Used for translating the app
     *
     * @param textView  converted String value is displayed here
     * @param resource  resource String id pointing ot the correct String (dependent on the language set)
     */
    @BindingAdapter("setResourceText")
    public static void setResourceText(TextView textView, Integer resource){
        if(resource != null)
            textView.setText(resource);
    }

    // Verify token Binding Adapters
    /**
     *  Given a {@link VerifyTokenStates} state and a {@link View} object sets the background color
     *  of the view to according to the state
     *
     * @param view  object to set the background color
     * @param state state that determines the color
     */
    @BindingAdapter("setVerifyTokenBackgroundColor")
    public static void setVerifyTokenBackgroundColor(View view, VerifyTokenStates state) {
        int color = R.color.grey_400;
        switch (state) {
            case FAILURE:
                color = R.color.red_500;
                break;
            case SUCCESSFUL:
                color = R.color.green_300;
        }
        view.setBackgroundResource(color);
    }

    /**
     * Given an {@link ImageView} and a {@link VerifyTokenStates} state sets the image of the ImageView
     * according to the current VerifyTokenState
     *
     * @param imageView ImageView to set image to
     * @param state     Current VerifyTokenState
     */
    @BindingAdapter("setVerifyTokenBackgroundImage")
    public static void setVerifyTokenBackgroundImage(ImageView imageView, VerifyTokenStates state) {
        int image = R.drawable.ic_cancel;
        switch (state) {
            case IDLE:
            case LOADING:
                break;
            case SUCCESSFUL:
                image = R.drawable.ic_check;
        }
        imageView.setBackgroundResource(image);
    }

    /**
     * Given a {@link VerifyTokenStates} state and a {@link TextView} sets the text of the TextView
     * according to the current VerifyTokenState
     *
     * @param textView  TextView to set text to
     * @param state     Current VerifyTokenState
     */
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
