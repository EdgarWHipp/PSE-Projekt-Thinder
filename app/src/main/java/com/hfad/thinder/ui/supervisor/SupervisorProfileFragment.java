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
 * A simple {@link Fragment} subclass.
 * Use the {@link SupervisorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupervisorProfileFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private EditProfileViewModel viewmodel;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private FragmentSupervisorProfileBinding binding;

  public SupervisorProfileFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment SupervisorProfileFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SupervisorProfileFragment newInstance(String param1, String param2) {
    SupervisorProfileFragment fragment = new SupervisorProfileFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(false);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supervisor_profile, container,
        false);

    viewmodel = new ViewModelProvider(this).get(EditProfileViewModel.class);
    binding.setFragment(this);
    binding.setViewmodel(viewmodel);
    binding.setLifecycleOwner(this);

    viewmodel.getFormState().observe(getViewLifecycleOwner(), new Observer<EditProfileFormState>() {
      @Override
      public void onChanged(EditProfileFormState editProfileFormState) {
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
        viewmodel.profileDataChanged();
      }
    };

    final Observer<ViewModelResult> safeResultObserver = new Observer<ViewModelResult>() {
      @Override
      public void onChanged(ViewModelResult editProfileResult) {
        Toast toast;
        if (editProfileResult.isSuccess()) {
          toast = Toast.makeText(getContext(),
              getResources().getString(R.string.edit_profile_success_message), Toast.LENGTH_LONG);
        } else if (!editProfileResult.isSuccess()) {
          toast =
              Toast.makeText(getContext(), editProfileResult.getErrorMessage(), Toast.LENGTH_LONG);
        }
      }
    };

    final Observer<ViewModelResult> deleteResultObserver = new Observer<ViewModelResult>() {
      @Override
      public void onChanged(ViewModelResult editProfileResult) {
        if (editProfileResult.isSuccess()) {
          goToLoginActivity();
        } else if (!editProfileResult.isSuccess()) {
          Toast toast =
              Toast.makeText(getContext(), editProfileResult.getErrorMessage(), Toast.LENGTH_LONG);
        }
      }
    };

    viewmodel.getSafeResult().observe(getViewLifecycleOwner(), safeResultObserver);
    viewmodel.getDeleteResult().observe(getViewLifecycleOwner(), deleteResultObserver);
    binding.etFirstName.addTextChangedListener(afterTextChangedListener);
    binding.etLastName.addTextChangedListener(afterTextChangedListener);
    binding.etBuilding.addTextChangedListener(afterTextChangedListener);
    binding.etRoom.addTextChangedListener(afterTextChangedListener);
    binding.etPhoneNumber.addTextChangedListener(afterTextChangedListener);
    binding.etInstitute.addTextChangedListener(afterTextChangedListener);

    // Inflate the layout for this fragment
    View view = binding.getRoot();
    return view;
  }

  private void goToLoginActivity() {
    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
    getActivity().startActivity(intent);
  }

  public void deleteProfile(View view){
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setCancelable(true);
    builder.setTitle(getContext().getResources().getString(R.string.account_remove));
    builder.setMessage(getContext().getResources().getString(R.string.account_remove_text));
    builder.setPositiveButton(getContext().getResources().getString(R.string.remove),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                viewmodel.delete();
                goToLoginActivity();
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
