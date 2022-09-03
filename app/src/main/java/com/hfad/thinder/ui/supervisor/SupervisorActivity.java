package com.hfad.thinder.ui.supervisor;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.supervisor.SupervisorViewModel;

/**
 * Acts as the main ui element in the supervisor screen. Handles the correct display of the fragments
 * showing the actual content.
 */
public class SupervisorActivity extends AppCompatActivity {

    private SupervisorViewModel viewModel;

    /**
     * Called upon creation of the {@link SupervisorActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.hfad.thinder.databinding.ActivitySupervisorBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_supervisor);

        // setting up the bottom navigation bar
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;
        NavController navController = findNavController(this, R.id.supervisorFragmentContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // setting up the top action bar
        Toolbar topActionBar = mBinding.toolbar;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.supervisorProfileFragment, R.id.thesisManagerFragment).build();
        setSupportActionBar(topActionBar);
        NavigationUI.setupWithNavController(topActionBar, navController, appBarConfiguration);

        viewModel = new ViewModelProvider(this).get(SupervisorViewModel.class);

        // observes if profile is complete. If that is the case moves to the theses manager screen and enables the buttons
        final Observer<Boolean> profileCreatedObserver = profileCreated -> {

            if (profileCreated) {
                bottomNavigationView.getMenu().findItem(R.id.thesisManagerFragment).setEnabled(true);
                bottomNavigationView.setSelectedItemId(R.id.thesisManagerFragment);
            } else {
                bottomNavigationView.getMenu().findItem(R.id.thesisManagerFragment).setEnabled(false);
            }

        };

        viewModel.getProfileComplete().observe(this, profileCreatedObserver);

    }

    /**
     * signals to the {@link SupervisorViewModel} that the profile is complete
     */
    public void profileCompleted() {
        viewModel.setProfileComplete(true);
    }

    /**
     * Prevents user from going back to the login screen when pressing the back button
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
