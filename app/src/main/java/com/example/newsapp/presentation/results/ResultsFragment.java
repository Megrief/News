package com.example.newsapp.presentation.results;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.DateContainer;
import com.example.newsapp.domain.entity.Entity;
import com.example.newsapp.presentation.results.adapter.EntityAdapter;
import com.example.newsapp.presentation.results.adapter.EntityDiffCallback;
import com.example.newsapp.presentation.results.screen_state.ContentState;
import com.example.newsapp.presentation.results.screen_state.ErrorState;
import com.example.newsapp.presentation.results.view_model.ResultsViewModel;
import com.example.newsapp.presentation.root.RootActivity;
import com.example.newsapp.util.ErrorType;
import com.example.newspractice.R;
import com.example.newspractice.databinding.FragmentResultsBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultsFragment extends Fragment {

    private FragmentResultsBinding binding;
    private ResultsViewModel viewModel;

    public ResultsFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
        configureList();
        setupScreenStateObserver();
        configureFilterButton();
        binding.settingsButton.setOnClickListener(iv -> viewModel.showFiltersDialog(requireContext()));

    }

    @Override
    public void onResume() {
        super.onResume();
        configureToolbar();
        if (((RootActivity) requireActivity()).needToRefresh) {
            binding.message.setVisibility(GONE);
            binding.progressBar.setVisibility(VISIBLE);
            viewModel.refreshArticles();
            ((RootActivity) requireActivity()).needToRefresh = false;
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = ((RootActivity) requireActivity()).getToolbar();
        MenuItem settings = toolbar.getMenu().findItem(R.id.settings);
        settings.setVisible(true);
        toolbar.setNavigationIcon(null);
        toolbar.getMenu().findItem(R.id.add_theme).setVisible(false);
        settings.setOnMenuItemClickListener(item -> {
            findNavController(this).navigate(R.id.action_resultsFragment_to_settingsFragment);
            return true;
        });
    }

    private void configureList() {
        EntityAdapter articlesAdapter = new EntityAdapter(
                new EntityDiffCallback(),
                url -> {
                    NavDirections direction = ResultsFragmentDirections.actionResultsFragmentToWebViewFragment(url);
                    findNavController(this).navigate(direction);
                }
        );
        binding.articlesList.setAdapter(articlesAdapter);
    }

    private void onError(@NonNull ErrorType errorType) {
        binding.message.setVisibility(VISIBLE);

        String message;
        switch (errorType) {
            case SERVER_ERROR: {
                message = getString(R.string.server_error);
                break;
            }
            case NO_INTERNET: {
                message = getString(R.string.no_internet_connection);
                break;
            }
            default: {
                message = getString(R.string.nothing_found); //TODO customize message if filtersOn
            }
        }
        binding.message.setText(message);
    }

    private void onSuccess(@NonNull ContentState contentState) {
        binding.articlesList.setVisibility(VISIBLE);
        List<Entity> finalList = new ArrayList<>();
        String lastDate = "";
        for (Article article : contentState.articles) {
            if (!article.getPublishedAt().equals(lastDate)) {
                finalList.add(new DateContainer(article.getPublishedAt()));
                lastDate = article.getPublishedAt();
            }
            finalList.add(article);
        }
        EntityAdapter adapter = (EntityAdapter) binding.articlesList.getAdapter();
        assert adapter != null;
        adapter.setItems(finalList);
    }


    private void setupScreenStateObserver() {
        viewModel.getScreenState().observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(GONE);
            if (state instanceof ContentState) {
                onSuccess((ContentState) state);
            } else {
                onError(((ErrorState) state).errorType);
            }
        });
    }

    private void configureFilterButton() {
        viewModel.getFiltersState().observe(getViewLifecycleOwner(), filtersOn -> {
            if (filtersOn) {
                binding.settingsButton.setBackgroundTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.filters_on_color)
                );
            } else {
                binding.settingsButton.setBackgroundTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.secondary));
            }
        });
    }
}
