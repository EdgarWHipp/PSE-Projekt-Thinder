package com.hfad.thinder.ui;

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
import com.hfad.thinder.databinding.ActivitySupervisorBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.viewmodels.supervisor.SupervisorViewModel;

public class SupervisorActivity extends AppCompatActivity {

    private ActivitySupervisorBinding mBinding;
    private Toolbar myChildToolbar;

    private SupervisorViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_supervisor);
        // my_child_toolbar is defined in the layout file
        myChildToolbar = mBinding.toolbar;

        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;

        NavController navController = findNavController(this, R.id.supervisorFragmentContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.supervisorProfileFragment, R.id.thesisManagerFragment).build();

        setSupportActionBar(myChildToolbar);
        NavigationUI.setupWithNavController(myChildToolbar, navController, appBarConfiguration);

        viewmodel = new ViewModelProvider(this).get(SupervisorViewModel.class);

        final Observer<Boolean> profileCreatedObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean profileCreated) {

                if(profileCreated){
                    bottomNavigationView.getMenu().findItem(R.id.thesisManagerFragment).setEnabled(true);
                    bottomNavigationView.setSelectedItemId(R.id.thesisManagerFragment);
                }else {
                    bottomNavigationView.getMenu().findItem(R.id.thesisManagerFragment).setEnabled(false);
                }

            }
        };

        viewmodel.getProfileComplete().observe(this, profileCreatedObserver);

    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }


}
