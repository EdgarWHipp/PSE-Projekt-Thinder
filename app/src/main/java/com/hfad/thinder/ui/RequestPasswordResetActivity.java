package com.hfad.thinder.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityRequestPasswordResetBinding;
import com.hfad.thinder.viewmodels.user.PasswordResetRequestViewModel;

public class RequestPasswordResetActivity extends AppCompatActivity {

    private ActivityRequestPasswordResetBinding binding;
    private PasswordResetRequestViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_password_reset);
        viewmodel = new ViewModelProvider(this).get(PasswordResetRequestViewModel.class);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = binding.toolbar;
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void goToForgotPasswordActivity(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}