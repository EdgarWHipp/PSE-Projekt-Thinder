package com.hfad.thinder.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentEditThesisBinding;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditThesisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditThesisFragment extends NewThesisFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentEditThesisBinding binding;

    public EditThesisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditThesisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditThesisFragment newInstance(String param1, String param2) {
        EditThesisFragment fragment = new EditThesisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_thesis, container, false);
        binding.setFragment(this);
        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        viewModel.setThesisId(requireArguments().getString("thesisUUID"));
        return binding.getRoot();
    }

    public void goToStatistics(View view){
        Navigation.findNavController(view).navigate(R.id.action_editThesisFragment_to_thesisStatisticsFragment);
    }

    public void deleteThesis(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.remove_thesis));
        builder.setMessage(getContext().getResources().getString(R.string.remove_thesis_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.remove),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveThesis(View view){
        viewModel.editThesis();
    }

    @Override
    public void openImagePicker(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.open_image_picker));
        builder.setMessage(getContext().getResources().getString(R.string.open_image_picker_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.open),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditThesisFragment.super.makeImageSelection();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }
}