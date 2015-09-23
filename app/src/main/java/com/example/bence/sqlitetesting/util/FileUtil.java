package com.example.bence.sqlitetesting.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bence on 17.09.15.
 */
public class FileUtil {

    public static String readAssetFile(Context context, String fileName) {
        String result = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            result = new String(buffer);
        } catch (IOException e) {
            throw new ApplicationException(String.format("Failed to read asset: %s. Reason: %s",
                    fileName, e.getMessage()));
        }
        return result;
    }
}
