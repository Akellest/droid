package ru.mirea.lamzin.cryptoloader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1;
    private EditText editTextPhrase;
    private Button buttonEncrypt;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhrase = findViewById(R.id.editTextPhrase);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);

        try {
            secretKey = AES.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = editTextPhrase.getText().toString();
                if (!phrase.isEmpty()) {
                    try {
                        String encryptedText = AES.encrypt(phrase, secretKey);

                        Bundle bundle = new Bundle();
                        bundle.putString(MyLoader.ARG_ENCRYPTED_TEXT, encryptedText);
                        bundle.putSerializable(MyLoader.ARG_KEY, secretKey);

                        LoaderManager.getInstance(MainActivity.this).initLoader(LOADER_ID, bundle, MainActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Введите фразу", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return new MyLoader(this, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
            Toast.makeText(this, "Дешифрованный текст: " + data, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
