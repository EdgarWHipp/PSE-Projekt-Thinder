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
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentStudentProfileBinding binding;
    private EditProfileViewModel viewModel;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
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
        // Inflate the layout for this fragment
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_student_profile, container, false);

        view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(EditProfileViewModel.class);
        binding.setFragment(this);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
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

    public void removeProfile(View view) {
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

    public void goToCoursesOfStudyFragment(View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_studentProfileFragment_to_coursesOfStudyStudentFragment);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
}