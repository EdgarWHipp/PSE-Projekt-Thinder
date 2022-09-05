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
import com.hfad.thinder.ui.student.LikedThesesFragment;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 * Top fragment seen in the swipe deck. First fragment in the detail view.
 */
public class SwipeScreenTopFragment extends Fragment {

    private Boolean isCardOne = true;

    /**
     * Required empty constructor
     */
    public SwipeScreenTopFragment() {
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

        com.hfad.thinder.databinding.FragmentSwipeScreenTopBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen_top, container, false);
        View view = binding.getRoot();
        SwipeScreenViewModel viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return view;
    }

    /**
     * Extracts information from bundle and sets {@link SwipeScreenTopFragment#isCardOne} accordingly
     *
     * @param view                  view returned by {@link LikedThesesFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isCardOne = bundle.getBoolean("isCardOne");
        }
    }

    /**
     *
     * @return {@link Boolean} whether it is card one or card two
     */
    public boolean isCardOne() {
        return isCardOne;
    }
}