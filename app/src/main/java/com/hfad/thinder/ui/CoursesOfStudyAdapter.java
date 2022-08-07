package com.hfad.thinder.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;

import java.util.ArrayList;

public class CoursesOfStudyAdapter extends RecyclerView.Adapter<CoursesOfStudyAdapter.CoursesOfStudyViewHolder> implements Filterable {

    private ArrayList<CourseOfStudyItem> elements;
    private ArrayList<CourseOfStudyItem> elementsFull;
    private CoursesOfStudyPicker viewModel;


    public static class CoursesOfStudyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mCheckBox;

        public CoursesOfStudyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.cbCoursesOfStudy);
        }

    }

    public CoursesOfStudyAdapter(CoursesOfStudyPicker viewModel) {
        this.viewModel = viewModel;
        this.elements = new ArrayList<>();
        this.elements.addAll(viewModel.getElements());
        elementsFull = new ArrayList<>(elements);
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
        int currentPosition = position;
        CourseOfStudyItem current = elements.get(position);
        holder.mCheckBox.setText(current.getCourseOfStudy());
        holder.mCheckBox.setChecked(current.isPicked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewModel.makeCourseOfStudySelection(currentPosition, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @Override
    public Filter getFilter() {
        return elementsFilter;
    }

    private Filter elementsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<CourseOfStudyItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(elementsFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (CourseOfStudyItem item : elementsFull) {
                    if (item.getCourseOfStudy().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            elements.clear();
            elements.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void setElements(ArrayList<CourseOfStudyItem> elements){
        this.elements = elements;
        notifyDataSetChanged();
    }
}
