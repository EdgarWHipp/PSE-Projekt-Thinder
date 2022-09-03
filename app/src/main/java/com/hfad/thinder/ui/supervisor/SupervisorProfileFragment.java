package com.hfad.thinder.ui.supervisor;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSupervisorProfileBinding;
import com.hfad.thinder.ui.user.LoginActivity;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.supervisor.EditProfileFormState;
import com.hfad.thinder.viewmodels.supervisor.EditProfileViewModel;

/**
 * Supervisor profile screen where the supervisor can change her/his personal data.
 */
public class SupervisorProfileFragment extends Fragment {

    private EditProfileViewModel viewModel;
    private FragmentSupervisorProfileBinding binding;

    /**
     * Required empty constructor
     */
    public SupervisorProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supervisor_profile, container,
                false);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding.setFragment(this);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // observes the result of a profile deletion and moves user to login-screen if successful
        viewModel.getFormState().observe(getViewLifecycleOwner(), editProfileFormState -> {
            if (editProfileFormState.getFirstNameErrorMessage() != null) {
                binding.etFirstName.setError(
                        getResources().getString(editProfileFormState.getFirstNameErrorMessage()));
            }
            if (editProfileFormState.getLastNameErrorMessage() != null) {
                binding.etLastName.setError(
                        getResources().getString(editProfileFormState.getLastNameErrorMessage()));
            }
            if (editProfileFormState.getBuildingErrorMessage() != null) {
                binding.etBuilding.setError(
                        getResources().getString(editProfileFormState.getBuildingErrorMessage()));
            }
            if (editProfileFormState.getRoomErrorMessage() != null) {
                binding.etRoom.setError(
                        getResources().getString(editProfileFormState.getRoomErrorMessage()));
            }
            if (editProfileFormState.getPhoneNumberErrorMessage() != null) {
                binding.etPhoneNumber.setError(
                        getResources().getString(editProfileFormState.getPhoneNumberErrorMessage()));
            }
            if (editProfileFormState.getInstituteErrorMessage() != null) {
                binding.etInstitute.setError(
                        getResources().getString(editProfileFormState.getInstituteErrorMessage()));
            }
        });

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
                viewModel.profileDataChanged();
            }
        };

        // observes the result of a data change and informs user about success/failure
        final Observer<ViewModelResult> saveResultObserver = editProfileResult -> {
            if(editProfileResult == null)
                return;
            Toast toast;
            if (editProfileResult.isSuccess()) {
                toast = Toast.makeText(getContext(),
                        getResources().getString(R.string.edit_profile_success_message), Toast.LENGTH_LONG);
                toast.show();
                ((SupervisorActivity) getActivity()).profileCompleted();
            } else if (!editProfileResult.isSuccess()) {
                toast =
                        Toast.makeText(getContext(), editProfileResult.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        };

        final Observer<ViewModelResult> deleteResultObserver = editProfileResult -> {
            if (editProfileResult.isSuccess()) {
                goToLoginActivity();
            } else if (!editProfileResult.isSuccess()) {
                Toast toast =
                        Toast.makeText(getContext(), editProfileResult.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        };

        viewModel.getSafeResult().observe(getViewLifecycleOwner(), saveResultObserver);
        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), deleteResultObserver);
        binding.etFirstName.addTextChangedListener(afterTextChangedListener);
        binding.etLastName.addTextChangedListener(afterTextChangedListener);
        binding.etBuilding.addTextChangedListener(afterTextChangedListener);
        binding.etRoom.addTextChangedListener(afterTextChangedListener);
        binding.etPhoneNumber.addTextChangedListener(afterTextChangedListener);
        binding.etInstitute.addTextChangedListener(afterTextChangedListener);

        return binding.getRoot();
    }

    /**
     * Starts the {@link LoginActivity}
     */
    private void goToLoginActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * Opens confirmation prompt and calls delete method in the {@link EditProfileViewModel}
     */
    public void deleteProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.account_remove));
        builder.setMessage(getContext().getResources().getString(R.string.account_remove_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.remove),
                (dialog, which) -> {
                    viewModel.delete();
                    goToLoginActivity();
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
