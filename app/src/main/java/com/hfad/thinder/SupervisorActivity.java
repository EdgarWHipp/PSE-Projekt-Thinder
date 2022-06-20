package com.hfad.thinder;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfad.thinder.databinding.ActivitySupervisorBinding;

public class SupervisorActivity extends AppCompatActivity {

    private ActivitySupervisorBinding mBinding;
    private Toolbar myChildToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_supervisor);
        BottomNavigationView bottomNavigationView = mBinding.bottomNavigationView;
        NavController navController = findNavController(this, R.id.supervisorFragmentContainer);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        // my_child_toolbar is defined in the layout file
        myChildToolbar = mBinding.toolbar;

        setSupportActionBar(myChildToolbar);
    }

    /**
     * Sets the title of the actionbar referenced in the xml layout
     *
     * @param title new title
     */
    public void setActionBarTitle(String title) {
        myChildToolbar.setTitle(title);
    }
}
