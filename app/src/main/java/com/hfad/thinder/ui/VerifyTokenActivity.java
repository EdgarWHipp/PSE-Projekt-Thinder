package com.hfad.thinder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;

import android.os.Bundle;

import com.hfad.thinder.databinding.ActivityVerifyTokenBinding;
import com.hfad.thinder.viewmodels.VerifyTokenViewModel;

public class VerifyTokenActivity extends AppCompatActivity {

    private ActivityVerifyTokenBinding binding;
    private VerifyTokenViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_token);

        viewmodel = new ViewModelProvider(this).get(VerifyTokenViewModel.class);

        binding.setViewmodel(viewmodel);
        binding.setLifecycleOwner(this);
    }
}