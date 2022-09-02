package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenTextBinding;
import com.hfad.thinder.viewmodels.student.EditProfileViewModel;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeScreenTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeScreenTextFragment extends Fragment {

    /**
     * Required empty constructor
     */
    public SwipeScreenTextFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSwipeScreenTextBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen_text, container, false);
        SwipeScreenViewModel viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}