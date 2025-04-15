package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
//TODO 1.0.0 fragment a supprimer
public class SynthesisFragment extends Fragment {
    private static final String TAG_NAME = "SynthesisFragment";
    private FragmentSynthesisBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // binding
        WebView myWebView = binding.webview;
        // Activer JavaScript dans la WebView
        myWebView.getSettings().setJavaScriptEnabled(true);
        // Charger une URL
        myWebView.loadUrl("https://garnierpascalweb.fr/app/ffcalculator/");
        // Optionnel : Pour ouvrir les liens dans la même WebView au lieu du navigateur
        myWebView.setWebViewClient(new WebViewClient());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}