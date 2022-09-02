package com.hfad.thinder.ui.supervisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.ui.CoursesOfStudyFragment;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;
import com.hfad.thinder.viewmodels.supervisor.NewThesisViewModel;

/**
 * Extension of the {@link CoursesOfStudyFragment}. Needed to get the {@link EditThesisViewModel}.
 */
public class CoursesOfStudyNewThesisFragment extends CoursesOfStudyFragment {
    /**
     * Creates and returns the view hierarchy associated with the fragment. Gets the correct ViewModel.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewThesisViewModel.class);
        return view;
    }
}
