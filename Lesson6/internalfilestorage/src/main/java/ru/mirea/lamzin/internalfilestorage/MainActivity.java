package ru.mirea.lamzin.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "history.txt";

    private EditText dateInput, descInput;
    private Button saveBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateInput = findViewById(R.id.dateInput);
        descInput = findViewById(R.id.descInput);
        saveBtn = findViewById(R.id.saveBtn);
        textView = findViewById(R.id.textView);

        saveBtn.setOnClickListener(v -> {
            String date = dateInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();

            if (date.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
                return;
            }

            String textToSave = date + ": " + desc + "\n";

            try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(textToSave.getBytes());
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Ошибка записи файла", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            readFileInThread();
        });

        readFileInThread();
    }

    private void readFileInThread() {
        new Thread(() -> {
            String text = getTextFromFile();
            textView.post(() -> {
                if (text != null) textView.setText(text);
                else textView.setText("Файл пуст или не найден");
            });
        }).start();
    }

    private String getTextFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
