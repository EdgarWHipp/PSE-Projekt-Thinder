package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupervisorViewModel extends ViewModel {
    // used to dynamically update the title of the appbar depending on the fragment that is currently on top
    private MutableLiveData<String> appBarTitle;
}
