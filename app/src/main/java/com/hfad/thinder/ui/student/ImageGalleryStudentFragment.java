package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.ImageGalleryFragment;
import com.hfad.thinder.viewmodels.student.LikedThesisDetailedViewModel;

/**
 * Extension of the {@link ImageGalleryFragment}. Needed to get the {@link LikedThesisDetailedViewModel}.
 */
public class ImageGalleryStudentFragment extends ImageGalleryFragment {

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        LikedThesisDetailedViewModel viewModel = new ViewModelProvider(requireActivity()).get(LikedThesisDetailedViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return view;
    }
}
