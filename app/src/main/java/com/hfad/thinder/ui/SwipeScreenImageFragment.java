package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenImageBinding;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeScreenImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeScreenImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSwipeScreenImageBinding binding;
    private View view;
    private SwipeScreenViewModel viewModel;

    private ImageView imageView;

    public SwipeScreenImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwipeScreenImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwipeScreenImageFragment newInstance(String param1, String param2) {
        SwipeScreenImageFragment fragment = new SwipeScreenImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen_image, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        view = binding.getRoot();

        imageView = binding.ivImage;

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        imageView.setImageBitmap(viewModel.getCurrentDetailViewImage());
    }
}