package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.thinder.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesOfStudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesOfStudyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<CourseOfStudyItem> elements;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View view;

    public CoursesOfStudyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursesOfStudyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesOfStudyFragment newInstance(String param1, String param2) {
        CoursesOfStudyFragment fragment = new CoursesOfStudyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        elements = new ArrayList<CourseOfStudyItem>();
        elements.add(new CourseOfStudyItem("Informatik"));
        elements.add(new CourseOfStudyItem("Mathematik"));
        elements.add(new CourseOfStudyItem("Biologie"));
        elements.add(new CourseOfStudyItem("Physik"));
        elements.add(new CourseOfStudyItem("Elektrotechnik"));
        elements.add(new CourseOfStudyItem("Maschinenbau"));

        mRecyclerView = view.findViewById(R.id.rvCoursesOfStudy);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new CoursesOfStudyAdapter(elements);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_courses_of_study, container, false);
        return view;
    }
}