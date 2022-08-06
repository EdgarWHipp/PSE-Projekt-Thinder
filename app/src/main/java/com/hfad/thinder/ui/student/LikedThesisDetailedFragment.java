package com.hfad.thinder.ui.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentLikedThesisDetailedBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.student.LikedThesisDetailedViewModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikedThesisDetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedThesisDetailedFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentLikedThesisDetailedBinding binding;
    private LikedThesisDetailedViewModel viewModel;

    UUID thesisUUID;


    public LikedThesisDetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LikedThesisDetailedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LikedThesisDetailedFragment newInstance(String param1, String param2) {
        LikedThesisDetailedFragment fragment = new LikedThesisDetailedFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liked_thesis_detailed, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LikedThesisDetailedViewModel.class);
        thesisUUID = UUID.fromString(requireArguments().getString("thesisUUID"));
        String thesisTitle = requireArguments().getString("thesisTitle");
        ((StudentActivity) getActivity()).setActionBarTitle(thesisTitle);
        Bitmap image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_image);
        Bitmap image2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.index);
        ArrayList<Bitmap> images = new ArrayList<>();
        images.add(image2);
        images.add(image);
        viewModel.setThesisId(thesisUUID, images);
        // Inflate the layout for this fragment
        binding.setFragment(this);
        binding.setViewModel(viewModel);

        final Observer<ViewModelResult> deleteResultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult.isSuccess()) {
                    Toast toast = Toast.makeText(getContext(), getText(R.string.delete_successful), Toast.LENGTH_LONG);
                    toast.show();
                } else if (!viewModelResult.isSuccess()) {
                    Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };

        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), deleteResultObserver);

        return binding.getRoot();
    }

    public void startEmailClient(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String receiver = viewModel.getMail().getValue();
        Uri data = Uri.parse("mailto:" + receiver);
        intent.setData(data);
        startActivity(intent);
    }

    public void goToFillOutFormFragment(View view){
        Bundle bundle = new Bundle();
        bundle.putString("thesisUUID", thesisUUID.toString());
        Navigation.findNavController(view).navigate(R.id.action_likedThesisDetailedFragment_to_fillOutFormFragment, bundle);
    }

    public void goToImageGalleryFragment(View view){
        Navigation.findNavController(view).navigate(R.id.action_likedThesisDetailedFragment_to_imageGalleryFragmentStudent);
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
}