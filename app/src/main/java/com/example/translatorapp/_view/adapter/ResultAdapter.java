package com.example.translatorapp._view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp._presenter.list.IResultListPresenter;
import com.example.translatorapp.view.item.IResultItemView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> implements ILogger {

    private static final String TAG = com.example.translatorapp.view.adapter.ResultAdapter.class.getSimpleName();

    private final IResultListPresenter presenter;

    public ResultAdapter(IResultListPresenter presenter) {
        this.presenter = presenter;
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
        holder.position = position;
        holder.itemView.setOnClickListener((view) -> {
            showVerboseLog(TAG, "itemView [" + position + "] Clicked");
            presenter.onItemClick(holder);
        });
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements IResultItemView {

        int position;
        TextView header;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header_textview_recycler_item);
            description = itemView.findViewById(R.id.description_textview_recycler_item);
        }

        @Override
        public void setHeader(String headerText) {
            header.setText(headerText);
        }

        @Override
        public void setDescription(String descriptionText) {
            description.setText(descriptionText);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}