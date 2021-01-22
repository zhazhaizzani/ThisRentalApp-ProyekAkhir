package com.example.thisrentalapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thisrentalapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button informasi = findViewById(R.id.btn_info_mobil);
        Button sewa = findViewById(R.id.btn_sewa);

        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DaftarKendaraanActivity.class);
                startActivity(i);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this, PenyewaActivity.class);
                startActivity(s);
            }
        });
    }
}
