package com.hfad.thinder.ui.student;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.ActivityStudentBinding;
import com.hfad.thinder.viewmodels.student.StudentViewModel;

/**
 * Acts as the main ui element in the student screen. Handles the correct display of the fragments
 * showing the actual content.
 */
public class StudentActivity extends AppCompatActivity {

    private ActivityStudentBinding mBinding;
    private Toolbar topActionBar;

    private StudentViewModel viewModel;

    /**
     * Called upon creation of the {@link StudentActivity}. Acts as a constructor sets up the member variables and inflates the layout.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_student);

        // setting up the bottom navigation bar
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;
        NavController navController = findNavController(this, R.id.studentFragmentContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // setting up the top action bar
        topActionBar = mBinding.toolbar;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.studentProfileFragment, R.id.swipeScreenFragment, R.id.likedThesesFragment).build();
        setSupportActionBar(topActionBar);
        NavigationUI.setupWithNavController(topActionBar, navController, appBarConfiguration);

        // hides the top action bar if the displayed fragment is the swipe screen fragment
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.swipeScreenFragment) {
                topActionBar.setVisibility(View.GONE);
                mBinding.separator.setVisibility(View.GONE);
            } else {
                topActionBar.setVisibility(View.VISIBLE);
                mBinding.separator.setVisibility(View.VISIBLE);
            }
        });

        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        // observes if profile is complete. If that is the case moves to the swipe screen and enables the buttons
        final Observer<Boolean> profileCreatedObserver = profileCreated -> {

            if (profileCreated) {
                bottomNavigationView.getMenu().findItem(R.id.likedThesesFragment).setEnabled(true);
                bottomNavigationView.getMenu().findItem(R.id.swipeScreenFragment).setEnabled(true);
                bottomNavigationView.setSelectedItemId(R.id.swipeScreenFragment);
            } else {
                bottomNavigationView.getMenu().findItem(R.id.likedThesesFragment).setEnabled(false);
                bottomNavigationView.getMenu().findItem(R.id.swipeScreenFragment).setEnabled(false);
            }
        };

        viewModel.getProfileComplete().observe(this, profileCreatedObserver);
    }

    /**
     * signals to the {@link StudentViewModel} that the profile is complete
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
