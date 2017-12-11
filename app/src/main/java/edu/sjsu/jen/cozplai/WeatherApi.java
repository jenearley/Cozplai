package edu.sjsu.jen.cozplai;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jen0e on 12/10/17.
 */

public class WeatherApi {

    private static final String TAG = "WeatherAPI";
    private static final String API_KEY = "dcf6724881910bcd23179e7cc34c9080";
    private static final String API_END_POINT = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s";

    @SuppressLint("StaticFieldLeak")
    static void getWeatherAtLocation(final double lat, final double lng, final WeatherCallback callback) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                callback.onWeatherResult(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String url = String.format(API_END_POINT, lat, lng, API_KEY);
                Log.d(TAG, "getWeatherAtLocation: " + url);
                try {
                    URL weatherUrl = new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) weatherUrl.openConnection();
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        String response = stringBuilder.toString();
                        Log.d(TAG, "Weather: " + response);
                        urlConnection.disconnect();
                        return response;
                    }
                } catch (MalformedURLException ignored) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
