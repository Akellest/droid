package com.mirea.lamzinas.control_lesson1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // THIS
        setContentView(R.layout.activity_second);
        // OR THAT
        /*
        setContentView(R.layout.activity_main);
        TextView myTextView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        CheckBox checkBox = findViewById(R.id.checkBox);

        myTextView.setText("New text in MIREA");

        button.setText("MireaButton");

        checkBox.setChecked(true);

        button.setOnClickListener(v -> {
            myTextView.setText("Button was clicked");
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                myTextView.setText("CheckBox is checked");
            } else {
                myTextView.setText("CheckBox is unchecked");
            }
        });

         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
