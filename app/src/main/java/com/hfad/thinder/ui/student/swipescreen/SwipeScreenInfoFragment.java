package com.hfad.thinder.ui.student.swipescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 *  Showing general information about the thesis and supervisor
 */
public class SwipeScreenInfoFragment extends Fragment {

    /**
     * Required empty constructor
     */
    public SwipeScreenInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.hfad.thinder.databinding.FragmentSwipeScreenInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen_info, container, false);
        SwipeScreenViewModel viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}