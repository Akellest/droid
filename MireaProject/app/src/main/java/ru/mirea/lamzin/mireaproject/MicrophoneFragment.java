package ru.mirea.lamzin.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MicrophoneFragment extends Fragment {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private Button recordButton, playButton;
    private boolean isRecording = false, isPlaying = false;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private String fileName;

    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_microphone, container, false);

        recordButton = view.findViewById(R.id.btn_record);
        playButton = view.findViewById(R.id.btn_play);
        playButton.setEnabled(false);

        fileName = getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/audiorecordtest.3gp";

        recordButton.setOnClickListener(v -> {
            if (!isRecording) {
                if (checkPermissions()) {
                    startRecording();
                } else {
                    requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);
                }
            } else {
                stopRecording();
            }
        });

        playButton.setOnClickListener(v -> {
            if (!isPlaying) {
                startPlaying();
            } else {
                stopPlaying();
            }
        });

        return view;
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
            recorder.start();
            recordButton.setText("Остановить запись");
            playButton.setEnabled(false);
            isRecording = true;
            Toast.makeText(getContext(), "Запись началась", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка записи", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        recordButton.setText("Начать запись");
        playButton.setEnabled(true);
        isRecording = false;
        Toast.makeText(getContext(), "Запись остановлена", Toast.LENGTH_SHORT).show();
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
            playButton.setText("Остановить воспроизведение");
            recordButton.setEnabled(false);
            isPlaying = true;
            player.setOnCompletionListener(mp -> stopPlaying());
            Toast.makeText(getContext(), "Воспроизведение началось", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
        playButton.setText("Начать воспроизведение");
        recordButton.setEnabled(true);
        isPlaying = false;
        Toast.makeText(getContext(), "Воспроизведение остановлено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Разрешение получено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Разрешение на запись аудио не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
