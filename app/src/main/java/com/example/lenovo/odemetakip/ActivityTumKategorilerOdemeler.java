package com.example.lenovo.odemetakip;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.lenovo.odemetakip.adapter.AdapterOdemelerListesi;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class ActivityTumKategorilerOdemeler extends AppCompatActivity {

    private RecyclerView rv_tumOdemelerRecyclerListe;
    private AdapterOdemelerListesi mAdapterOdemelerListesi;
    private TextView tx_kategoriIsmi;

    String gelenKategori = null;

    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;

    ArrayList<Odemeler> tumOdemeler=new ArrayList<>();

    private Toolbar mTolbar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tum_kategoriler_odemeler);





        mTolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        try {
            gelenKategori = getIntent().getExtras().getString("OdemeKategoriAdi");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if (gelenKategori==null) {

            //eğer boşsa(detaylıdan geliyorumdur. sağdan sola açılsın ve kategoriyi shared ile alsın.)
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            gelenKategori = preferences.getString("gelenKategori","null");

        } else {

            //eğer doluysa mainden geliyorumdur soldan sağa açılsın ve kategoriyi shared ile yazsın.
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("gelenKategori", gelenKategori);
            editor.commit();
        }


            tx_kategoriIsmi = findViewById(R.id.tumOdemeler_kategoriIsmi);
            tx_kategoriIsmi.setText(gelenKategori);


            rv_tumOdemelerRecyclerListe = findViewById(R.id.rv_tum_odemeler_liste);

            LinearLayoutManager manager = new LinearLayoutManager(this);

            rv_tumOdemelerRecyclerListe.setLayoutManager(manager);

            mAdapterOdemelerListesi = new AdapterOdemelerListesi(this, tumOdemeler);

            rv_tumOdemelerRecyclerListe.setAdapter(mAdapterOdemelerListesi);


            dataGuncelle();//normalde buraya diğer mainden putextra ile aldığımz kategori text ini yollayıp ona göre filtreleme yapıcak


    }


    public void dataGuncelle()
    {
        if(gelenKategori.equals("Bitenler"))
        {
            tumOdemeler.clear();
            tumOdemeler=tumBitenOdemeleriGetir();
            mAdapterOdemelerListesi.update(tumOdemeler);
        }
        else {
            tumOdemeler.clear();
            tumOdemeler = tumOdemeleriGetir(gelenKategori);
            mAdapterOdemelerListesi.update(tumOdemeler);
        }

    }

    private ArrayList<Odemeler> tumOdemeleriGetir(String gelenKategori)
    {
        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor=getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu,OdemeParaBirimi"},"OdemeKategoriAdi=?",new String[]{gelenKategori},null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {

                Odemeler geciciOdeme = new Odemeler();
                geciciOdeme.setOdemeId(cursor.getInt(cursor.getColumnIndex("OdemeId")));
                geciciOdeme.setOdemeBaslik(cursor.getString(cursor.getColumnIndex("OdemeBaslik")));
                geciciOdeme.setOdemeKategoriAdi(cursor.getString(cursor.getColumnIndex("OdemeKategoriAdi")));
                geciciOdeme.setOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeOdenenTaksitSayisi")));
                geciciOdeme.setOdemeKalanTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeKalanTaksitSayisi")));
                geciciOdeme.setOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("OdemeAylikFiyat")));
                geciciOdeme.setOdemeAylikHatirlat(cursor.getInt(cursor.getColumnIndex("OdemeAylikHatirlat")));
                geciciOdeme.setOdemeHatirlatmaAyGunu(cursor.getInt(cursor.getColumnIndex("OdemeHatirlatmaAyGunu")));
                geciciOdeme.setOdemeParaBirimi(cursor.getString(cursor.getColumnIndex("OdemeParaBirimi")));

                if(geciciOdeme.getOdemeKalanTaksitSayisi()<1)
                {

                }else
                {
                    tumOdemeler.add(geciciOdeme);
                }



            }
        }
        return tumOdemeler;
    }



    private ArrayList<Odemeler> tumBitenOdemeleriGetir()
    {
        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor=getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu,OdemeParaBirimi"},"OdemeKalanTaksitSayisi=?",new String[]{String.valueOf(0)},null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {

                Odemeler geciciOdeme = new Odemeler();
                geciciOdeme.setOdemeId(cursor.getInt(cursor.getColumnIndex("OdemeId")));
                geciciOdeme.setOdemeBaslik(cursor.getString(cursor.getColumnIndex("OdemeBaslik")));
                geciciOdeme.setOdemeKategoriAdi(cursor.getString(cursor.getColumnIndex("OdemeKategoriAdi")));
                geciciOdeme.setOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeOdenenTaksitSayisi")));
                geciciOdeme.setOdemeKalanTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeKalanTaksitSayisi")));
                geciciOdeme.setOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("OdemeAylikFiyat")));
                geciciOdeme.setOdemeAylikHatirlat(cursor.getInt(cursor.getColumnIndex("OdemeAylikHatirlat")));
                geciciOdeme.setOdemeHatirlatmaAyGunu(cursor.getInt(cursor.getColumnIndex("OdemeHatirlatmaAyGunu")));
                geciciOdeme.setOdemeParaBirimi(cursor.getString(cursor.getColumnIndex("OdemeParaBirimi")));


                    tumOdemeler.add(geciciOdeme);




            }
        }
        return tumOdemeler;
    }






}
