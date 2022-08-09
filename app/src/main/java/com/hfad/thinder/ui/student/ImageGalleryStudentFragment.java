package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.ImageGalleryFragment;
import com.hfad.thinder.viewmodels.student.LikedThesisDetailedViewModel;
import com.hfad.thinder.viewmodels.supervisor.NewThesisViewModel;

public class ImageGalleryStudentFragment extends ImageGalleryFragment {
    LikedThesisDetailedViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LikedThesisDetailedViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return view;
    }
}
