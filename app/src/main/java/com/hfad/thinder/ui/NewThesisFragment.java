package com.hfad.thinder.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    private String imagePath;

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
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        Context context = getActivity();
        imagePath = "";

        if(requestCode == 10 && resultCode == Activity.RESULT_OK){
            if(data.getData() != null){
                Uri uri = data.getData();
                imagePath = getFileName(uri);
                binding.tvImagesFromGallery.setText(imagePath);
            } else if(data.getClipData() != null){
                ClipData mClipData = data.getClipData();
                String paths = "";
                for(int i = 0; i < mClipData.getItemCount(); ++i){
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagePath += (getFileName(uri) + " ");
                }
                binding.tvImagesFromGallery.setText(imagePath);
            }


        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}