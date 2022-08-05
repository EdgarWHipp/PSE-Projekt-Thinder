package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentFillOutFormBinding;
import com.hfad.thinder.viewmodels.student.FillOutFormViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FillOutFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillOutFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentFillOutFormBinding binding;
    FillOutFormViewModel viewModel;

    public FillOutFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FillOutFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FillOutFormFragment newInstance(String param1, String param2) {
        FillOutFormFragment fragment = new FillOutFormFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_out_form, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FillOutFormViewModel.class);
        viewModel.setThesisId(requireArguments().getString("thesisUUID"));
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        return binding.getRoot();
    }

    public void sendForm(View view){
        viewModel.sendForm();
    }

}