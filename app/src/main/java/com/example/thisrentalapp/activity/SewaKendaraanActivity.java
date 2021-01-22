package com.example.thisrentalapp.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thisrentalapp.R;
import com.example.thisrentalapp.helper.DataHelper;

import java.util.List;

public class SewaKendaraanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nama, alamat, no_hp, lamasewa;
    RadioGroup promo;
    RadioButton weekday, weekend;
    Button finish;

    String stNama, stAlamat, stHp, stMerk, stLamaSewa;
    double doublepromo;
    int intLama, intPromo, intHarga;
    double doubleTotal;

    private Spinner spinner;
    DataHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);
        
        dbHelper = new DataHelper(this);
        
        spinner = findViewById(R.id.spinner);
        finish = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        promo = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        lamasewa = findViewById(R.id.eTLamaSewa);
        
        spinner.setOnItemSelectedListener(this);
        
        loadSpinnerData();
        
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stNama = nama.getText().toString();
                stAlamat = alamat.getText().toString();
                stHp = no_hp.getText().toString();
                stLamaSewa = lamasewa.getText().toString();
                if (stNama.isEmpty() || stAlamat.isEmpty() || stHp.isEmpty() || stLamaSewa.isEmpty()) {
                    Toast.makeText(SewaKendaraanActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weekday.isChecked()) {
                    doublepromo = 0.1;
                } else if (weekend.isChecked()) {
                    doublepromo = 0.25;
                }
                
                if (stMerk.equals("Pajero")) {
                    intHarga = 500000;
                } else if (stMerk.equals("Brio")) {
                    intHarga = 200000;
                } else if (stMerk.equals("Jazz")) {
                    intHarga = 350000;
                } else if (stMerk.equals("Fortuner")) {
                    intHarga = 550000;
                } else if (stMerk.equals("Alphard")) {
                    intHarga = 700000;
                } else if (stMerk.equals("Nmax")) {
                    intHarga = 90000;
                } else if (stMerk.equals("Vespa")) {
                    intHarga = 200000;
                } else if (stMerk.equals("Scoopy")) {
                    intHarga = 80000;
                } else if (stMerk.equals("KLX")) {
                    intHarga = 100000;
                }
                
                intLama = Integer.parseInt(stLamaSewa);
                intPromo = (int) (doublepromo * 100);
                doubleTotal = (intHarga * intLama) - (intHarga * intLama * doublepromo);

                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                        stNama + "','" +
                        stAlamat + "','" +
                        stHp + "');");
                dbH.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" +
                        stMerk + "','" +
                        stNama + "','" +
                        intPromo + "','" +
                        intLama + "','" +
                        doubleTotal + "');");
                PenyewaActivity.k.RefreshList();
                finish();
            }
        });
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaMobl);
        toolbar.setTitle("Sewa Kendaraan");
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

    private void loadSpinnerData() {
        DataHelper db = new DataHelper(getApplicationContext());
        List<String> categories = db.getAllCategories();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
