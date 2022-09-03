package com.hfad.thinder.ui.supervisor;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentNewThesisBinding;
import com.hfad.thinder.ui.ThesisCardAdapter;
import com.hfad.thinder.ui.student.LikedThesesFragment;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;
import com.hfad.thinder.viewmodels.supervisor.NewThesisViewModel;
import com.hfad.thinder.viewmodels.supervisor.ThesisFormState;
import com.hfad.thinder.viewmodels.supervisor.ThesisViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 *  UI responsible for the creation of new theses.
 */
public class NewThesisFragment extends Fragment {

  private FragmentNewThesisBinding binding;
  protected ThesisViewModel viewModel;

  /**
   * Required empty constructor.
   */
  public NewThesisFragment() {
    // Required empty public constructor
  }

  /**
   * Handles layout inflation and binding. Gets the {@link NewThesisViewModel}.
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
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_thesis, container, false);

    View view = binding.getRoot();
    viewModel = new ViewModelProvider(requireActivity()).get(NewThesisViewModel.class);
    binding.setViewModel((NewThesisViewModel) viewModel);
    binding.setFragment(this);
    binding.setLifecycleOwner(getViewLifecycleOwner());


    return view;
  }

  /**
   * Observes important data changes in the {@link NewThesisViewModel}
   *
   * @param view                  view returned by {@link NewThesisFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
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

    final Observer<ViewModelResult> saveResultObserver = viewModelResult -> {
      if(viewModelResult==null)
        return;
      if (viewModelResult.isSuccess()) {
        Toast toast =
            Toast.makeText(getActivity(), getText(R.string.save_successful), Toast.LENGTH_LONG);
        toast.show();
        Navigation.findNavController(view).popBackStack();
      } else {
        Toast toast =
            Toast.makeText(getActivity(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
        toast.show();
      }
    };

    viewModel.getSaveResult().observe(getViewLifecycleOwner(), saveResultObserver);
  }

  /**
   * Calls the save function in the {@link NewThesisViewModel}
   */
  public void saveThesis() {
    viewModel.save();
  }

  /**
   * Moves user to the {@link CoursesOfStudyNewThesisFragment}
   *
   * @param view used to find fragment in NavController
   */
  public void goToCoursesOfStudyFragment(View view) {
    Navigation.findNavController(view)
        .navigate(R.id.action_newThesisFragment_to_coursesOfStudyNewThesisFragment);
  }

  /**
   * Moves user to the {@link ImageGalleryNewThesisFragment}
   *
   * @param view used to find fragment in NavController
   */
  public void goToImageGalleryFragment(View view) {
    Navigation.findNavController(view)
        .navigate(R.id.action_newThesisFragment_to_imageGalleryNewThesisFragment);
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