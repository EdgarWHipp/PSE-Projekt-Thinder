package com.hfad.thinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.databinding.ActivityLoginBinding;
import com.hfad.thinder.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding mBinding;
    LoginViewModel viewmodel;
    

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
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToForgotPasswordActivity(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}