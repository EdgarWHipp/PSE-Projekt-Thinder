package com.hfad.thinder.ui;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentNewThesisBinding;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewThesisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewThesisFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String imagePath;

    private FragmentNewThesisBinding binding;
    protected EditThesisViewModel viewModel;


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
        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        binding.setViewModel(viewModel);
        binding.setFragment(this);

        return view;
    }

    public void saveThesis(View view){
        viewModel.addThesis();
    }

    public void goToCoursesOfStudyFragment(View view){
        Navigation.findNavController(view).navigate(R.id.action_newThesisFragment_to_coursesOfStudyFragment);
    }

    public void goToImageGalleryFragment(View view){
        Navigation.findNavController(view).navigate(R.id.action_newThesisFragment_to_imageGalleryFragment2);
    }


    public void openImagePicker(View view){
        Log.i(TAG, "openImagePicker: ");
        makeImageSelection();
    }

    public void makeImageSelection(){
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 10);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        Context context = getActivity();

        if(requestCode == 10 && resultCode == Activity.RESULT_OK){
            ArrayList<Bitmap> images = new ArrayList<Bitmap>();
            if(data.getData() != null){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    images.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(data.getClipData() != null){
                ClipData mClipData = data.getClipData();
                for(int i = 0; i < mClipData.getItemCount(); ++i){
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        images.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            viewModel.setImages(new MutableLiveData<>(images));
        }
    }
}