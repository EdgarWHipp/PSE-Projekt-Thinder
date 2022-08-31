package com.hfad.thinder.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;

import java.util.ArrayList;

/**
 *  Is responsible for displaying the given data by the ViewModel in a RecyclerView.
 *  Also handles click events and filter operations.
 */
public class CoursesOfStudyAdapter extends RecyclerView.Adapter<CoursesOfStudyAdapter.CoursesOfStudyViewHolder> implements Filterable {

    private ArrayList<CourseOfStudyItem> elements;
    private ArrayList<CourseOfStudyItem> elementsFull;
    private CoursesOfStudyPicker viewModel;

    private Filter elementsFilter = new Filter() {
        /**
         * returns all objects corresponding to the given charSequence
         *
         * @param charSequence filter input
         * @return             filtered output
         */
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

        /**
         * Once filtering is completed this method performs the actual data change
         *
         * @param charSequence  filter input
         * @param filterResults results of filtering
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            elements.clear();
            elements.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Constructor
     *
     * @param viewModel allows access to the actual data
     */
    public CoursesOfStudyAdapter(CoursesOfStudyPicker viewModel) {
        this.viewModel = viewModel;
        this.elements = new ArrayList<>();
        this.elements.addAll(viewModel.getElements());
        elementsFull = new ArrayList<>(elements);
    }

    /**
     * Creates new viewholder
     *
     * @param parent    ParentViewGroup
     * @param viewType  type of the view
     * @return          CourseOfStudyViewHolder
     */
    @Override
    public CoursesOfStudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courses_of_study, parent, false);
        CoursesOfStudyViewHolder coursesOfStudyViewHolder = new CoursesOfStudyViewHolder(v);
        return coursesOfStudyViewHolder;
    }

    /**
     * Binds data to views
     *
     * @param holder    viewholder holding the views
     * @param position  position used to get the data
     */
    @Override
    public void onBindViewHolder(@NonNull CoursesOfStudyViewHolder holder, int position) {
        int currentPosition = position;
        CourseOfStudyItem current = elements.get(position);
        holder.mCheckBox.setText(current.getCourseOfStudy());
        holder.mCheckBox.setChecked(current.isPicked());

        holder.setItemClickListener(new CoursesOfStudyViewHolder.ItemClickListener(){
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox checkBox = (CheckBox) v;
                viewModel.makeCourseOfStudySelection(elements.get(currentPosition).getCourseOfStudy(), checkBox.isChecked());
            }
        });
    }

    /**
     * returns number of elements in recyclerview
     *
     * @return Integer
     */
    @Override
    public int getItemCount() {
        return elements.size();
    }

    /**
     * returns filter
     *
     * @return Filter
     */
    @Override
    public Filter getFilter() {
        return elementsFilter;
    }

    /**
     * Sets elements of recyclerview and notifies about changed data
     *
     * @param elements new elements
     */
    public void setElements(ArrayList<CourseOfStudyItem> elements) {
        this.elements = elements;
        notifyDataSetChanged();
    }

    /**
     *  Holds all the views of one recyclerview item. Implements OnClickListener to recognize clicks
     *  to the views
     */
    public static class CoursesOfStudyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox mCheckBox;

        ItemClickListener itemClickListener;

        /**
         * Constructor
         *
         * @param itemView view of viewholder
         */
        public CoursesOfStudyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCheckBox = itemView.findViewById(R.id.cbCoursesOfStudy);

            mCheckBox.setOnClickListener(this);
        }

        /**
         * Used for observer pattern. Registers ItemClickListener
         *
         * @param ic itemclicklistener
         */
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }

        /**
         * Allows recognition of click input
         *
         * @param view object that has been clicked
         */
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        /**
         *  used for handling click events
         */
        private interface ItemClickListener {
            void onItemClick(View v, int pos);
        }
    }
}
