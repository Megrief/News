package com.example.newsapp.presentation.root;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.newspractice.R;
import com.example.newspractice.databinding.ActivityRootBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RootActivity extends AppCompatActivity {

    private ActivityRootBinding binding;
    private NavController navController;
    public boolean needToRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRootBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        configureToolbar();

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        navController = Objects.requireNonNull(hostFragment).getNavController();
        navController.setGraph(R.navigation.news_nav_graph);
    }

    public Toolbar getToolbar() {
        return binding.toolbar;
    }

    private void configureToolbar() {

        MenuItem addItem = binding.toolbar.getMenu().findItem(R.id.add_theme);
        addItem.setVisible(false);

        binding.toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }
}