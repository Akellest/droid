package ru.mirea.lamzin.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.lamzin.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private EditText editTextAge, editTextProfession;
    private Button buttonSubmit;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextAge = findViewById(R.id.editTextAge);
        editTextProfession = findViewById(R.id.editTextProfession);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewResult = findViewById(R.id.textViewResult);

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                Log.d(MainActivity.class.getSimpleName(), "Task executed. This is result: " + result);
                textViewResult.setText(result);
            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageStr = editTextAge.getText().toString();
                String profession = editTextProfession.getText().toString();
                if (!ageStr.isEmpty() && !profession.isEmpty()) {
                    int age = Integer.parseInt(ageStr);

                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY", "Возраст: " + age + ", Профессия: " + profession);
                    msg.setData(bundle);

                    myLooper.mHandler.sendMessage(msg);
                }
            }
        });
    }
}
