package ru.mirea.lamzin.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText editName, editEmail;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "profile_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadData();

        btnSave.setOnClickListener(v -> saveData());

        return view;
    }

    private void loadData() {
        editName.setText(sharedPreferences.getString(KEY_NAME, ""));
        editEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, editName.getText().toString());
        editor.putString(KEY_EMAIL, editEmail.getText().toString());
        editor.apply();
    }
}
