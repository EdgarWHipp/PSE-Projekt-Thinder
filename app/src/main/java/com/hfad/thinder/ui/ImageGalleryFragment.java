package com.hfad.thinder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentImageGalleryBinding;

/**
 * Abstract Fragment class that handles binding and layout inflation for ImageGalleries
 */
public abstract class ImageGalleryFragment extends Fragment {

    protected FragmentImageGalleryBinding binding;

    /**
     * Required empty public constructor
     */
    public ImageGalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Acts as a constructor
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_gallery, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }
}