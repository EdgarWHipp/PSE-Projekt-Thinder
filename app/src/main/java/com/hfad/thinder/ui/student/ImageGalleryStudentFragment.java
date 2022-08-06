package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.ImageGalleryFragment;
import com.hfad.thinder.viewmodels.supervisor.NewThesisViewModel;

public class ImageGalleryStudentFragment extends ImageGalleryFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(NewThesisViewModel.class);
        binding.setViewModel(viewmodel);

        return view;
    }
}
