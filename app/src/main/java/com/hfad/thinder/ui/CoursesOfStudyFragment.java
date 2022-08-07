package com.hfad.thinder.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentCoursesOfStudyBinding;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.student.EditProfileViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class CoursesOfStudyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<CourseOfStudyItem> elements;

    private RecyclerView mRecyclerView;
    private CoursesOfStudyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FragmentCoursesOfStudyBinding binding;
    protected CoursesOfStudyPicker viewModel;

    private View view;

    public CoursesOfStudyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mRecyclerView = view.findViewById(R.id.rvCoursesOfStudy);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new CoursesOfStudyAdapter(viewModel);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final Observer<ArrayList<CourseOfStudyItem>> coursesOfStudyItemsObserver = new Observer<ArrayList<CourseOfStudyItem>>() {
            @Override
            public void onChanged(ArrayList<CourseOfStudyItem> courseOfStudyItems) {
                if(!mRecyclerView.isComputingLayout())
                    mAdapter.setElements(viewModel.getCoursesOfStudyList().getValue());
            }
        };
        viewModel.getCoursesOfStudyList().observe(getViewLifecycleOwner(), coursesOfStudyItemsObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courses_of_study, container, false);
        binding.setFragment(this);
        // Inflate the layout for this fragment
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void save(View view){
        Navigation.findNavController(view).popBackStack();
    }
}