package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentImageGalleryBinding;
import com.hfad.thinder.viewmodels.student.ImageGalleryViewModel;
import com.hfad.thinder.viewmodels.user.ForgotPasswordViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageGalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageGalleryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentImageGalleryBinding binding;
    private ImageGalleryViewModel viewmodel;

    public ImageGalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageGalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageGalleryFragment newInstance(String param1, String param2) {
        ImageGalleryFragment fragment = new ImageGalleryFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_gallery, container, false);
        viewmodel = new ViewModelProvider(this).get(ImageGalleryViewModel.class);
        viewmodel.setThesisId(requireArguments().getString("thesisId"));
        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }
}