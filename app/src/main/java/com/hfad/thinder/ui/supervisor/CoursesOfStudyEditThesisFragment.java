package com.hfad.thinder.ui.supervisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.CoursesOfStudyFragment;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

public class CoursesOfStudyEditThesisFragment extends CoursesOfStudyFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        return view;
    }
}
