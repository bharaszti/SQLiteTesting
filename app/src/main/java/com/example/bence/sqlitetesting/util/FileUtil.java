package com.example.bence.sqlitetesting.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bence on 17.09.15.
 */
public class FileUtil {

    public static String readResourceFile(String fileName) {
        String assetName = "/assets/" + fileName;
        InputStream inputStream = FileUtil.class.getResourceAsStream(assetName);
        if (inputStream == null) {
            throw new ApplicationException(String.format("Resource not found: %s", assetName));
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String read = null;
        try {
            while ((read = bufferedReader.readLine()) != null) {
                stringBuilder.append(read);
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new ApplicationException(String.format("Failed to read resource: %s. Reason: %s",
                    assetName, e.getMessage()));
        }

        String result = stringBuilder.toString();
        return result;
    }

}
