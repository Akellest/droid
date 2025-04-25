package ru.mirea.lamzin.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quote = extras.getString(MainActivity.QUOTES_KEY);

            TextView bookNameTextView = findViewById(R.id.textViewBook);
            TextView quoteTextView = findViewById(R.id.textViewQuote);
            bookNameTextView.setText("Любимая книга разработчика: " + bookName);
            quoteTextView.setText("Цитата: " + quote);
        }

        EditText bookInput = findViewById(R.id.editTextBook);
        EditText quoteInput = findViewById(R.id.editTextQuote);
        Button sendButton = findViewById(R.id.buttonSend);

        sendButton.setOnClickListener(v -> {
            String userBook = bookInput.getText().toString();
            String userQuote = quoteInput.getText().toString();

            Intent data = new Intent();
            data.putExtra(MainActivity.USER_MESSAGE,
                    "Название книги: " + userBook + "\nЦитата: " + userQuote);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}
