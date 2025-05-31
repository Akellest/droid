package ru.mirea.lamzin.lesson7;
import android.util.Log;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.lamzin.lesson7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov";
    private final int port = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(v -> new GetTimeTask().execute());
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String timeResult = "";
            try {
                Socket socket = new Socket();
                socket.connect(new java.net.InetSocketAddress(host, port), 5000); // 5 секунд таймаут

                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                String line2 = reader.readLine();
                if (line2 == null) return "ERROR_IO";
                timeResult = line2;

                socket.close();
            } catch (UnknownHostException e) {
                Log.e("TIME_ERROR", "Ошибка сокета", e);
                return "ERROR_HOST";
            } catch (IOException e) {
                Log.e("TIME_ERROR", "Ошибка сокета", e);
                return "ERROR_IO";
            }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            if ("ERROR_HOST".equals(result)) {
                binding.textDate.setText("Сервер недоступен");
                binding.textTime.setText("");
                return;
            }
            if ("ERROR_IO".equals(result) || result == null) {
                binding.textDate.setText("Ошибка получения данных");
                binding.textTime.setText("");
                return;
            }
            try {
                String[] tokens = result.trim().split("\\s+");
                String datePart = tokens[1];
                String timePart = tokens[2];
                SimpleDateFormat parser = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());
                Date dt = parser.parse(datePart + " " + timePart);
                SimpleDateFormat fmtDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                binding.textDate.setText("Дата: " + fmtDate.format(dt));
                binding.textTime.setText("Время: " + fmtTime.format(dt));
            } catch (Exception e) {
                binding.textDate.setText("Неверный формат данных");
                binding.textTime.setText("");
            }
        }
    }
}
