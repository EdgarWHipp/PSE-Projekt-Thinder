package com.hfad.thinder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;

import java.util.ArrayList;

/**
 * Abstract Fragment class that handles binding and layout inflation for the course of study
 * selection screen.
 */
public abstract class CoursesOfStudyFragment extends Fragment {

    protected CoursesOfStudyPicker viewModel;

    private RecyclerView mRecyclerView;
    private CoursesOfStudyAdapter mAdapter;

    /**
     * Required empty public constructor
     */
    public CoursesOfStudyFragment() {
        // Required empty public constructor
    }

    /**
     * Called after onCreateView. Sets up the {@link RecyclerView}. Observes Changes of the course
     * of study list in the {@link CoursesOfStudyPicker} ViewModel
     *
     * @param view                  view returned by {@link CoursesOfStudyFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mRecyclerView = view.findViewById(R.id.rvCoursesOfStudy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new CoursesOfStudyAdapter(viewModel);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final Observer<ArrayList<CourseOfStudyItem>> coursesOfStudyItemsObserver = new Observer<ArrayList<CourseOfStudyItem>>() {
            @Override
            public void onChanged(ArrayList<CourseOfStudyItem> courseOfStudyItems) {
                if (!mRecyclerView.isComputingLayout() && !mRecyclerView.isShown())
                    mAdapter.setElements(viewModel.getCoursesOfStudyList().getValue());
            }
        };
        viewModel.getCoursesOfStudyList().observe(getViewLifecycleOwner(), coursesOfStudyItemsObserver);
    }

    /**
     * Handles layout inflation. Connects binding with fragment.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        com.hfad.thinder.databinding.FragmentCoursesOfStudyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courses_of_study, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }



    /**
     * Sets up the top action bar for {@link RecyclerView} search actions.
     *
     * @param menu      options menu in which items are placed
     * @param inflater  inflates menu
     */
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

    /**
     * Moves to previous screen
     *
     * @param view  used to find fragment in the NavController
     */
    public void save(View view) {
        Navigation.findNavController(view).popBackStack();
    }
}