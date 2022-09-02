package com.hfad.thinder.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityRegisterBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.user.RegistrationFormState;
import com.hfad.thinder.viewmodels.user.RegistrationViewModel;


public class RegisterActivity extends AppCompatActivity {

  private ActivityRegisterBinding mBinding;
  private RegistrationViewModel viewModel;

  /**
   * Called upon creation of the {@link RegisterActivity}. Acts as a constructor sets up the member variables and inflates the layout.
   *
   * @param savedInstanceState not used
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Inflate view and obtain an instance of the binding class
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    mBinding.setActivity(this);
    // obtain the vieWmodel component
    viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    // assign the component to a property in the binding class
    mBinding.setViewmodel(viewModel);
    mBinding.setLifecycleOwner(this);

    // my_child_toolbar is defined in the layout file
    Toolbar myChildToolbar = mBinding.toolbar;

    setSupportActionBar(myChildToolbar);
    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();

    // Enable the Up button
    ab.setDisplayHomeAsUpEnabled(true);


    // observes the RegistrationFormState and sets error accordingly
    viewModel.getRegistrationFormState().observe(this, new Observer<RegistrationFormState>() {
      @Override
      public void onChanged(RegistrationFormState registrationFormState) {
        if (registrationFormState.getEmailErrorMessage() != null) {
          mBinding.etLoginEMail.setError(
              getResources().getString(registrationFormState.getEmailErrorMessage()));
        }
        if (registrationFormState.getFirstNameErrorMessage() != null) {
          mBinding.etfirstname.setError(
              getResources().getString(registrationFormState.getFirstNameErrorMessage()));
        }
        if (registrationFormState.getLastNameErrorMessage() != null) {
          mBinding.etlastname.setError(
              getResources().getString(registrationFormState.getLastNameErrorMessage()));
        }
        if (registrationFormState.getPasswordErrorMessage() != null) {
          mBinding.etLoginPassword.setError(
              getResources().getString(registrationFormState.getPasswordErrorMessage()));
        }
        if (registrationFormState.getPasswordConfirmationErrorMessage() != null) {
          mBinding.etconfirmpassword.setError(getResources().getString(
              registrationFormState.getPasswordConfirmationErrorMessage()));
        }
      }
    });

    // informs the viewModel about input changes
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
        viewModel.registrationDataChanged();
      }
    };

    // observes the result of the register operation and moves the user to the next screen
    final Observer<ViewModelResult> registrationSuccessfulObserver =
        new Observer<ViewModelResult>() {
          @Override
          public void onChanged(@Nullable final ViewModelResult registrationResult) {
            if (registrationResult.isSuccess()) {
              goToVerifyTokenActivity();
            }
          }
        };

    viewModel.getRegistrationResult().observe(this, registrationSuccessfulObserver);


    mBinding.etLoginEMail.addTextChangedListener(afterTextChangedListener);
    mBinding.etfirstname.addTextChangedListener(afterTextChangedListener);
    mBinding.etlastname.addTextChangedListener(afterTextChangedListener);
    mBinding.etLoginPassword.addTextChangedListener(afterTextChangedListener);
    mBinding.etconfirmpassword.addTextChangedListener(afterTextChangedListener);

  }
  /**
   * Moves to the {@link VerifyTokenActivity}
   */
  private void goToVerifyTokenActivity() {
    Intent intent = new Intent(this, VerifyTokenActivity.class);
    startActivity(intent);
  }

  /**
   * Hides keyboard and moves calls register in the {@link RegistrationViewModel}
   */
  public void register(){
    // Check if no view has focus:
    View view = this.getCurrentFocus();
    if (view != null) {
      //hide keyboard
      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    viewModel.register();
  }
}
