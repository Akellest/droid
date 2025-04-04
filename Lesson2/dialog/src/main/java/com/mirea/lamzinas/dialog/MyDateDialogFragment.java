package com.mirea.lamzinas.dialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year1, month1, dayOfMonth1) -> {
                    String date = String.format("Вы выбрали: %02d-%02d-%04d", dayOfMonth1, month1 + 1, year1);
                    Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                }, year, month, dayOfMonth);

        datePickerDialog.setTitle("Выберите дату");
        return datePickerDialog;
    }
}
