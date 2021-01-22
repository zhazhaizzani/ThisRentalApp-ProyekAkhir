package com.example.thisrentalapp.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thisrentalapp.R;
import com.example.thisrentalapp.helper.DataHelper;

public class DetailPenyewaActivity extends AppCompatActivity {

    String stNama, stAlamat, stHp, stMerk, stHarga;
    int intLama, intPromo, intTotal;
    double doubleTotal;

    protected Cursor cursor;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);

        dbHelper = new DataHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from penyewa, kendaraan, sewa where penyewa.nama = sewa.nama AND kendaraan.merk = sewa.merk AND penyewa.nama = '" + getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            stNama = cursor.getString(0);
            stAlamat = cursor.getString(1);
            stHp = cursor.getString(2);
            stMerk = cursor.getString(3);
            stHarga = cursor.getString(4);
            intPromo = cursor.getInt(7);
            intLama = cursor.getInt(8);
            intTotal = cursor.getInt(9);
        }

        TextView Namaa = findViewById(R.id.HNama);
        TextView Alamatt = findViewById(R.id.HAlamat);
        TextView HPp = findViewById(R.id.HTelp);

        TextView Merkk = findViewById(R.id.HMerk);
        TextView Hargaa = findViewById(R.id.HHarga);

        TextView Lamaa = findViewById(R.id.HLamaSewa);
        TextView Promoo = findViewById(R.id.HPromo);

        Namaa.setText("    " + stNama);
        Alamatt.setText("    " + stAlamat);
        HPp.setText("    " + stHp);

        Merkk.setText("    " + stMerk);
        Hargaa.setText("   Rp. " + stHarga);

        Lamaa.setText("    " + intLama + " hari");
        Promoo.setText("    " + intPromo + "%");

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailPenyewa);
        toolbar.setTitle(" Detail Penyewa");
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
