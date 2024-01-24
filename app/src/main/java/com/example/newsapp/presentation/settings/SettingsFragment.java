package com.example.newsapp.presentation.settings;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.presentation.root.RootActivity;
import com.example.newsapp.presentation.settings.adapter.ThemeAdapter;
import com.example.newsapp.presentation.settings.view_model.SettingsViewModel;
import com.example.newspractice.R;
import com.example.newspractice.databinding.FragmentSettingsBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private SettingsViewModel viewModel;

    public SettingsFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        viewModel.refreshThemeList();
        configureBottomSheetBehavior();
        configureResultsList();
        setScreenStateObserver();
        configureThemeCreation();
        setNeedToRefreshObserver();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureToolbar();
    }

    private void configureToolbar() {
        Toolbar toolbar = ((RootActivity) requireActivity()).getToolbar();
        Menu menu = toolbar.getMenu();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        MenuItem settings = menu.findItem(R.id.settings);

        settings.setVisible(false);

        MenuItem addTheme = menu.findItem(R.id.add_theme);
        addTheme.setVisible(true);

        addTheme.setOnMenuItemClickListener(item -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            binding.header.setText(R.string.theme_creation);
            binding.accept.setText(R.string.create);
            return true;
        });
    }

    private void configureBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        binding.shadow.setVisibility(GONE);
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        binding.shadow.setVisibility(VISIBLE);
                        break;
                    }
                    default: {
                        // do nothing
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // do nothing
            }
        };

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void configureResultsList() {
        ThemeAdapter adapter = new ThemeAdapter(
                themeItem -> {
                    viewModel.showDeleteDialog(requireContext(), themeItem).show();
                },
                themeItem -> {
                    binding.header.setText(R.string.theme_editing);
                    binding.accept.setText(R.string.save);
                    binding.themeValue.setText(themeItem.theme);
                    viewModel.saveEditing(themeItem);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
        );
        binding.themeList.setAdapter(adapter);
    }

    private void setScreenStateObserver() {
        viewModel.getScreenState().observe(getViewLifecycleOwner(), themeItemList -> {
            if (themeItemList.isEmpty()) {
                binding.themeList.setVisibility(GONE);
                binding.noContent.setVisibility(VISIBLE);
            } else {
                binding.themeList.setVisibility(VISIBLE);
                binding.noContent.setVisibility(GONE);
                ThemeAdapter adapter = (ThemeAdapter) binding.themeList.getAdapter();
                assert adapter != null;
                adapter.setContent(themeItemList);
            }
        });
    }

    private void configureThemeCreation() {
        binding.accept.setOnClickListener(view -> {
            String value = String.valueOf(binding.themeValue.getText());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            viewModel.saveTheme(value);
            binding.themeValue.setText("");
        });

        EditText editText = binding.themeValue;
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });
        editText.addTextChangedListener(provideTextWatcher());
    }

    private void setNeedToRefreshObserver() {
        viewModel.getNeedToRefresh().observe(getViewLifecycleOwner(), needToRefresh -> {
            if (needToRefresh) {
                ((RootActivity) requireActivity()).needToRefresh = true;
            }
        });
    }

    @NonNull
    private TextWatcher provideTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // doNothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().trim().isEmpty()) {
                    binding.accept.setEnabled(viewModel.checkForRepeats(String.valueOf(s)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        };
    }
}
