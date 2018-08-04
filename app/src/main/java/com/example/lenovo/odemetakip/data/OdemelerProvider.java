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

public class OdemelerProvider extends ContentProvider {

    SQLiteDatabase db;

    /////

    static final String CONTENT_AUTHORITY="com.example.lenovo.odemetakip.odemelerprovider";
    static final String PATH_ODEMELER="odemeler";
    static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ODEMELER);
    //content://com.example.lenovo.notsepetiapp.odemelerprovider/odemeler linki olusuyor.


    static final UriMatcher matcher;

    static {

        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY,PATH_ODEMELER, 1);

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
            case 1:
                cursor=db.query(Odemeler.TABLE_NAME,projection,selection,selectionArgs,sortOrder,null,null);
                break;
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
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
