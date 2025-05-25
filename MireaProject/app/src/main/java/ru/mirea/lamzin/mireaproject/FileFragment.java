package ru.mirea.lamzin.mireaproject;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileFragment extends Fragment {
    private List<String> encryptedList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);

        ListView listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, encryptedList);
        listView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> showInputDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String encrypted = encryptedList.get(position);
            String decrypted = xorEncryptDecrypt(encrypted, "key");
            showDecryptedDialog(decrypted);
        });

        loadEncryptedData();
        return view;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Введите текст для шифрования");

        final EditText input = new EditText(requireContext());
        builder.setView(input);

        builder.setPositiveButton("Зашифровать и сохранить", (dialog, which) -> {
            String text = input.getText().toString();
            String encrypted = xorEncryptDecrypt(text, "key");
            saveEncryptedData(encrypted);
            encryptedList.add(encrypted);
            adapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void showDecryptedDialog(String decrypted) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Расшифрованный текст")
                .setMessage(decrypted)
                .setPositiveButton("Ок", null)
                .show();
    }

    private String xorEncryptDecrypt(String input, String key) {
        char[] inputChars = input.toCharArray();
        char[] keyChars = key.toCharArray();
        char[] output = new char[inputChars.length];

        for (int i = 0; i < inputChars.length; i++) {
            output[i] = (char)(inputChars[i] ^ keyChars[i % keyChars.length]);
        }
        return new String(output);
    }

    private void saveEncryptedData(String encrypted) {
        SharedPreferences prefs = requireContext().getSharedPreferences("encrypted_files", Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("data", new HashSet<>());
        set = new HashSet<>(set);
        set.add(encrypted);
        prefs.edit().putStringSet("data", set).apply();
    }

    private void loadEncryptedData() {
        SharedPreferences prefs = requireContext().getSharedPreferences("encrypted_files", Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("data", new HashSet<>());
        encryptedList.clear();
        encryptedList.addAll(set);
    }
}
