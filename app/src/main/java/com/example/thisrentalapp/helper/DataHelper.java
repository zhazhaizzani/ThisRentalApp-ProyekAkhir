package com.example.thisrentalapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rental.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table penyewa (" +
                "nama text," +
                "alamat text," +
                "no_hp text," +
                "primary key(nama)" +
                ");" +
                "");
        db.execSQL("create table kendaraan(" +
                "merk text," +
                "harga int," +
                "primary key(merk)" +
                ");" +
                "");
        db.execSQL("create table sewa(" +
                "merk text," +
                "nama text," +
                "promo int," +
                "lama int," +
                "total double," +
                "foreign key(merk) references kendaraan (merk), " +
                "foreign key(nama) references penyewa (nama) " +
                ");" +
                "");

        db.execSQL("insert into kendaraan values (" +
                "'Pajero'," +
                "500000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Brio'," +
                "200000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Jazz'," +
                "350000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Fortuner'," +
                "550000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Alphard'," +
                "700000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Nmax'," +
                "90000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Vespa'," +
                "200000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'Scoopy'," +
                "80000" +
                ");" +
                "");
        db.execSQL("insert into kendaraan values (" +
                "'KLX'," +
                "100000" +
                ");" +
                "");
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<String>();
        String selectQuery = "select * from kendaraan";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return categories;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
