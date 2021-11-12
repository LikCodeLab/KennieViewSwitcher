package com.kennie.library.example;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kennie.library.switcher.OnCheckedChangeListener;
import com.kennie.library.switcher.SwitcherButton;

public class ExampleActivity extends AppCompatActivity {

    private SwitcherButton switcher_x;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_act);
        switcher_x = findViewById(R.id.switcher_x);
        switcher_x.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onChange(boolean checked) {
                Toast.makeText(ExampleActivity.this, "" + checked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
