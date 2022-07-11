package com.hfad.thinder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityRegisterBinding;
import com.hfad.thinder.viewmodels.user.RegistrationFormState;
import com.hfad.thinder.viewmodels.user.RegistrationResult;
import com.hfad.thinder.viewmodels.user.RegistrationViewModel;


public class RegisterActivity extends AppCompatActivity {

  private ActivityRegisterBinding mBinding;
  private RegistrationViewModel viewmodel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Inflate view and obtain an instance of the binding class
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    // obtain the viewmodel component
    viewmodel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    // assign the component to a property in the binding class
    mBinding.setViewmodel(viewmodel);
    mBinding.setLifecycleOwner(this);

    // my_child_toolbar is defined in the layout file
    Toolbar myChildToolbar = mBinding.toolbar;

    setSupportActionBar(myChildToolbar);
    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();

    // Enable the Up button
    ab.setDisplayHomeAsUpEnabled(true);


    //Setzt Fehlermeldungen für das Eingabeformat
    viewmodel.getRegistrationFormState().observe(this, new Observer<RegistrationFormState>() {
      @Override
      public void onChanged(RegistrationFormState registrationFormState) {
        if (registrationFormState.getEmailErrorMessageResourceId() != null) {
          mBinding.etLoginEMail.setError(
              getResources().getString(registrationFormState.getEmailErrorMessageResourceId()));
        }
        if (registrationFormState.getFirstNameErrorMessageResourceId() != null) {
          mBinding.etfirstname.setError(
              getResources().getString(registrationFormState.getFirstNameErrorMessageResourceId()));
        }
        if (registrationFormState.getLastNameErrorMessageResourceId() != null) {
          mBinding.etlastname.setError(
              getResources().getString(registrationFormState.getLastNameErrorMessageResourceId()));
        }
        if (registrationFormState.getPasswordErrorMessageResourceId() != null) {
          mBinding.etLoginPassword.setError(
              getResources().getString(registrationFormState.getPasswordErrorMessageResourceId()));
        }
        if (registrationFormState.getPasswordConfirmationErrorMessageResourceId() != null) {
          mBinding.etconfirmpassword.setError(getResources().getString(
              registrationFormState.getPasswordConfirmationErrorMessageResourceId()));
        }
      }
    });

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
        viewmodel.registrationDataChanged();
      }
    };

    final Observer<RegistrationResult> registrationSuccessfulObserver =
        new Observer<RegistrationResult>() {
          @Override
          public void onChanged(@Nullable final RegistrationResult registrationResult) {
            if (registrationResult.isSuccess()) {
              goToVerifyTokenActivity();
            }
          }
        };

    viewmodel.getRegistrationResult().observe(this, registrationSuccessfulObserver);


    mBinding.etLoginEMail.addTextChangedListener(afterTextChangedListener);
    mBinding.etfirstname.addTextChangedListener(afterTextChangedListener);
    mBinding.etlastname.addTextChangedListener(afterTextChangedListener);
    mBinding.etLoginPassword.addTextChangedListener(afterTextChangedListener);
    mBinding.etconfirmpassword.addTextChangedListener(afterTextChangedListener);

  }

  private void goToVerifyTokenActivity() {
    Intent intent = new Intent(this, VerifyTokenActivity.class);
    startActivity(intent);
  }
}
