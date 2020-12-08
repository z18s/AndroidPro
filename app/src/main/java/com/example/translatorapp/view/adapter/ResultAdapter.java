package com.example.translatorapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.model.data.SearchResult;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<SearchResult> data;

    public ResultAdapter(List<SearchResult> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.item_result, parent, false);
        return new ViewHolder(titleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<SearchResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements IResultItemView {

        TextView header;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header_textview_recycler_item);
            description = itemView.findViewById(R.id.description_textview_recycler_item);
        }

        @Override
        public void bind(SearchResult data) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                header.setText(data.getText());
                description.setText(data.getMeanings().get(0).getTranslation().getTranslation());
            }
        }
    }
}