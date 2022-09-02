package com.hfad.thinder.ui.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentStudentProfileBinding;
import com.hfad.thinder.ui.user.LoginActivity;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.student.EditProfileFormState;
import com.hfad.thinder.viewmodels.student.EditProfileViewModel;

/**
 * Student profile screen where the student can change her/his personal data.
 */
public class StudentProfileFragment extends Fragment {

    private FragmentStudentProfileBinding binding;
    private EditProfileViewModel viewModel;


    /**
     * Required empty constructor
     */
    public StudentProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link EditProfileViewModel}
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
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_student_profile, container, false);

        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(EditProfileViewModel.class);
        binding.setFragment(this);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // observes the result of a profile deletion and moves user to login-screen if successful
        final Observer<ViewModelResult> deleteResultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult.isSuccess()) {
                    goToLoginActivity();
                } else {
                    Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(),
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };

        // observes the result of a data change and informs user about success/failure
        final Observer<ViewModelResult> saveResultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if(viewModelResult == null)
                    return;
                if (viewModelResult.isSuccess()) {
                    Toast toast =
                            Toast.makeText(getContext(), getText(R.string.save_successful), Toast.LENGTH_LONG);
                    toast.show();
                    ((StudentActivity) getActivity()).profileCompleted();
                } else {
                    Toast toast =
                            Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.profileDataChanged();
            }
        };


        viewModel.getSelectedCoursesOfStudy().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.profileDataChanged();
            }
        });

        viewModel.getFormState().observe(getViewLifecycleOwner(), new Observer<EditProfileFormState>() {
            @Override
            public void onChanged(EditProfileFormState editProfileFormState) {
                Resources resources = getResources();
                if (editProfileFormState.getFirstNameErrorMessage() != null) {
                    binding.etFirstName.setError(resources.getString(editProfileFormState.getFirstNameErrorMessage()));
                }
                if (editProfileFormState.getLastNameErrorMessage() != null) {
                    binding.etLastName.setError(resources.getString(editProfileFormState.getLastNameErrorMessage()));
                }
                if (editProfileFormState.getCoursesOfStudyErrorMessage() != null) {
                    binding.tvCoursesOfStudy.setError(resources.getString(editProfileFormState.getCoursesOfStudyErrorMessage()));
                }
            }
        });
        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), deleteResultObserver);
        viewModel.getSafeResult().observe(getViewLifecycleOwner(), saveResultObserver);
        binding.etFirstName.addTextChangedListener(afterTextChangedListener);
        binding.etLastName.addTextChangedListener(afterTextChangedListener);

        return view;
    }

    /**
     * Opens confirmation prompt and calls delete method in the {@link EditProfileViewModel}
     */
    public void removeProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.account_remove));
        builder.setMessage(getContext().getResources().getString(R.string.account_remove_text));
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

    /**
     * Moves user to the {@link CoursesOfStudyStudentFragment}
     *
     * @param view needed to get fragment in NavController
     */
    public void goToCoursesOfStudyFragment(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_studentProfileFragment_to_coursesOfStudyStudentFragment);
    }

    /**
     * Starts the {@link LoginActivity}
     */
    private void goToLoginActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
}