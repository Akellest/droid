package ru.mirea.lamzin.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText groupNumber, listNumber, favoriteMovie;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "lesson6_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        groupNumber = findViewById(R.id.groupNumber);
        listNumber = findViewById(R.id.listNumber);
        favoriteMovie = findViewById(R.id.favoriteMovie);
        Button saveBtn = findViewById(R.id.saveBtn);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        groupNumber.setText(preferences.getString("group", ""));
        listNumber.setText(preferences.getString("list", ""));
        favoriteMovie.setText(preferences.getString("movie", ""));

        saveBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("group", groupNumber.getText().toString());
            editor.putString("list", listNumber.getText().toString());
            editor.putString("movie", favoriteMovie.getText().toString());
            editor.apply();
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        });
    }
}
