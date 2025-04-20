package com.gpwsofts.ffcalculator.mobile.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentHelpBinding;

/**
 * Fragment Aide
 * @since 1.0.0
 */
public class HelpFragment extends Fragment {
    private static final String TAG_NAME = "HelpFragment";
    private FragmentHelpBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.onCreateBegin(TAG_NAME);
        super.onCreate(savedInstanceState);
        LogUtils.onCreateEnd(TAG_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.onCreateViewBegin(TAG_NAME);
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // binding
        WebView myWebView = binding.webview;
        // Activer JavaScript dans la WebView
        myWebView.getSettings().setJavaScriptEnabled(true);
        // Charger une URL
        myWebView.loadUrl(BuildConfig.HELP_BASE_URL);
        // Optionnel : Pour ouvrir les liens dans la même WebView au lieu du navigateur
        myWebView.setWebViewClient(new WebViewClient());
        LogUtils.onCreateViewEnd(TAG_NAME);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.onViewCreatedBegin(TAG_NAME);
        super.onViewCreated(view, savedInstanceState);
        LogUtils.onViewCreatedEnd(TAG_NAME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}