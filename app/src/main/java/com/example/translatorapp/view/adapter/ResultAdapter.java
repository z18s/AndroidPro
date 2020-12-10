package com.example.translatorapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.SearchResult;
import com.example.translatorapp.view.item.IResultItemView;

import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> implements ILogger {

    private static final String TAG = ResultAdapter.class.getSimpleName();

    private List<SearchResult> data;

    public void setData(DataModel dataModel) {
        showVerboseLog(TAG, "setData - " + dataModel.getStatus().toString());
        data = ((DataModel.Success)dataModel).getData();
        notifyDataSetChanged();
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
        });
        SearchResult searchResult = data.get(position);
        setRecyclerData(holder, searchResult);
    }

    private void setRecyclerData(IResultItemView view, SearchResult searchResult) {
        showVerboseLog(TAG, "setRecyclerData");

        searchResult.getTextObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String headerText) {
                view.setHeader(headerText);
                view.setDescription(searchResult.getMeanings().get(0).getTranslation().getTranslation());
                showVerboseLog(TAG, "setRecyclerData.onNext - " + headerText);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
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