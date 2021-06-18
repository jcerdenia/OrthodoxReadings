package com.joshuacerdenia.android.orthodoxreadings;

import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setupToolbar(toolbar);

        scrollView = findViewById(R.id.scroll_view);
        progressBar = findViewById(R.id.progress_bar);
        titleTextView = findViewById(R.id.title_text_view);
        contentTextView = findViewById(R.id.content_text_view);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.requestReadings();
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.primary_text_dark));
        toolbar.setOnClickListener(l -> scrollView.smoothScrollTo(0, 0));
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getReading().observe(this, reading -> {
            updateUI(reading.title, reading.content);
        });
    }

    private void updateUI(String title, String content) {
        Spanned htmlContent = HtmlCompat.fromHtml(content, 0);
        titleTextView.setText(title);
        contentTextView.setText(htmlContent);
        progressBar.setVisibility(View.GONE);
    }
}