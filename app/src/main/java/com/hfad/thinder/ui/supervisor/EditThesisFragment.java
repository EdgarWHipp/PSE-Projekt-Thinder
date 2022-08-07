package com.hfad.thinder.ui.supervisor;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentEditThesisBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.supervisor.ThesisFormState;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditThesisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditThesisFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentEditThesisBinding binding;
    private View view;
    private EditThesisViewModel viewModel;

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
        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        viewModel.setThesisId(UUID.fromString(requireArguments().getString("thesisUUID")));
        binding.setViewModel(viewModel);
        view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.thesisDataChanged();
            }
        };

        viewModel.getImages().observe(getViewLifecycleOwner(), new Observer<ArrayList<Bitmap>>() {
            @Override
            public void onChanged(ArrayList<Bitmap> bitmaps) {
                viewModel.thesisDataChanged();
            }
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), new Observer<ThesisFormState>() {
            @Override
            public void onChanged(ThesisFormState editThesisFormState) {
                Resources resources = getResources();
                if (editThesisFormState.getTitleErrorMessage() != null) {
                    binding.etInsertNameOfThesis.setError(resources.getString(editThesisFormState.getTitleErrorMessage()));
                }
                if (editThesisFormState.getTaskErrorMessage() != null) {
                    binding.etInsertTask.setError(resources.getString(editThesisFormState.getTaskErrorMessage()));
                }
                if (editThesisFormState.getMotivationErrorMessage() != null) {
                    binding.etInsertMotivation.setError(resources.getString(editThesisFormState.getMotivationErrorMessage()));
                }
                if (editThesisFormState.getProfessor() != null) {
                    binding.etInsertSupervisingProf.setError(resources.getString(editThesisFormState.getProfessor()));
                }
                if (editThesisFormState.getCourseOfStudy() != null) {
                    binding.tvCoursesOfStudy.setError(resources.getString(editThesisFormState.getCourseOfStudy()));
                }
                if(editThesisFormState.getQuestionsErrorMessage() != null){
                    binding.etInsertQuestions.setError(resources.getString(editThesisFormState.getQuestionsErrorMessage()));
                }
            }
        });


        binding.etInsertNameOfThesis.addTextChangedListener(afterTextChangedListener);
        binding.etInsertTask.addTextChangedListener(afterTextChangedListener);
        binding.etInsertMotivation.addTextChangedListener(afterTextChangedListener);
        binding.etInsertQuestions.addTextChangedListener(afterTextChangedListener);
        binding.etInsertSupervisingProf.addTextChangedListener(afterTextChangedListener);
        binding.tvCoursesOfStudy.addTextChangedListener(afterTextChangedListener);

        final Observer<ViewModelResult> deleteResultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult.isSuccess()) {
                    Navigation.findNavController(view).popBackStack();
                } else {
                    Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(),
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };

        final Observer<ViewModelResult> editThesisResultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult.isSuccess()) {
                    Toast toast =
                            Toast.makeText(getContext(), getText(R.string.save_successful), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast =
                            Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };
        viewModel.getSaveResult().observe(getViewLifecycleOwner(), editThesisResultObserver);
        viewModel.getDeleteThesisResult().observe(getViewLifecycleOwner(), deleteResultObserver);
    }

    /**
     * Uses NavigationComponent to navigate to the StatisticsFragment
     *
     * @param view used to find corresponding NavigationController
     */
    public void goToStatistics(View view){
        Navigation.findNavController(view).navigate(R.id.action_editThesisFragment_to_thesisStatisticsFragment);
    }

    /**
     * Opens dialog window und calls delete function in viewmodel
     */
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
        viewModel.save();
    }

    public void goToCoursesOfStudyFragment(View view){
        Navigation.findNavController(view).navigate(R.id.action_editThesisFragment_to_coursesOfStudyEditThesisFragment);
    }

    public void goToImageGalleryFragment(View view){
        Navigation.findNavController(view).navigate(R.id.action_editThesisFragment_to_imageGalleryEditThesisFragment);
    }

    public void openImagePicker(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.open_image_picker));
        builder.setMessage(getContext().getResources().getString(R.string.open_image_picker_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.open),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makeImageSelection();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
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
            viewModel.setImages(images);
        }
    }
}