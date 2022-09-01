package com.hfad.thinder.ui.user;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityForgotPasswordBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.user.ForgotPasswordFormState;
import com.hfad.thinder.viewmodels.user.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {


    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    /**
     * Called upon creation of the {@link ForgotPasswordActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = binding.toolbar;
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // informs the viewModel about input changes
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
                viewModel.passwordForgotDataChanged();
            }
        };

        // observes the ForgotPasswordState and sets error accordingly
        viewModel.getFormState().observe(this, new Observer<ForgotPasswordFormState>() {
            @Override
            public void onChanged(ForgotPasswordFormState forgotPasswordFormState) {
                Resources resources = getResources();
                if(forgotPasswordFormState.getCodeErrorMessage() != null)
                    binding.etcode.setError(resources.getString(forgotPasswordFormState.getCodeErrorMessage()));
                if(forgotPasswordFormState.getNewPasswordErrorMessage()!=null)
                    binding.etLoginPassword.setError(resources.getString(forgotPasswordFormState.getNewPasswordErrorMessage()));
                if(forgotPasswordFormState.getNewPasswordConfirmationErrorMessage() != null)
                    binding.etconfirmpassword.setError(resources.getString(forgotPasswordFormState.getNewPasswordConfirmationErrorMessage()));
            }
        });

        // observes the result of the password change operation and informs the user accordingly
        final Observer<ViewModelResult> resultObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult viewModelResult) {
                if(viewModelResult == null)
                    return;
                if(viewModelResult.isSuccess()) {
                    Toast.makeText(getApplicationContext(), R.string.successful_password_change, Toast.LENGTH_SHORT).show();
                    goToLoginActivity();
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.failure, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getLoginResult().observe(this, resultObserver);
        binding.etcode.addTextChangedListener(afterTextChangedListener);
        binding.etLoginPassword.addTextChangedListener(afterTextChangedListener);
        binding.etconfirmpassword.addTextChangedListener(afterTextChangedListener);
    }

    /**
     * Moves to the {@link LoginActivity}
     */
    private void goToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}