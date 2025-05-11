package ru.mirea.lamzin.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("KEY");
                Log.d("MyLooper", "Received message: " + data);

                // Извлекаем возраст из данных
                String[] parts = data.split(", ");
                String ageStr = parts[0].split(": ")[1];
                int age = Integer.parseInt(ageStr);

                try {
                    Thread.sleep(age * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "Ваша профессия: " + parts[1] + ". Прошло времени: " + age + " секунд.");
                message.setData(bundle);

                mainHandler.sendMessage(message);
            }
        };

        Looper.loop();
    }
}
