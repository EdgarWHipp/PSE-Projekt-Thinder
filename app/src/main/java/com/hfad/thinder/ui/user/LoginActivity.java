package com.hfad.thinder.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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
    private LoginViewModel viewModel;

    /**
     * Called upon creation of the {@link LoginActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate view and obtain an instance of the binding class
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        // obtain the viewModel component
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // assign the component to a property in the binding class
        mBinding.setViewmodel(viewModel);
        mBinding.setActivity(this);
        mBinding.setLifecycleOwner(this);

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
                viewModel.loginDataChanged();
            }
        };

        // observes the result of the login operation and moves the user to the next screen
        final Observer<ViewModelResult> loginSuccessfulObserver = new Observer<ViewModelResult>() {
            @Override
            public void onChanged(ViewModelResult loginResult) {

                if (loginResult.getSuccess() == ViewModelResultTypes.STUDENT) {
                    goToStudentActivity();
                }
                if (loginResult.getSuccess() == ViewModelResultTypes.SUPERVISOR) {

                    goToSupervisorActivity();
                }
                if (loginResult.getSuccess() == ViewModelResultTypes.UNVERIFIED) {
                    gotToVerifyTokenActivity();
                }

            }
        };

        viewModel.getLoginResult().observe(this, loginSuccessfulObserver);

        mBinding.etLoginEMail.addTextChangedListener(afterTextChangedListener);
        mBinding.etLoginPassword.addTextChangedListener(afterTextChangedListener);

    }

    /**
     * Moves to the {@link RegisterActivity}
     *
     * @param view button that triggered the method call
     */
    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the {@link RegisterActivity}
     *
     * @param view button that triggered the method call
     */
    public void goToRequestPasswordResetActivity(View view) {
        Intent intent = new Intent(this, RequestPasswordResetActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the {@link StudentActivity}
     *
     */
    private void goToStudentActivity() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the {@link SupervisorActivity}
     *
     */
    private void goToSupervisorActivity() {
        Intent intent = new Intent(this, SupervisorActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the {@link VerifyTokenActivity}
     *
     */
    private void gotToVerifyTokenActivity() {
        Intent intent = new Intent(this, VerifyTokenActivity.class);
        startActivity(intent);
    }

    /**
     * Hides the keyboard and calls the login function in the {@link LoginViewModel}
     */
    public void login(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            //hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        viewModel.login();
    }

    /**
     * Prevents user to get to the {@link LoginActivity} when pressing the back button after logging in successfully
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}