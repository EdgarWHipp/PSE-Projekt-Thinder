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
import com.hfad.thinder.viewmodels.supervisor.SupervisorViewModel;

public class StudentActivity extends AppCompatActivity {

    private ActivityStudentBinding mBinding;
    private Toolbar myChildToolbar;

    private StudentViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_student);
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;
        NavController navController = findNavController(this, R.id.studentFragmentContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // my_child_toolbar is defined in the layout file
        myChildToolbar = mBinding.toolbar;

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.studentProfileFragment, R.id.swipeScreenFragment, R.id.likedThesesFragment).build();

        setSupportActionBar(myChildToolbar);
        NavigationUI.setupWithNavController(myChildToolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.swipeScreenFragment) {
                    myChildToolbar.setVisibility(View.GONE);
                    mBinding.separator.setVisibility(View.GONE);
                } else {
                    myChildToolbar.setVisibility(View.VISIBLE);
                    mBinding.separator.setVisibility(View.VISIBLE);
                }
            }
        });

        viewmodel = new ViewModelProvider(this).get(StudentViewModel.class);

        final Observer<Boolean> profileCreatedObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean profileCreated) {

                if(profileCreated){
                    bottomNavigationView.getMenu().findItem(R.id.likedThesesFragment).setEnabled(true);
                    bottomNavigationView.getMenu().findItem(R.id.swipeScreenFragment).setEnabled(true);
                    bottomNavigationView.setSelectedItemId(R.id.likedThesesFragment);
                }else {
                    bottomNavigationView.getMenu().findItem(R.id.likedThesesFragment).setEnabled(false);
                    bottomNavigationView.getMenu().findItem(R.id.swipeScreenFragment).setEnabled(false);
                }

            }
        };

        viewmodel.getProfileComplete().observe(this, profileCreatedObserver);
    }

    public void setActionBarTitle(String title){
        myChildToolbar.setTitle(title);
    }

    public void profileCompleted(){
        viewmodel.setProfileComplete(true);
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }



}
