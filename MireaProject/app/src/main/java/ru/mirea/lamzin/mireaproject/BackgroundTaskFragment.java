package ru.mirea.lamzin.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class BackgroundTaskFragment extends Fragment {

    private TextView statusTextView;

    public BackgroundTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background_task, container, false);

        statusTextView = view.findViewById(R.id.statusTextView);
        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest);
        WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.RUNNING) {
                            statusTextView.setText("Задача выполняется...");
                        } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            statusTextView.setText("Задача успешно завершена!");
                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            statusTextView.setText("Ошибка выполнения задачи!");
                        }
                    }
                });

        return view;
    }
}
