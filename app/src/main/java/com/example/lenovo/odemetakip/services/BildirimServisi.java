package com.example.lenovo.odemetakip.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.example.lenovo.odemetakip.R;

import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.goncalves.pugnotification.notification.PugNotification;


public class BildirimServisi extends IntentService {

    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;

    public static final String TAG=Thread.currentThread().getName();

    ArrayList<Odemeler> odenmesiGerekenOdemelerListe=new ArrayList<>();

    public BildirimServisi() {
        super("BildirimServisi");
        Log.d(TAG,"bildirim : consructor");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG,"bildirim : onHandleIntent");
        odenmesiGerekenOdemelerListe=odemeZamaniGelenOdemeleriGetir();

        for(Odemeler geciciOdeme:odenmesiGerekenOdemelerListe)
        {
            if(bildirimGerekli(geciciOdeme.getOdemeHatirlatmaAyGunu()))
            {
               bildirimYolla(geciciOdeme.getOdemeBaslik(),geciciOdeme.getOdemeAylikFiyat(),(geciciOdeme.getOdemeOdenenTaksitSayisi()+1));
            }
        }

    }

    private void bildirimYolla(String baslik, int fiyat, int odenecekAy)
    {
        PugNotification.with(this)
                .load()
                .title("ÖDEME GÜNÜN GELDİ DOSTU!")
                .message("Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" Hadi görüşürüz bu kıyağımı da unutma :)")
                .bigTextStyle("")
                .smallIcon(R.drawable.alisveri)
                .largeIcon(R.drawable.yapilanodemeler)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();

    }


    private boolean bildirimGerekli(int odemeHatirlatmaAyGunu) {
        Calendar calendar = Calendar.getInstance();
        int bugun = calendar.get(Calendar.DAY_OF_MONTH);

        if(bugun==odemeHatirlatmaAyGunu)
            return true;
        else
            return false;

    }

    private ArrayList<Odemeler> odemeZamaniGelenOdemeleriGetir() {

        ArrayList<Odemeler> odenmesiGerekenler = new ArrayList<>();

        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor = getContentResolver()
                .query(CONTENT_URI, new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu"}, "OdemeAylikHatirlat=?", new String[]{"1"}, null);


        if (cursor != null) {
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

                odenmesiGerekenler.add(geciciOdeme);

            }
        }
        return odenmesiGerekenler;
    }


}
