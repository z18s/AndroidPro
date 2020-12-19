package com.example.translatorapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.view.MainActivity;
import com.example.translatorapp.view.item.IHistoryItemView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.example.translatorapp.view.BundleConstants.HISTORY_EXTRA;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements ILogger {

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private List<String> list;

    public void setData(List<String> list) {
        showVerboseLog(TAG, "setData - " + list);
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View historyView = inflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(historyView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;
        String word = list.get(position);

        holder.itemView.setOnClickListener((view) -> {
            showVerboseLog(TAG, "itemView [" + position + "] Clicked");
            startActivity(view, word);
        });

        setRecyclerData(holder, word);
    }

    private void startActivity(View view, String word) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        Bundle args = new Bundle();
        args.putString(HISTORY_EXTRA, word);
        intent.putExtras(args);
        view.getContext().startActivity(intent);
    }

    private void setRecyclerData(IHistoryItemView view, String word) {
        showVerboseLog(TAG, "setRecyclerData");

        Observable.just(word).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String headerText) {
                view.setHeader(headerText);
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
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements IHistoryItemView {

        int position;
        TextView header;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.header_history_textview_recycler_item);
        }

        @Override
        public void setHeader(String headerText) {
            header.setText(headerText);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}