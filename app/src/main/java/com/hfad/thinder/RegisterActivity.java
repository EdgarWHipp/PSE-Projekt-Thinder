package com.hfad.thinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.hfad.thinder.databinding.ActivityLoginBinding;
import com.hfad.thinder.databinding.ActivityRegisterBinding;
import com.hfad.thinder.viewmodels.LoginViewModel;
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

  }
}
