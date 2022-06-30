package com.hfad.thinder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;

import android.os.Bundle;

import com.hfad.thinder.databinding.ActivityConfirmPasswordBinding;
import com.hfad.thinder.viewmodels.ConfirmPasswordViewModel;

public class ConfirmPasswordActivity extends AppCompatActivity {

    private ActivityConfirmPasswordBinding binding;
    private ConfirmPasswordViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_password);

        viewmodel = new ViewModelProvider(this).get(ConfirmPasswordViewModel.class);

        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(this);
    }
}