package com.hfad.thinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hfad.thinder.databinding.ActivityLoginBinding;
import com.hfad.thinder.databinding.ActivityRegisterBinding;
import com.hfad.thinder.viewmodels.LoginViewModel;
import com.hfad.thinder.viewmodels.RegistrationFormState;
import com.hfad.thinder.viewmodels.RegistrationViewModel;


public class RegisterActivity extends AppCompatActivity {

  ActivityRegisterBinding mBinding;
  RegistrationViewModel viewmodel;

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
        if (registrationFormState.getEmailError() != null) {
          mBinding.etLoginEMail.setError(registrationFormState.getEmailError());
        }
        if (registrationFormState.getPasswordError() != null) {
          mBinding.etLoginPassword.setError(registrationFormState.getPasswordError());
        }
        if (registrationFormState.getNotFullError() != null) {
          mBinding.etfirstname.setError(registrationFormState.getNotFullError());
          mBinding.etlastname.setError(registrationFormState.getNotFullError());
          mBinding.etchooseuniversity.setError(registrationFormState.getNotFullError());
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

    mBinding.etLoginEMail.addTextChangedListener(afterTextChangedListener);
    mBinding.etLoginPassword.addTextChangedListener(afterTextChangedListener);
    mBinding.etfirstname.addTextChangedListener(afterTextChangedListener);
    mBinding.etlastname.addTextChangedListener(afterTextChangedListener);



  }
}
