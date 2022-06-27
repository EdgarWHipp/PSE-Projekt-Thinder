package com.hfad.thinder.ui;

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

import java.util.ArrayList;

public class ThesisManagerAdapter extends RecyclerView.Adapter<ThesisManagerAdapter.ThesisManagerViewHolder> implements Filterable {

    private ArrayList<ThesisManagerItem> elements;
    private ArrayList<ThesisManagerItem> elementsFull;

    public static class ThesisManagerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;

        public ThesisManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            image = itemView.findViewById(R.id.ivThesisManagerItem);
        }
    }

    public ThesisManagerAdapter(ArrayList<ThesisManagerItem> elements) {
        this.elements = elements;
        elementsFull = new ArrayList<>(elements);
    }

    @NonNull
    @Override
    public ThesisManagerAdapter.ThesisManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thesis_manager, parent, false);
        ThesisManagerAdapter.ThesisManagerViewHolder coursesOfStudyViewHolder = new ThesisManagerAdapter.ThesisManagerViewHolder(v);
        return coursesOfStudyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThesisManagerAdapter.ThesisManagerViewHolder holder, int position) {
        ThesisManagerItem current = elements.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        holder.image.setImageResource(current.getImage());
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
            ArrayList<ThesisManagerItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(elementsFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ThesisManagerItem item : elementsFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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

}
