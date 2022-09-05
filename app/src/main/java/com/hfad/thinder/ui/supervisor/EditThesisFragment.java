package com.hfad.thinder.ui.supervisor;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentEditThesisBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;
import com.hfad.thinder.viewmodels.supervisor.ThesisFormState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *  UI that allows user to edit all the fields of one of his/her created theses.
 */
public class EditThesisFragment extends Fragment {

    private FragmentEditThesisBinding binding;
    private EditThesisViewModel viewModel;

    /**
     * Required empty constructor.
     */
    public EditThesisFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link EditThesisViewModel}.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
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

        return binding.getRoot();
    }

    /**
     * Observes important data changes in the {@link EditThesisViewModel}
     *
     * @param view                  view returned by {@link EditThesisFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
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

        viewModel.getImages().observe(getViewLifecycleOwner(), bitmaps -> viewModel.thesisDataChanged());

        viewModel.getFormState().observe(getViewLifecycleOwner(), editThesisFormState -> {
            Resources resources = getResources();
            if (editThesisFormState.getTitleErrorMessage() != null) {
                binding.etInsertNameOfThesis.setError(
                        resources.getString(editThesisFormState.getTitleErrorMessage()));
            }
            if (editThesisFormState.getTaskErrorMessage() != null) {
                binding.etInsertTask.setError(
                        resources.getString(editThesisFormState.getTaskErrorMessage()));
            }
            if (editThesisFormState.getMotivationErrorMessage() != null) {
                binding.etInsertMotivation.setError(
                        resources.getString(editThesisFormState.getMotivationErrorMessage()));
            }
            if (editThesisFormState.getProfessorErrorMessage() != null) {
                binding.etInsertSupervisingProf.setError(
                        resources.getString(editThesisFormState.getProfessorErrorMessage()));
            }
            if (editThesisFormState.getQuestionsErrorMessage() != null) {
                binding.etInsertQuestions.setError(
                        resources.getString(editThesisFormState.getQuestionsErrorMessage()));
            }
        });


        binding.etInsertNameOfThesis.addTextChangedListener(afterTextChangedListener);
        binding.etInsertTask.addTextChangedListener(afterTextChangedListener);
        binding.etInsertMotivation.addTextChangedListener(afterTextChangedListener);
        binding.etInsertQuestions.addTextChangedListener(afterTextChangedListener);
        binding.etInsertSupervisingProf.addTextChangedListener(afterTextChangedListener);
        binding.tvCoursesOfStudy.addTextChangedListener(afterTextChangedListener);

        final Observer<ViewModelResult> deleteResultObserver = viewModelResult -> {
            if (viewModelResult == null)
                return;
            if (viewModelResult.isSuccess()) {
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(),
                        Toast.LENGTH_LONG);
                toast.show();
            }
        };

        final Observer<ViewModelResult> editThesisResultObserver = viewModelResult -> {
            if (viewModelResult == null)
                return;
            if (viewModelResult.isSuccess()) {
                Toast toast =
                        Toast.makeText(getContext(), getText(R.string.save_successful), Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast =
                        Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        };
        viewModel.getSaveResult().observe(getViewLifecycleOwner(), editThesisResultObserver);
        viewModel.getDeleteThesisResult().observe(getViewLifecycleOwner(), deleteResultObserver);
    }

    /**
     * Uses NavigationComponent to navigate to the StatisticsFragment
     *
     * @param view used to find fragment in NavController
     */
    public void goToStatisticsFragment(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_editThesisFragment_to_thesisStatisticsFragment);
    }

    /**
     * Opens dialog window und calls delete function in {@link EditThesisViewModel}
     */
    public void deleteThesis() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.remove_thesis));
        builder.setMessage(getContext().getResources().getString(R.string.remove_thesis_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.remove),
                (dialog, which) -> viewModel.delete());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Calls the save function in the {@link EditThesisViewModel}
     */
    public void saveThesis() {
        viewModel.save();
    }

    /**
     * Moves user to the {@link CoursesOfStudyEditThesisFragment}
     *
     * @param view used to find fragment in NavController
     */
    public void goToCoursesOfStudyFragment(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_editThesisFragment_to_coursesOfStudyEditThesisFragment);
    }

    /**
     * Moves user to the {@link ImageGalleryEditThesisFragment}
     *
     * @param view used to find fragment in NavController
     */
    public void goToImageGalleryFragment(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_editThesisFragment_to_imageGalleryEditThesisFragment);
    }

    /**
     * Starts the image selection process
     */
    public void openImagePicker() {
        makeImageSelection();
    }

    /**
     * Performs an image selection
     */
    public void makeImageSelection() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 10);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    /**
     * Is called after the image picker activity is finished.
     *
     * @param requestCode   code handed over to the image picker activity
     * @param resultCode    result code from the image picker activity
     * @param data          data from the image gallery activity i.e. images
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            ArrayList<Bitmap> images = new ArrayList<>();
            if (data.getData() != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    images.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); ++i) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    try {
                        Bitmap bitmap =
                                MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
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
