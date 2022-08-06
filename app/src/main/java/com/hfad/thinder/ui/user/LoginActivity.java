package com.hfad.thinder.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityLoginBinding;
import com.hfad.thinder.ui.student.StudentActivity;
import com.hfad.thinder.ui.supervisor.SupervisorActivity;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.viewmodels.user.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding mBinding;
  private LoginViewModel viewmodel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Inflate view and obtain an instance of the binding class
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    // obtain the viewmodel component
    viewmodel = new ViewModelProvider(this).get(LoginViewModel.class);
    // assign the component to a property in the binding class
    mBinding.setViewmodel(viewmodel);
    mBinding.setLifecycleOwner(this);

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
        viewmodel.loginDataChanged();
      }
    };

    final Observer<ViewModelResult> loginSuccessfulObserver = new Observer<ViewModelResult>() {
      @Override
      public void onChanged(ViewModelResult loginResult) {


          if (loginResult.getSuccess() == ViewModelResultTypes.STUDENT) {
            goToStudentActivity();
          }
          if (loginResult.getSuccess() == ViewModelResultTypes.SUPERVISOR) {

            goToSupervisorActivity();
          }

      }
    };

    viewmodel.getLoginResult().observe(this, loginSuccessfulObserver);

    mBinding.etLoginEMail.addTextChangedListener(afterTextChangedListener);
    mBinding.etLoginPassword.addTextChangedListener(afterTextChangedListener);


  }


  public void goToRegisterActivity(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  public void goToRequestPasswordResetActivity(View view) {
    Intent intent = new Intent(this, RequestPasswordResetActivity.class);
    startActivity(intent);
  }

  private void goToStudentActivity() {
    Intent intent = new Intent(this, StudentActivity.class);
    startActivity(intent);
  }

  private void goToSupervisorActivity() {
    Intent intent = new Intent(this, SupervisorActivity.class);
    startActivity(intent);
  }

  @Override
  public void onBackPressed(){
    moveTaskToBack(true);
  }
}