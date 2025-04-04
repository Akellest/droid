package com.mirea.lamzinas.dialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.app.TimePickerDialog;
import android.widget.Toast;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (view, hourOfDay, minute1) -> {
                    String time = String.format("Вы выбрали: %02d:%02d", hourOfDay, minute1);
                    Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
                }, hour, minute, true);


        timePickerDialog.setTitle("Выберите время");
        return timePickerDialog;
    }
}
