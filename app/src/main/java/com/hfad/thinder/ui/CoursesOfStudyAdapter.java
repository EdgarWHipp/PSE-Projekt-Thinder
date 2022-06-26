package com.hfad.thinder.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;

import java.util.ArrayList;

public class CoursesOfStudyAdapter extends RecyclerView.Adapter<CoursesOfStudyAdapter.CoursesOfStudyViewHolder> {

    private ArrayList<CourseOfStudyItem> elements;

    public static class CoursesOfStudyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadioButton;

        public CoursesOfStudyViewHolder(@NonNull View itemView) {
            super(itemView);
            mRadioButton = itemView.findViewById(R.id.rbCoursesOfStudy);
        }
    }

    public CoursesOfStudyAdapter(ArrayList<CourseOfStudyItem> elements) {
        this.elements = elements;
    }

    @NonNull
    @Override
    public CoursesOfStudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courses_of_study, parent, false);
        CoursesOfStudyViewHolder coursesOfStudyViewHolder = new CoursesOfStudyViewHolder(v);
        return coursesOfStudyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesOfStudyViewHolder holder, int position) {
        CourseOfStudyItem current = elements.get(position);
        holder.mRadioButton.setText(current.getCourseOfStudy());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }
}
