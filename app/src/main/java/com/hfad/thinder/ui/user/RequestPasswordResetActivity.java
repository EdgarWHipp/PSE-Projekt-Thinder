package com.hfad.thinder.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.user.PasswordResetRequestViewModel;

public class RequestPasswordResetActivity extends AppCompatActivity {

    /**
     * Called upon creation of the {@link RequestPasswordResetActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.hfad.thinder.databinding.ActivityRequestPasswordResetBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_request_password_reset);
        PasswordResetRequestViewModel viewModel = new ViewModelProvider(this).get(PasswordResetRequestViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = binding.toolbar;
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        final Observer<ViewModelResult> resultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if (viewModelResult.isSuccess()) {
                    goToForgotPasswordActivity();
                } else {
                    Toast.makeText(getApplicationContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        viewModel.getResetRequestResult().observe(this, resultObserver);
    }

    /**
     * Moves to the {@link ForgotPasswordActivity}
     */
    public void goToForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}