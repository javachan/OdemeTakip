package com.yusufali.lenovo.odemetakip;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yusufali.lenovo.odemetakip.adapter.AdapterGunuGelenOdemelerListesi;
import com.yusufali.lenovo.odemetakip.data.GunuGelenOdemeler;
import com.yusufali.lenovo.odemetakip.data.Odemeler;
import com.yusufali.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class ActivityGunuGelenler extends AppCompatActivity {


    private RecyclerView rv_gunuGelenOdemelerRecycListe;
    private AdapterGunuGelenOdemelerListesi mAdapterGunuGelenlerOdemelerListesi;

    public static final Uri CONTENT_URI_GUNU_GELEN_ODEMELER= OdemelerProvider.CONTENT_URI_GUNU_GELEN_ODEMELER;

    ArrayList<GunuGelenOdemeler> tumGunuGelenOdemeler=new ArrayList<>();
    private Toolbar mTolbar;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunu_gelenler);


        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //////////////////
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2499957816496992/8314333781");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //////////

        rv_gunuGelenOdemelerRecycListe=findViewById(R.id.rc_gunuGelenlerListe);
        LinearLayoutManager manager=new LinearLayoutManager(this);

        rv_gunuGelenOdemelerRecycListe.setLayoutManager(manager);
        mAdapterGunuGelenlerOdemelerListesi=new AdapterGunuGelenOdemelerListesi(this,tumGunuGelenOdemeler);

        rv_gunuGelenOdemelerRecycListe.setAdapter(mAdapterGunuGelenlerOdemelerListesi);

        dataGuncelle();



        MobileAds.initialize(this, "ca-app-pub-2499957816496992~8459811622");


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2499957816496992/9198178223");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {


            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }


        });




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
