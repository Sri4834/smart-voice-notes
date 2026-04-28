
package com.example.smartvoicenotes;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AISummarizer {
    private static final String TAG = "AISummarizer";
    private static final String API_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = BuildConfig.API_KEY;

    public interface SummaryCallback {
        void onSummaryReceived(String summary);
        void onError(String error);
    }

    public static void summarizeText(String inputText, SummaryCallback callback) {
        if (inputText == null || inputText.trim().isEmpty()) {
            callback.onError("Input text cannot be empty");
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(API_ENDPOINT + "?key=" + API_KEY);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setDoOutput(true);

                // ✅ Updated Smart Notes Prompt
                String prompt = " Convert the following spoken lecture text into well-organized, structured, and easy-to-study class notes. The notes should include: - Clear headings for each topic (e.g., Introduction to OOP, Java Syntax, Classes and Objects, etc.) - Bullet points for key concepts - Code blocks for any code spoken - Simple explanations for definitions like class, object, inheritance, etc. - Use student-friendly formatting - Begin the response with: \\\"SMART NOTES:\\\"\\n\\nLecture text:\\n\"" + inputText;


                JSONObject request = new JSONObject()
                        .put("contents", new JSONArray()
                                .put(new JSONObject()
                                        .put("parts", new JSONArray()
                                                .put(new JSONObject()
                                                        .put("text", prompt)
                                                )
                                        )
                                )
                        )
                        .put("generationConfig", new JSONObject()
                                .put("temperature", 0.3)
                                .put("maxOutputTokens", 4096)
                        );

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(request.toString().getBytes());
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String response = readStream(connection.getInputStream());
                    Log.d(TAG, "API Response: " + response);

                    String summary = parseResponse(response);
                    if (summary != null) {
                        String finalSummary = ensureSummaryFormat(summary);
                        handler.post(() -> callback.onSummaryReceived(finalSummary));
                    } else {
                        handler.post(() -> callback.onError("Failed to parse summary"));
                    }
                } else {
                    String error = readStream(connection.getErrorStream());
                    Log.e(TAG, "API Error: " + responseCode + " - " + error);
                    handler.post(() -> callback.onError("API Error: " + responseCode));
                }
            } catch (Exception e) {
                Log.e(TAG, "Request Failed", e);
                handler.post(() -> callback.onError("Error: " + e.getMessage()));
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private static String ensureSummaryFormat(String summary) {
        summary = summary.trim();
        if (!summary.startsWith("SMART NOTES:")) {
            return "SMART NOTES:\n" + summary;
        }
        return summary;
    }

    private static String parseResponse(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray candidates = response.getJSONArray("candidates");
            if (candidates.length() == 0) return null;

            return candidates.getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } catch (JSONException e) {
            Log.e(TAG, "Parsing Error: " + e.getMessage());
            return null;
        }
    }

    private static String readStream(java.io.InputStream stream) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}
