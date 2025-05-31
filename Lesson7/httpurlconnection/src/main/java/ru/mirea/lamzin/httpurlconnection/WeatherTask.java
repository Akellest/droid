package ru.mirea.lamzin.httpurlconnection;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.lamzin.httpurlconnection.databinding.ActivityMainBinding;

public class WeatherTask extends AsyncTask<String, Void, String> {

    private final ActivityMainBinding binding;

    public WeatherTask(ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    protected void onPreExecute() {
        binding.textWeather.setText("Загружаем погоду...");
    }

    @Override
    protected String doInBackground(String... coords) {
        String latitude = coords[0];
        String longitude = coords[1];
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&current_weather=true";
        try {
            return downloadData(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if ("error".equals(result)) {
            binding.textWeather.setText("Ошибка загрузки погоды");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            JSONObject currentWeather = json.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");
            double windspeed = currentWeather.getDouble("windspeed");
            binding.textWeather.setText("Температура: " + temperature + "°C\nВетер: " + windspeed + " км/ч");
        } catch (JSONException e) {
            e.printStackTrace();
            binding.textWeather.setText("Ошибка парсинга погоды");
        }
    }

    private String downloadData(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                data = bos.toString();
                bos.close();
            } else {
                data = connection.getResponseMessage() + ". Код ошибки: " + responseCode;
            }
            connection.disconnect();
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return data;
    }
}
