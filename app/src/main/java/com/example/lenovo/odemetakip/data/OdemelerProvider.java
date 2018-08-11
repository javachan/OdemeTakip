package com.example.lenovo.odemetakip.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.lenovo.odemetakip.data.OdemeTakipContract.Odemeler;
import com.example.lenovo.odemetakip.data.OdemeTakipContract.GunuGelenOdemeler;

public class OdemelerProvider extends ContentProvider {

    SQLiteDatabase db;

    /////

    static final String CONTENT_AUTHORITY="com.example.lenovo.odemetakip.odemelerprovider";
    static final String PATH_ODEMELER="odemeler";
    static final String PATH_GUNU_GELEN_ODEMELER="gunu_gelen_odemeler";
    static final String PATH_GECMIS_ODEMELER="gecmis_odemeler";
    static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ODEMELER);
    //content://com.example.lenovo.notsepetiapp.odemelerprovider/odemeler linki olusuyor.

    //sorgu calısırken uri olarak bunu  yollayacağım.
    public static final Uri CONTENT_URI_GUNU_GELEN_ODEMELER=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_GUNU_GELEN_ODEMELER);

    public static final Uri CONTENT_URI_GECMIS_ODEMELER=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_GECMIS_ODEMELER);







    static final UriMatcher matcher;

    static {

        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY,PATH_ODEMELER, 1);
        matcher.addURI(CONTENT_AUTHORITY,PATH_GUNU_GELEN_ODEMELER,2);
        matcher.addURI(CONTENT_AUTHORITY,PATH_GECMIS_ODEMELER,3);

    }



    ////


    @Override
    public boolean onCreate() {
        DatabaseHelper helper=new DatabaseHelper(getContext());
        db=helper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {


        Cursor cursor=null;
        switch (matcher.match(uri))
        {
            case 1: {
                cursor = db.query(Odemeler.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            }

            case 2: {
                cursor = db.query(GunuGelenOdemeler.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            }
            case 3: {
                cursor = db.query(OdemeTakipContract.GecmisOdemeler.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            }
        }



        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (matcher.match(uri))
        {
            case 1:
            {
                long eklenenSatirID=db.insert(Odemeler.TABLE_NAME,null,contentValues);
                if(eklenenSatirID>0)
                {
                    Uri _uri= ContentUris.withAppendedId(CONTENT_URI,eklenenSatirID); //analinkimiz/1, analinkimiz/2 vs...
                    return _uri;
                }
                break;
            }
            case 2:
            {
                long eklenenSatirID=db.insert(GunuGelenOdemeler.TABLE_NAME,null,contentValues);
                if(eklenenSatirID>0)
                {
                    Uri _uri= ContentUris.withAppendedId(CONTENT_URI_GUNU_GELEN_ODEMELER,eklenenSatirID); //analinkimiz/1, analinkimiz/2 vs...
                    return _uri;
                }
                break;
            }
            case 3:
            {
                long eklenenSatirID=db.insert(OdemeTakipContract.GecmisOdemeler.TABLE_NAME,null,contentValues);
                if(eklenenSatirID>0)
                {
                    Uri _uri= ContentUris.withAppendedId(CONTENT_URI_GECMIS_ODEMELER,eklenenSatirID); //analinkimiz/1, analinkimiz/2 vs...
                    return _uri;
                }
                break;
            }


        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int etkilenenSatirSayisi=0;
        switch (matcher.match(uri))
        {
            case 1: {
                etkilenenSatirSayisi = db.delete(Odemeler.TABLE_NAME, s, strings);
                break;
            }
            case 2:
            {
                etkilenenSatirSayisi = db.delete(GunuGelenOdemeler.TABLE_NAME, s, strings);
                break;
            }
        }
        return etkilenenSatirSayisi;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (matcher.match(uri))
        {
            case 1:
            {
                int id=db.update(Odemeler.TABLE_NAME,contentValues,s,strings);
                break;
            }
            case 2:
            {
                int id=db.update(GunuGelenOdemeler.TABLE_NAME,contentValues,s,strings);
                break;
            }
        }
        return 0;
    }
}
