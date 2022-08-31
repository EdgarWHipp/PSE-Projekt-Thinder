package com.hfad.thinder.ui;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.ThesisCardItem;

import java.util.ArrayList;
import java.util.Locale;

public class ThesisCardAdapter
        extends RecyclerView.Adapter<ThesisCardAdapter.ThesisManagerViewHolder> implements Filterable {

    private ArrayList<ThesisCardItem> elementsFull;
    private ArrayList<ThesisCardItem> elements;
    private final Filter elementsFilter = new Filter() {
        /**
         * returns all objects corresponding to the given charSequence
         *
         * @param charSequence filter input
         * @return             filtered output
         */
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ThesisCardItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(elementsFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ThesisCardItem item : elementsFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
            ArrayList<ThesisCardItem> filteredList = (ArrayList) filterResults.values;
            Log.i(TAG, "publishResults: " + filteredList.size());
            elements.clear();
            elements.addAll(filteredList);
            notifyDataSetChanged();
        }
    };
    private OnItemClickListener listener;

    public ThesisCardAdapter(ArrayList<ThesisCardItem> elements) {
        if (elements != null && !(elements.isEmpty())) {
            this.elements = new ArrayList<>(elements);
            elementsFull = new ArrayList<>(elements);
            Log.i(TAG, "ThesisCardAdapter: " + elementsFull.size());
        } else {
            this.elements = new ArrayList<>();
            elementsFull = new ArrayList<>();
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThesisCardAdapter.ThesisManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thesis_manager, parent, false);
        ThesisCardAdapter.ThesisManagerViewHolder coursesOfStudyViewHolder =
                new ThesisCardAdapter.ThesisManagerViewHolder(v, listener);
        return coursesOfStudyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThesisCardAdapter.ThesisManagerViewHolder holder,
                                 int position) {
        ThesisCardItem current = elements.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getTask());
        holder.image.setImageBitmap(current.getImage());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @Override
    public Filter getFilter() {
        return elementsFilter;
    }

    public void setElements(ArrayList<ThesisCardItem> elements) {
        if(elements != null){
            this.elements = (ArrayList<ThesisCardItem>) elements.clone();
            elementsFull = (ArrayList<ThesisCardItem>) elements.clone();
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ThesisManagerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;

        public ThesisManagerViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            image = itemView.findViewById(R.id.ivThesisManagerItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
