package com.mirea.lamzinas.buttonclicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStudent;
    private CheckBox checkBox;
    private Button btnWhoAmI;
    private Button btnItIsNotMe;
    private Button btnItIsNotMeVer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStudent = findViewById(R.id.tvOut);
        checkBox = findViewById(R.id.checkBox);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        btnItIsNotMeVer2 = findViewById(R.id.btnItIsNotMe_ver2);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnWhoAmI) {
                    textViewStudent.setText("Мой номер по списку № 16/17");
                } else if (v.getId() == R.id.btnItIsNotMe) {
                    textViewStudent.setText("Это не я сделал");
                }
                checkBox.setChecked(!checkBox.isChecked());
            }
        };

        btnWhoAmI.setOnClickListener(buttonClickListener);
        btnItIsNotMe.setOnClickListener(buttonClickListener);
    }

    public void onMyButtonClick(View view) {
        Toast.makeText(this, "Ещё один способ!", Toast.LENGTH_SHORT).show();
        checkBox.setChecked(!checkBox.isChecked());
    }
}
