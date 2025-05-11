package ru.mirea.lamzin.cryptoloader;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import androidx.loader.content.AsyncTaskLoader;
import javax.crypto.SecretKey;

public class MyLoader extends AsyncTaskLoader<String> {
    public static final String ARG_ENCRYPTED_TEXT = "encrypted_text";
    public static final String ARG_KEY = "key";
    private String encryptedText;
    private SecretKey key;

    public MyLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            encryptedText = args.getString(ARG_ENCRYPTED_TEXT);
            key = (SecretKey) args.getSerializable(ARG_KEY);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            SystemClock.sleep(2000);
            return AES.decrypt(encryptedText, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
