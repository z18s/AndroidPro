package com.example.translatorapp.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.translatorapp.R;
import com.example.translatorapp.utils.NetworkUtils;
import com.example.translatorapp.view.fragment.AlertDialogFragment;
import com.example.translatorapp.view.image.EquilateralImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.example.translatorapp.view.BundleConstants.DIALOG_FRAGMENT_TAG;
import static com.example.translatorapp.view.BundleConstants.WORD_EXTRA;
import static com.example.translatorapp.view.BundleConstants.DESCRIPTION_EXTRA;
import static com.example.translatorapp.view.BundleConstants.URL_EXTRA;

public class WordActivity extends AppCompatActivity {

    private TextView headerTextView;
    private TextView descriptionTextView;
    private EquilateralImageView imageView;

    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        setActionbarHomeButtonAsUp();

        initViews();
        setData();
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

    private void initViews() {
        headerTextView = findViewById(R.id.description_header);
        descriptionTextView = findViewById(R.id.description_text);
        imageView = findViewById(R.id.word_image);
        swipeLayout = findViewById(R.id.description_swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(this::startLoadingOrShowError);
    }

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        headerTextView.setText(bundle.getString(WORD_EXTRA));
        descriptionTextView.setText(bundle.getString(DESCRIPTION_EXTRA));
        String imageUrl = bundle.getString(URL_EXTRA);
        if (imageUrl == null || imageUrl.isEmpty()) {
            stopRefreshAnimationIfNeeded();
        } else {
            usePicassoToLoadPhoto(imageView, imageUrl);
        }
    }

    private void startLoadingOrShowError() {
        if (isOnline()) {
            setData();
        } else {
            AlertDialogFragment.newInstance(
                    getString(R.string.dialog_title_device_is_offline),
                    getString(R.string.dialog_message_device_is_offline)
            ).show(getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
            stopRefreshAnimationIfNeeded();
        }
    }

    private boolean isOnline() {
        return NetworkUtils.isOnline(getApplicationContext());
    }

    private void stopRefreshAnimationIfNeeded() {
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }

    private void usePicassoToLoadPhoto(ImageView imageView, String imageUrl) {
        Picasso.with(getApplicationContext()).load("https:" + imageUrl)
                .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        stopRefreshAnimationIfNeeded();
                    }

                    @Override
                    public void onError() {
                        stopRefreshAnimationIfNeeded();
                        imageView.setImageResource(R.drawable.ic_load_error_vector);
                    }
                });
    }
}