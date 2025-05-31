package ru.mirea.lamzin.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.lamzin.httpurlconnection.databinding.ActivityMainBinding;

public class DownloadPageTask extends AsyncTask<String, Void, String> {

    private final MainActivity activity;
    private final ActivityMainBinding binding;

    public DownloadPageTask(MainActivity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    @Override
    protected void onPreExecute() {
        binding.textIp.setText("Загружаем...");
        binding.textCity.setText("");
        binding.textRegion.setText("");
        binding.textWeather.setText("");
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadData(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if ("error".equals(result)) {
            binding.textIp.setText("Ошибка загрузки");
            binding.textCity.setText("");
            binding.textRegion.setText("");
            binding.textWeather.setText("");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            String ip = json.getString("ip");
            String city = json.optString("city", "Неизвестно");
            String region = json.optString("region", "Неизвестно");
            String country = json.optString("country", "Неизвестно");
            String postal = json.optString("postal", "Неизвестно");
            String timezone = json.optString("timezone", "Неизвестно");
            String loc = json.optString("loc", "0,0");

            binding.textIp.setText("IP: " + ip);
            binding.textCity.setText("Город: " + city);
            binding.textRegion.setText("Регион: " + region);
            binding.textCountry.setText("Страна: " + country);
            binding.textPostal.setText("Почтовый индекс: " + postal);
            binding.textTimezone.setText("Часовой пояс: " + timezone);

            String[] latLong = loc.split(",");
            if (latLong.length == 2) {
                new WeatherTask(binding).execute(latLong[0], latLong[1]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            binding.textIp.setText("Ошибка парсинга данных");
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
