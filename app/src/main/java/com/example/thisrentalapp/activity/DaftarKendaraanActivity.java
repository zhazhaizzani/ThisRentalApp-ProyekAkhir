package com.example.thisrentalapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thisrentalapp.R;
import com.example.thisrentalapp.helper.DataHelper;

public class DaftarKendaraanActivity extends AppCompatActivity {
    String[] daftar;
    ListView ListView1;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcentar;
    @SuppressLint("StaticFieldLeak")
    public static DaftarKendaraanActivity k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan);

        k = this;
        dbcentar = new DataHelper(this);

        RefreshList();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbInfoMbl);
        toolbar.setTitle("Tentang Daftar Kendaraan");
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

    private void RefreshList() {
        SQLiteDatabase db = dbcentar.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kendaraan", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }
        ListView1 = findViewById(R.id.listView1);
        ListView1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView1.setSelected(true);
        ListView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                Intent i = new Intent(DaftarKendaraanActivity.this, DetailKendaraanActivity.class);
                i.putExtra("merk", selection);
                startActivity(i);
            }
        });
        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();
    }
}
