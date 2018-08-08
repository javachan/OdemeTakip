package com.example.lenovo.odemetakip;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.lenovo.odemetakip.adapter.AdapterOdemelerListesi;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class ActivityTumKategorilerOdemeler extends AppCompatActivity {

    private RecyclerView rv_tumOdemelerRecyclerListe;
    private AdapterOdemelerListesi mAdapterOdemelerListesi;
    private TextView tx_kategoriIsmi;

    String gelenKategori;

    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;

    ArrayList<Odemeler> tumOdemeler=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tum_kategoriler_odemeler);


        gelenKategori=getIntent().getExtras().getString("OdemeKategoriAdi");

        tx_kategoriIsmi=findViewById(R.id.tumOdemeler_kategoriIsmi);
        tx_kategoriIsmi.setText(gelenKategori+" Ödemelerim");


        rv_tumOdemelerRecyclerListe=findViewById(R.id.rv_tum_odemeler_liste);

        LinearLayoutManager manager=new LinearLayoutManager(this);
      
        rv_tumOdemelerRecyclerListe.setLayoutManager(manager);

        mAdapterOdemelerListesi=new AdapterOdemelerListesi(this,tumOdemeler);

        rv_tumOdemelerRecyclerListe.setAdapter(mAdapterOdemelerListesi);



        dataGuncelle();//normalde buraya diğer mainden putextra ile aldığımz kategori text ini yollayıp ona göre filtreleme yapıcak

    }


    public void dataGuncelle()
    {
        tumOdemeler.clear();
        tumOdemeler=tumOdemeleriGetir(gelenKategori);
        mAdapterOdemelerListesi.update(tumOdemeler);

    }

    private ArrayList<Odemeler> tumOdemeleriGetir(String gelenKategori)
    {
        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor=getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu"},"OdemeKategoriAdi=?",new String[]{gelenKategori},null);



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

                tumOdemeler.add(geciciOdeme);

            }
        }
        return tumOdemeler;
    }



}
