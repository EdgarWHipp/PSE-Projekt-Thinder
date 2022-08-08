package com.hfad.thinder.ui.supervisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.ImageGalleryFragment;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

public class ImageGalleryEditThesisFragment extends ImageGalleryFragment {
    EditThesisViewModel viewmodel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        binding.setViewModel(viewmodel);

        return view;
    }
}