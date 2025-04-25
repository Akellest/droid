package ru.mirea.lamzin.simplefragmentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment1, fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Система сама выберет правильную разметку

        // Получаем FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Инициализируем фрагменты
        fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();

        // Получаем кнопки для переключения фрагментов
        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);

        // Портретная ориентация: отображаем только один фрагмент
        if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerLeft, fragment1)
                        .commit();
            }

            // Устанавливаем действия для кнопок
            btnFirstFragment.setVisibility(Button.VISIBLE);
            btnSecondFragment.setVisibility(Button.VISIBLE);

            btnFirstFragment.setOnClickListener(v -> fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerLeft, fragment1)
                    .commit());

            btnSecondFragment.setOnClickListener(v -> fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerLeft, fragment2)
                    .commit());
        }
        // Горизонтальная ориентация: показываем оба фрагмента
        else {
            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerLeft, fragment1)
                        .replace(R.id.fragmentContainerRight, fragment2)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
