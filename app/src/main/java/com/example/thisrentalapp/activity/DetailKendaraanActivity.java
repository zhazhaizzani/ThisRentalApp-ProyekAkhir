package com.example.thisrentalapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thisrentalapp.R;
import com.example.thisrentalapp.helper.DataHelper;

public class DetailKendaraanActivity extends AppCompatActivity {
    
    protected Cursor cursor;
    String stMerk, stHarga, stGambar;
    DataHelper dbhelper;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kendaraan);

        Bundle terima = getIntent().getExtras();

        dbhelper = new DataHelper(this);
        Intent intent = getIntent();

        String merk = terima.getString("merk");

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        cursor = db.rawQuery("select * from kendaraan where merk = '" + merk + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            stMerk = cursor.getString(0);
            stHarga = cursor.getString(1);
        }
        if (stMerk.equals("Pajero")) {
            stGambar = "pajero";
        } else if (stMerk.equals("Brio")) {
            stGambar = "brio";
        } else if (stMerk.equals("Jazz")) {
            stGambar = "jazz";
        } else if (stMerk.equals("Fortuner")) {
            stGambar = "fortuner";
        } else if (stMerk.equals("Alphard")) {
            stGambar = "alphard";
        } else if (stMerk.equals("Nmax")) {
            stGambar = "nmax";
        } else if (stMerk.equals("Vespa")) {
            stGambar = "vespa";
        } else if (stMerk.equals("Scoopy")) {
            stGambar = "scoopy";
        } else if (stMerk.equals("KLX")) {
            stGambar = "klx";
        }

        ImageView iGambar = findViewById(R.id.ivMobil);
        TextView tMerk = findViewById(R.id.JMobil);
        TextView tHarga = findViewById(R.id.JHarga);

        tMerk.setText(stMerk);
        iGambar.setImageResource(getResources().getIdentifier(stGambar, "drawable", getPackageName()));
        tHarga.setText("Rp. " + stHarga);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailMbl);
        toolbar.setTitle(" Detail Kendaraan ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
