package com.example.thisrentalapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thisrentalapp.R;
import com.example.thisrentalapp.helper.DataHelper;

public class PenyewaActivity extends AppCompatActivity {
    String[] daftar;
    int[] id;
    ListView ListView1;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static PenyewaActivity k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);

        Button tambah = findViewById(R.id.tambahPenyewa);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PenyewaActivity.this, SewaKendaraanActivity.class);
                startActivity(in);
            }
        });

        k = this;
        dbcenter = new DataHelper(this);

        RefreshList();
        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        toolbar.setTitle("Daftar Penyewa");
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

    void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM penyewa", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }

        ListView1 = findViewById(R.id.listView1);
        ListView1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftar));
        ListView1.setSelected(true);
        ListView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PenyewaActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent p = new Intent(PenyewaActivity.this, DetailPenyewaActivity.class);
                                p.putExtra("nama", selection);
                                startActivity(p);
                                break;
                            }
                            case 1: {
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM penyewa where nama = '" + selection + "'");
                                db.execSQL("DELETE FROM sewa where nama = '" + selection + "'");
                                RefreshList();
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();
    }
}
