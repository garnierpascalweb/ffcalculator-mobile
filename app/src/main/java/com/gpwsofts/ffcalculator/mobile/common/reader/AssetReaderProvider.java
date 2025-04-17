package com.gpwsofts.ffcalculator.mobile.common.reader;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Une implementation permettant de lire une source depuis les assets
 * @since 1.0.0
 */
public class AssetReaderProvider implements ReaderProvider {
    private final Context context;

    public AssetReaderProvider(Context context) {
        this.context = context;
    }

    @Override
    public BufferedReader openReader(String path) throws IOException {
        AssetManager assetManager = context.getAssets();
        return new BufferedReader(new InputStreamReader(assetManager.open(path),StandardCharsets.UTF_8));
    }
}
