package com.hfad.thinder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentNewThesisBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewThesisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewThesisFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button studyOfCoursesButton;
    private Button imagePickerButton;
    private FragmentNewThesisBinding binding;

    public NewThesisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewThesisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewThesisFragment newInstance(String param1, String param2) {
        NewThesisFragment fragment = new NewThesisFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_thesis, container, false);
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        studyOfCoursesButton = binding.btPickCoursesOfStudy;
        studyOfCoursesButton.setOnClickListener(this);
        imagePickerButton = binding.btAddImages;
        imagePickerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btPickCoursesOfStudy:
                Navigation.findNavController(view).navigate(R.id.action_newThesisFragment_to_coursesOfStudyFragment);
                break;
            case R.id.btAddImages:
                ImagePicker.with(this)
                        .crop(4f,3f)
                        .compress(1024)
                        .galleryOnly()
                        .start();
                break;
        }

    }
}