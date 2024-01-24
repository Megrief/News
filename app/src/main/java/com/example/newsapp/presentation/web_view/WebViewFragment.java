package com.example.newsapp.presentation.web_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.newsapp.presentation.root.RootActivity;
import com.example.newspractice.R;
import com.example.newspractice.databinding.FragmentWebViewBinding;

public class WebViewFragment extends Fragment {

    private FragmentWebViewBinding binding;

    public WebViewFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWebViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = WebViewFragmentArgs.fromBundle(getArguments()).getUrl();
        WebView webView = binding.getRoot();
        assert url != null;
        webView.loadUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        configureToolbar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((RootActivity) requireActivity())
                .getToolbar()
                .setNavigationIcon(null);
    }

    public void configureToolbar() {
        Toolbar toolbar = ((RootActivity) requireActivity()).getToolbar();
        MenuItem settings = toolbar.getMenu().findItem(R.id.settings);
        settings.setVisible(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
    }
}
