package com.example.lenovo.odemetakip;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.lenovo.odemetakip.adapter.AdapterGunuGelenOdemelerListesi;
import com.example.lenovo.odemetakip.data.GunuGelenOdemeler;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class ActivityGunuGelenler extends AppCompatActivity {


    private RecyclerView rv_gunuGelenOdemelerRecycListe;
    private AdapterGunuGelenOdemelerListesi mAdapterGunuGelenlerOdemelerListesi;

    public static final Uri CONTENT_URI_GUNU_GELEN_ODEMELER= OdemelerProvider.CONTENT_URI_GUNU_GELEN_ODEMELER;

    ArrayList<GunuGelenOdemeler> tumGunuGelenOdemeler=new ArrayList<>();
    private Toolbar mTolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunu_gelenler);


        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");




        rv_gunuGelenOdemelerRecycListe=findViewById(R.id.rc_gunuGelenlerListe);
        LinearLayoutManager manager=new LinearLayoutManager(this);

        rv_gunuGelenOdemelerRecycListe.setLayoutManager(manager);
        mAdapterGunuGelenlerOdemelerListesi=new AdapterGunuGelenOdemelerListesi(this,tumGunuGelenOdemeler);

        rv_gunuGelenOdemelerRecycListe.setAdapter(mAdapterGunuGelenlerOdemelerListesi);

        dataGuncelle();



       




    }

    public void dataGuncelle()
    {
        tumGunuGelenOdemeler.clear();
        tumGunuGelenOdemeler=tumGunuGelenOdemeleriGetir();
        mAdapterGunuGelenlerOdemelerListesi.update(tumGunuGelenOdemeler);
    }

    private ArrayList<GunuGelenOdemeler> tumGunuGelenOdemeleriGetir()
    {
        //cursor ile tüm günü gelen ödemeler tablosundakileri listeledik.
        Cursor cursor=getContentResolver().query(CONTENT_URI_GUNU_GELEN_ODEMELER,new String[]{"GunOdemeId,GunOdemeBaslik,GunOdemeOdenenTaksitSayisi,GunOdemeKalanTaksitSayisi,GunOdemeAylikFiyat,GunOdemeOdendimi,GunOdemeParaBirimi"},null,null,null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {

                GunuGelenOdemeler geciciGunuGelenOdeme = new GunuGelenOdemeler();

                geciciGunuGelenOdeme.setGunOdemeId(cursor.getInt(cursor.getColumnIndex("GunOdemeId")));
                geciciGunuGelenOdeme.setGunOdemeBaslik(cursor.getString(cursor.getColumnIndex("GunOdemeBaslik")));
                geciciGunuGelenOdeme.setGunOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("GunOdemeOdenenTaksitSayisi")));
                geciciGunuGelenOdeme.setGunOdemeKalanTaksitSayisi(cursor.getInt(cursor.getColumnIndex("GunOdemeKalanTaksitSayisi")));
                geciciGunuGelenOdeme.setGunOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("GunOdemeAylikFiyat")));
                geciciGunuGelenOdeme.setGunOdemeOdendimi(cursor.getString(cursor.getColumnIndex("GunOdemeOdendimi")));
                geciciGunuGelenOdeme.setGunOdemeParaBirimi(cursor.getString(cursor.getColumnIndex("GunOdemeParaBirimi")));


                tumGunuGelenOdemeler.add(geciciGunuGelenOdeme);

            }
        }
        return tumGunuGelenOdemeler;
    }


}
