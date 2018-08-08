package com.example.lenovo.odemetakip.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lenovo.odemetakip.ActivityMain;
import com.example.lenovo.odemetakip.ActivityTumKategorilerOdemeler;
import com.example.lenovo.odemetakip.R;

import com.example.lenovo.odemetakip.adapter.AdapterOdemelerListesi;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

// import br.com.goncalves.pugnotification.notification.PugNotification;


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
            if(bildirimGerekli(geciciOdeme.getOdemeHatirlatmaAyGunu(),geciciOdeme.getOdemeKalanTaksitSayisi()))
            {
               bildirimYolla(geciciOdeme.getOdemeId(),geciciOdeme.getOdemeBaslik(),geciciOdeme.getOdemeAylikFiyat(),(geciciOdeme.getOdemeOdenenTaksitSayisi()+1));
            }
        }

    }

    private void bildirimYolla(int id,String baslik, int fiyat, int odenecekAy)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH");
        String strDate = mdformat.format(calendar.getTime());



        if(strDate.equals("23"))
        {
            //eğer saat 23 ise, ödenenler i temizle ki bir sonraki ay bildirim gidebilsin.
        }


       /* PugNotification.with(this)
                .load()
                .title("ÖDEME GÜNÜN GELDİ DOSTU!")
                .message("Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" Hadi görüşürüz bu kıyağımı da unutma :)")
                .bigTextStyle("")
                .smallIcon(R.drawable.alisveri)
                .largeIcon(R.drawable.yapilanodemeler)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();

          */




       Intent pendingIntent=new Intent(this, ActivityTumKategorilerOdemeler.class);
       pendingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent bildirimIntent=PendingIntent.getActivity(this,10,pendingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            String message="Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" Hadi görüşürüz bu kıyağımı da unutma :)";

        @SuppressLint("ResourceAsColor") Notification builder=new NotificationCompat.Builder(this,"Yeni mesaj")
       .setSmallIcon(R.drawable.iconum)
       .setContentTitle("ÖDEME GÜNÜN GELDİ DOSTUM!")

       .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.yapilanodemeler))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle("ÖDEME GÜNÜN GELDİ DOSTUM!")
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
            .setContentText(message)

                .setColor(R.color.anaYesil).setOnlyAlertOnce(true)

            .setAutoCancel(true)
                .setContentIntent(bildirimIntent)


            .build();


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder);



       Log.d(TAG,"Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" Hadi görüşürüz bu kıyağımı da unutma :)");

    }


    private boolean bildirimGerekli(int odemeHatirlatmaAyGunu,int kalanTaksitSayisi) {
        Calendar calendar = Calendar.getInstance();
        int bugun = calendar.get(Calendar.DAY_OF_MONTH);


        if(bugun==odemeHatirlatmaAyGunu) {
            if(kalanTaksitSayisi>0)
                return true;
            else
                return false;
        }

        else
            return false;

    }

    private boolean b2()
    {

        //yukardan id alırım, günü gelen ödemenin idsi..
        //sonra ödenenler tablosuna bakarım var mı id si diye, yoksa bildirim gonder...
        //varsa gönderme.. o tabloya bakmayıda, eklemeyide başka metdda yaparım.

        return false;
    }


    private boolean b3()
    {
        //burda tabloda var mı yada tabloya ekleme işlemi yapılıp b2 ye dönderilir sonuç.
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
