package com.example.translatorapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.application.TranslatorApp;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.DataStatus;
import com.example.translatorapp.utils.NetworkUtils;
import com.example.translatorapp.view.adapter.ResultAdapter;
import com.example.translatorapp.viewmodel.MainViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements ILogger {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel;
    private ResultAdapter adapter;
    private Observer<DataModel> observer;

    private SearchView searchView;
    private Button button;
    private RecyclerView recyclerView;

    private FrameLayout successLayout;
    private FrameLayout loadingLayout;
    private LinearLayout errorLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showVerboseLog(TAG, "onCreate");

        TranslatorApp.instance.getComponent().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        observer = this::updateData;
        init();

        showVerboseLog(TAG, "onCreate - DONE");
    }

    public void init() {
        showVerboseLog(TAG, "init");
        initViews();
        initLayouts();
        initListeners();
        initAdapter();
        showVerboseLog(TAG, "init - DONE");
    }

    private void initViews() {
        searchView = findViewById(R.id.searchview_search);
        searchView.setIconifiedByDefault(false);
        button = findViewById(R.id.button_search);
        recyclerView = findViewById(R.id.main_activity_recyclerview);
    }

    private void initLayouts() {
        successLayout = findViewById(R.id.success_linear_layout);
        loadingLayout = findViewById(R.id.loading_frame_layout);
        errorLayout = findViewById(R.id.error_linear_layout);
    }

    private void initListeners() {
        button.setOnClickListener((view) -> {
            showVerboseLog(TAG, "button.OnClickListener");
            String word = searchView.getQuery().toString();
            hideKeypad();
            observeData(word);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String word) {
                showVerboseLog(TAG, "searchView.onQueryTextSubmit");
                hideKeypad();
                observeData(word);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showVerboseLog(TAG, "searchView.onQueryTextChange");
                return false;
            }
        });
    }

    private void observeData(String word) {
        viewModel.getData(word, true).observe(this, observer);
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new ResultAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void hideKeypad() {
        searchView.clearFocus();
    }

    public void updateData(DataModel data) {
        showVerboseLog(TAG, "updateData");
        DataStatus status = data.getStatus();
        if (status == DataStatus.SUCCESS) {
            showViewSuccess();
            adapter.setData(data);
        } else if (status == DataStatus.EMPTY) {
            showErrorScreen(getString(R.string.empty_server_response_on_success));
        } else if (status == DataStatus.LOADING) {
            showViewLoading();
            ProgressBar progressBarHorizontal = findViewById(R.id.progress_bar_horizontal);
            ProgressBar progressBarRound = findViewById(R.id.progress_bar_round);
            int progress = ((DataModel.Loading)data).getProgress();
            if (progress != 0) {
                progressBarHorizontal.setVisibility(View.VISIBLE);
                progressBarRound.setVisibility(View.GONE);
                progressBarHorizontal.setProgress(progress);
            } else {
                progressBarHorizontal.setVisibility(View.GONE);
                progressBarRound.setVisibility(View.VISIBLE);
            }
        } else if (status == DataStatus.ERROR) {
            String error = ((DataModel.Error)data).getError().getMessage();
            showErrorScreen(error);
        }
    }

    private void showErrorScreen(String errorText) {
        showViewError();
        TextView errorTextView = findViewById(R.id.error_textview);
        errorTextView.setText((errorText != null) ? errorText : getString(R.string.undefined_error));
    }

    private void showViewSuccess() {
        successLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private void showViewLoading() {
        successLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    private void showViewError() {
        successLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }
}