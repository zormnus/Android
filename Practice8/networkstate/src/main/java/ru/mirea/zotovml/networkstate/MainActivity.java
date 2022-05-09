package ru.mirea.zotovml.networkstate;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        LiveData<String> networkLiveData = NetworkLiveData.Companion.getInstance(this);
        networkLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
                textView.setText(value);
            }
        });
    }
}