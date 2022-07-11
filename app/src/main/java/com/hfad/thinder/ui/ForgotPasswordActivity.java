package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityForgotPasswordBinding;
import com.hfad.thinder.viewmodels.user.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {


  private ActivityForgotPasswordBinding binding;
  private ForgotPasswordViewModel viewmodel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
    viewmodel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

    // my_child_toolbar is defined in the layout file
    Toolbar myChildToolbar = binding.toolbar;
    setSupportActionBar(myChildToolbar);

    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();

    // Enable the Up button
    ab.setDisplayHomeAsUpEnabled(true);
  }
}