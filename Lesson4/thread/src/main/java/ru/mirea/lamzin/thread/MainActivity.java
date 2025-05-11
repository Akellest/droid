package ru.mirea.lamzin.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.lamzin.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int numberThread = counter++;

                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d",
                                numberThread, "БИСО-01-20", 17));

                        long endTime = System.currentTimeMillis() + 5 * 1000;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewThreadInfo.setText("Информация о потоке: Запущен поток № " + numberThread);
                            }
                        });

                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        Log.d("ThreadProject", "Выполнен поток № " + numberThread);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewThreadInfo.setText("Информация о потоке: Поток № " + numberThread + " завершен.");

                                String totalPairsStr = binding.editTextTotalPairs.getText().toString();
                                String daysStr = binding.editTextDays.getText().toString();

                                if (!totalPairsStr.isEmpty() && !daysStr.isEmpty()) {
                                    int totalPairs = Integer.parseInt(totalPairsStr);
                                    int days = Integer.parseInt(daysStr);

                                    double average = days > 0 ? (double) totalPairs / days : 0;
                                    binding.textViewResult.setText("Среднее пар в день: " + average);
                                } else {
                                    binding.textViewResult.setText("Поля не должны быть пустыми");
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
