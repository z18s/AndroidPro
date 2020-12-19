package com.example.translatorapp.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.application.TranslatorApp;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.view.adapter.HistoryAdapter;
import com.example.translatorapp.viewmodel.HistoryViewModel;

import java.util.List;

import javax.inject.Inject;

public class HistoryActivity extends AppCompatActivity implements ILogger {

    private static final String TAG = HistoryActivity.class.getSimpleName();

    private HistoryViewModel viewModel;
    private HistoryAdapter adapter;

    private RecyclerView recyclerView;

    private List<String> list;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        showVerboseLog(TAG, "onCreate");

        TranslatorApp.instance.getComponent().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(HistoryViewModel.class);
        init();

        showVerboseLog(TAG, "onCreate - DONE");
    }

    public void init() {
        showVerboseLog(TAG, "init");
        setActionbarHomeButtonAsUp();
        initViews();
        initAdapter();
        setData();
        showVerboseLog(TAG, "init - DONE");
    }

    private void initViews() {
        recyclerView = findViewById(R.id.history_activity_recyclerview);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new HistoryAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setActionbarHomeButtonAsUp() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setData() {
        list = viewModel.getHistoryData().getValue();
        adapter.setData(list);
    }
}