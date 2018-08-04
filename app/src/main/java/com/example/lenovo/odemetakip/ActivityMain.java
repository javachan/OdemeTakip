package com.example.lenovo.odemetakip;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.odemetakip.data.OdemeTakipContract;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;
import com.example.lenovo.odemetakip.services.BildirimServisi;

import java.util.ArrayList;


public class ActivityMain extends AppCompatActivity {

    CardView aa;
    private Button yeniOdemeButon;

    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;

    ArrayList<Odemeler> tumOdemeler=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        yeniOdemeButon=findViewById(R.id.btn_yeni_odeme);
        aa=findViewById(R.id.cardview2);



        dataGuncelle();


        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eğer 1. karta(ev) kartına tıklanmışsa textkategorim ev ve ona göre sorgu yapılır.
               // Toast.makeText(ActivityMain.this, ":))", Toast.LENGTH_SHORT).show();
                /*for(int i=0;i<tumOdemeler.size();i++)
                {
                   Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeId()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeBaslik()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeKategoriAdi()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeKalanTaksitSayisi()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeOdenenTaksitSayisi()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeAylikFiyat()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeAylikHatirlat()));
                    Log.d("logg",String.valueOf(tumOdemeler.get(i).getOdemeHatirlatmaAyGunu()));
                    Log.d("logg",String.valueOf("-------------------"));

                }

                VERİLER ALINIYOR. BAŞARILI BİR ŞEKİLDE.(----- TİRELERE KADAR OLANLAR TEK BİR ODEMENİN BİLGİLERİ.)
                */
                Intent denemei=new Intent(ActivityMain.this,ActivityTumKategorilerOdemeler.class);
                startActivity(denemei);


            }
        });


        yeniOdemeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityMain.this,ActivityYeniOdeme.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




        arkaplaniDegistir();


        //bildirim alarmı için.
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent bildirimIntent=new Intent(this, BildirimServisi.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,100, bildirimIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,5000,3600000,pendingIntent);
        ///
    }



    public void dataGuncelle()
    {
        tumOdemeler.clear();
        tumOdemeler=tumOdemeleriGetir();
       // mAdapterNotlarListesi.update(tumNotlar);

    }

    private ArrayList<Odemeler> tumOdemeleriGetir()
    {
        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor=getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu"},null,null,null);



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



    private void arkaplaniDegistir()
    {
        ImageView arkaplanResim=findViewById(R.id.iv_arkaplanImage);
        Glide.with(this)
                .load(R.drawable.b9)
                .into(arkaplanResim);
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
