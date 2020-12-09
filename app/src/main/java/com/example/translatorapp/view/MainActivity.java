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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.logger.ILogger;
import com.example.translatorapp.model.data.DataModel;
import com.example.translatorapp.model.data.DataStatus;
import com.example.translatorapp.presenter.MainPresenter;
import com.example.translatorapp.view.adapter.ResultAdapter;

public class MainActivity<T extends DataModel> extends AppCompatActivity implements IMainView, ILogger {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter<T, IMainView> presenter;
    private ResultAdapter adapter;

    private SearchView searchView;
    private Button button;
    private RecyclerView recyclerView;

    private FrameLayout successLayout;
    private FrameLayout loadingLayout;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showVerboseLog(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter<>();
        init();
        showVerboseLog(TAG, "onCreate - DONE");
    }

    @Override
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
            presenter.onClick(word, true);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String word) {
                showVerboseLog(TAG, "searchView.onQueryTextSubmit");
                hideKeypad();
                presenter.onClick(word, true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showVerboseLog(TAG, "searchView.onQueryTextChange");
                return false;
            }
        });
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new ResultAdapter(presenter.getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void hideKeypad() {
        searchView.clearFocus();
    }

    @Override
    protected void onStart() {
        showVerboseLog(TAG, "onStart");
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        showVerboseLog(TAG, "onStop");
        super.onStop();
        presenter.detachView(this);
    }

    @Override
    public void updateData(DataStatus status, Integer progress, String text) {
        showVerboseLog(TAG, "updateData");
        if (status == DataStatus.SUCCESS) {
            showViewSuccess();
            adapter.notifyDataSetChanged();
        } else if (status == DataStatus.EMPTY) {
            showErrorScreen(getString(R.string.empty_server_response_on_success));
            adapter.notifyDataSetChanged();
        } else if (status == DataStatus.LOADING) {
            showViewLoading();
            ProgressBar progressBarHorizontal = findViewById(R.id.progress_bar_horizontal);
            ProgressBar progressBarRound = findViewById(R.id.progress_bar_round);
            if (progress != 0) {
                progressBarHorizontal.setVisibility(View.VISIBLE);
                progressBarRound.setVisibility(View.GONE);
                progressBarHorizontal.setProgress(progress);
            } else {
                progressBarHorizontal.setVisibility(View.GONE);
                progressBarRound.setVisibility(View.VISIBLE);
            }
        } else if (status == DataStatus.ERROR) {
            showErrorScreen(text);
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