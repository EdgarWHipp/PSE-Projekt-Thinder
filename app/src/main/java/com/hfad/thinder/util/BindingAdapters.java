package com.hfad.thinder.util;

import android.view.View;

import androidx.databinding.BindingAdapter;

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
}
