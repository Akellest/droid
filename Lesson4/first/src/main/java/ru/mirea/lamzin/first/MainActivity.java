package ru.mirea.lamzin.first;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.lamzin.first.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.playPauseButton.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            binding.playPauseButton.setText(isPlaying ? "Pause" : "Play");
            // ...
        });
    }
}
