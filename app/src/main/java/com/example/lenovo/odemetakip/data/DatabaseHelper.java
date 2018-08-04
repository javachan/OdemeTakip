package com.example.lenovo.odemetakip.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lenovo.odemetakip.data.OdemeTakipContract.Odemeler;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="odemetakip.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_ODEMELER_CREATE=
            "create table "+Odemeler.TABLE_NAME+"("+Odemeler._ID+" integer PRIMARY KEY AUTOINCREMENT,"+Odemeler.COLUMN_ODEME_BASLIK+
             " text,"+Odemeler.COLUMN_ODEME_KATEGORI_ADI+" text,"+Odemeler.COLUMN_ODEME_ODENEN_TAKSIT_SAYISI+" integer default 0,"+Odemeler.COLUMN_ODEME_KALAN_TAKSIT_SAYISI+
                    " integer,"+Odemeler.COLUMN_ODEME_AYLIK_FIYAT+" integer,"+Odemeler.COLUMN_ODEME_AYLIK_HATIRLAT+" integer,"+Odemeler.COLUMN_ODEME_HATIRLATMA_AY_GUNU+" integer)";


    /*private static final String TABLE_ODEMELER_CREATE=
            "CREATE TABLE " + Odemeler.TABLE_NAME + "("+
                    KategoriEntry._ID + "INTEGER PRIMARY KEY,"+
                    KategoriEntry.COLUMN_KATEGORI + "TEXT)";



 create table odemeler(OdemeId integer PRIMARY KEY AUTOINCREMENT, OdemeBaslik text,
        OdemeKategoriAdi text, OdemeOdenenTaksitSayisi integer default 0, OdemeKalanTaksitSayisi integer,
                OdemeAylikFiyat integer, OdemeAylikHatirlat integer, OdemeHatirlatmaAyGunu integer)


                    */




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //her yeniden yapma 0 dan alma işleminde version arttır.(değiştir.)
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TABLE_ODEMELER_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("Drop Table If Exists "+Odemeler.TABLE_NAME);
        onCreate(sqLiteDatabase);
        //database version değiştiğinde.

    }
}
