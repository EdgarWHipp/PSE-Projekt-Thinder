package com.hfad.thinder.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityForgotPasswordBinding;
import com.hfad.thinder.ui.student.StudentActivity;
import com.hfad.thinder.ui.supervisor.SupervisorActivity;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
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
        viewmodel.passwordForgotDataChanged();
      }
    };

    final Observer<ViewModelResult> resultObserver = new Observer<ViewModelResult>() {
      @Override
      public void onChanged(ViewModelResult viewModelResult) {
        if (viewModelResult.isSuccess()) {
          if (viewModelResult.getSuccess() == ViewModelResultTypes.STUDENT) {
            goToStudentActivity();
          }
          if (viewModelResult.getSuccess() == ViewModelResultTypes.SUPERVISOR) {
            goToSupervisorActivity();
          }
        }
      }
    };

    viewmodel.getLoginResult().observe(this, resultObserver);
    binding.etcode.addTextChangedListener(afterTextChangedListener);
    binding.etLoginPassword.addTextChangedListener(afterTextChangedListener);
    binding.etconfirmpassword.addTextChangedListener(afterTextChangedListener);
  }

  private void goToStudentActivity() {
    Intent intent = new Intent(this, StudentActivity.class);
    startActivity(intent);
  }

  private void goToSupervisorActivity() {
    Intent intent = new Intent(this, SupervisorActivity.class);
    startActivity(intent);
  }
}