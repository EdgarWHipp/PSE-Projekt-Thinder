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

public class ThesisCardAdapter extends RecyclerView.Adapter<ThesisCardAdapter.ThesisManagerViewHolder> implements Filterable {

    private ArrayList<ThesisCardItem> elements;
    private ArrayList<ThesisCardItem> elementsFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
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

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ThesisCardAdapter(ArrayList<ThesisCardItem> elements) {
        this.elements = elements;
        elementsFull = new ArrayList<>(elements);
    }

    @NonNull
    @Override
    public ThesisCardAdapter.ThesisManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thesis_manager, parent, false);
        ThesisCardAdapter.ThesisManagerViewHolder coursesOfStudyViewHolder = new ThesisCardAdapter.ThesisManagerViewHolder(v, listener);
        return coursesOfStudyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThesisCardAdapter.ThesisManagerViewHolder holder, int position) {
        ThesisCardItem current = elements.get(position);
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

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            elements.clear();
            elements.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
