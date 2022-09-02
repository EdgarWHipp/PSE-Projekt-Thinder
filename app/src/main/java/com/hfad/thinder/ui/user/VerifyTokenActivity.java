package com.hfad.thinder.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.thinder.R;
import com.hfad.thinder.ui.student.StudentActivity;
import com.hfad.thinder.ui.supervisor.SupervisorActivity;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.viewmodels.user.VerifyTokenViewModel;

public class VerifyTokenActivity extends AppCompatActivity {

    /**
     * Called upon creation of the {@link VerifyTokenActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        com.hfad.thinder.databinding.ActivityVerifyTokenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_token);

        VerifyTokenViewModel viewModel = new ViewModelProvider(this).get(VerifyTokenViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        final Observer<ViewModelResult> verifyTokenResultObserver =
                new Observer<ViewModelResult>() {
                    @Override
                    public void onChanged(ViewModelResult verifyTokenResult) {

                        if (verifyTokenResult.getSuccess() == ViewModelResultTypes.STUDENT) {
                            goToStudentActivity();
                        }
                        if (verifyTokenResult.getSuccess() == ViewModelResultTypes.SUPERVISOR) {
                            goToSupervisorActivity();
                        }
                    }

                };
        viewModel.getVerifyTokenResult().observe(this, verifyTokenResultObserver);
    }

    /**
     * Moves to the {@link StudentActivity}
     */
    private void goToStudentActivity() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the {@link SupervisorActivity}
     */
    private void goToSupervisorActivity() {
        Intent intent = new Intent(this, SupervisorActivity.class);
        startActivity(intent);
    }
}