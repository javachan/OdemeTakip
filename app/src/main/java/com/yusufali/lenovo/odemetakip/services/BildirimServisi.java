package com.yusufali.lenovo.odemetakip.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.yusufali.lenovo.odemetakip.ActivityGunuGelenler;
import com.yusufali.lenovo.odemetakip.ActivityMain;
import com.yusufali.lenovo.odemetakip.ActivityTumKategorilerOdemeler;
import com.yusufali.lenovo.odemetakip.R;

import com.yusufali.lenovo.odemetakip.adapter.AdapterOdemelerListesi;
import com.yusufali.lenovo.odemetakip.data.DatabaseHelper;
import com.yusufali.lenovo.odemetakip.data.GunuGelenOdemeler;
import com.yusufali.lenovo.odemetakip.data.OdemeTakipContract;
import com.yusufali.lenovo.odemetakip.data.Odemeler;
import com.yusufali.lenovo.odemetakip.data.OdemelerProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

// import br.com.goncalves.pugnotification.notification.PugNotification;


public class BildirimServisi extends IntentService {

    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;
    public static final Uri CONTENT_URI_GUNU_GELENLER= OdemelerProvider.CONTENT_URI_GUNU_GELEN_ODEMELER;

    public static final String TAG=Thread.currentThread().getName();

    ArrayList<Odemeler> odenmesiGerekenOdemelerListe=new ArrayList<>();
    NotificationManager notificationManager;
    @SuppressLint("ResourceAsColor")
    Notification builder;

    public BildirimServisi() {
        super("BildirimServisi");
      Log.d(TAG,"bildirim : consructor");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

       Log.d(TAG,"bildirim : onHandleIntent");





        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat mdformat2 = new SimpleDateFormat("HH");
        String strDate2 = mdformat2.format(calendar2.getTime());

        Log.e("saat",strDate2);

        if(strDate2.equals("23"))
        {
            //her gün saat 23 de odemeGunuGelenler tablom sıfırlansın ki bir sonraki ay ödemesi gelenler için
            //yeniden bildirim gönderilebilsin.

            gunuGelenOdemelerTablosunuTemizle();

            Log.e("saat","calisiyor.");
            //eğer saat 23 ise, ödenenler i temizle ki bir sonraki ay bildirim gidebilsin.
        }


        odenmesiGerekenOdemelerListe=odemeZamaniGelenOdemeleriGetir();











        for(Odemeler geciciOdeme:odenmesiGerekenOdemelerListe)
        {
            if(bildirimGerekli(geciciOdeme.getOdemeHatirlatmaAyGunu(),geciciOdeme.getOdemeKalanTaksitSayisi()))
            {//ödeme hatırlatması 1 olanları buluyor.



                //deneme ekleme.. eğer bildirim gerekliyse günü gelenlerde var mı kontrol etsin.

                /////////////////////////////////////////////TABLO SIFIRLAMA 23 DE/////////////////////////77

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH");
                String strDate = mdformat.format(calendar.getTime());

                Log.e("saat",strDate);


                if(strDate.equals("23"))
                {
                    //her gün saat 23 de odemeGunuGelenler tablom sıfırlansın ki bir sonraki ay ödemesi gelenler için
                    //yeniden bildirim gönderilebilsin.

                    gunuGelenOdemelerTablosunuTemizle();

                    Log.e("saat","calisiyor.");
                    //eğer saat 23 ise, ödenenler i temizle ki bir sonraki ay bildirim gidebilsin.
                }

                //////////////////////////////////////////////////77
             else  if(gunuGelenlerdeVarMi(geciciOdeme.getOdemeId()))
                {
                    //varsa ekleme yapmasın.
                    Log.e("var",geciciOdeme.getOdemeBaslik());

                    if(gunuGelenlerOdenmisMi(geciciOdeme.getOdemeId()))
                    {
                        //ödendi ise hiç bi şey yapmasın.
                        Log.e("odendi",geciciOdeme.getOdemeBaslik());
                    }
                    else
                    {
                        //ödenmedi ise bildirim yollasın.
                        Log.e("odenmedi",geciciOdeme.getOdemeBaslik());
                        bildirimYolla(geciciOdeme.getOdemeId(),geciciOdeme.getOdemeBaslik(),geciciOdeme.getOdemeAylikFiyat(),(geciciOdeme.getOdemeOdenenTaksitSayisi()+1),geciciOdeme.getOdemeParaBirimi());
                    }

                }
                else
                {
                    Log.e("yok",geciciOdeme.getOdemeBaslik());
                    bildirimYolla(geciciOdeme.getOdemeId(),geciciOdeme.getOdemeBaslik(),geciciOdeme.getOdemeAylikFiyat(),(geciciOdeme.getOdemeOdenenTaksitSayisi()+1),geciciOdeme.getOdemeParaBirimi());
                    //yoksa gunu gelenlere ekle ve bildirim gönder.
                    gunuGelenlereEkle(geciciOdeme.getOdemeId(),geciciOdeme.getOdemeBaslik(),geciciOdeme.getOdemeOdenenTaksitSayisi(),geciciOdeme.getOdemeKalanTaksitSayisi(),geciciOdeme.getOdemeAylikFiyat(),geciciOdeme.getOdemeParaBirimi());
                }


            }
        }



    }

    private void gunuGelenOdemelerTablosunuTemizle()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
       SQLiteDatabase database = dbHelper.getWritableDatabase();


        database.delete(OdemeTakipContract.GunuGelenOdemeler.TABLE_NAME, null,null);

     //   getContentResolver().delete(CONTENT_URI_GUNU_GELENLER,null,null);
    }

    private boolean gunuGelenlerOdenmisMi(int odemeId) {
        Cursor cursor = getContentResolver()
                .query(CONTENT_URI_GUNU_GELENLER, new String[]{"GunOdemeOdendimi"}, "GunOdemeId=?", new String[]{String.valueOf(odemeId)}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex("GunOdemeOdendimi")).equals("Ödendi")) {
                    return true;

                }
            }

        }
        return false;
    }

    private void gunuGelenlereEkle(int odemeId, String odemeBaslik, int odemeOdenenTaksitSayisi, int odemeKalanTaksitSayisi, int odemeAylikFiyat,String paraBirimi)
    {

        ContentValues values=new ContentValues();
        values.put("GunOdemeId",odemeId);
        values.put("GunOdemeBaslik", odemeBaslik);
        values.put("GunOdemeOdenenTaksitSayisi",odemeOdenenTaksitSayisi);
        values.put("GunOdemeKalanTaksitSayisi",odemeKalanTaksitSayisi);
        values.put("GunOdemeAylikFiyat",odemeAylikFiyat);
        values.put("GunOdemeParaBirimi",paraBirimi);



        Uri uri=getContentResolver().insert(CONTENT_URI_GUNU_GELENLER, values);



    }

    private boolean gunuGelenlerdeVarMi(int id)
    {

        //günü gelenler tablosuna bakacak. yoksa bu id ye ait bir değer false döndürür.
        //varsa eğer ödenip ödenmediğine bakar, ödenmemişse false döndürür. ödenmişse true;



        Cursor cursor = getContentResolver()
                .query(CONTENT_URI_GUNU_GELENLER, new String[]{"GunOdemeId"}, "GunOdemeId=?", new String[]{String.valueOf(id)}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

               /* if(cursor.getString(cursor.getColumnIndex("GunOdemeOdendimi")).equals("Ödenmedi"))
                {
                    return true;
                }
                */
               return true;


            }
        }


        return false;
    }



    private void bildirimYolla(int id, String baslik, int fiyat, int odenecekAy, String paraBirimi)
    {



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



        String CHANNEL_ID = "my_channel_01";//



       Intent pendingIntent=new Intent(this, ActivityGunuGelenler.class);
       pendingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent bildirimIntent=PendingIntent.getActivity(this,10,pendingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            String message="Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" "+paraBirimi+" Hadi görüşürüz bu kıyağımı da unutma :)";

        builder=new NotificationCompat.Builder(this,CHANNEL_ID)
       .setSmallIcon(R.drawable.appicon)
       .setContentTitle("ÖDEME GÜNÜN GELDİ DOSTUM!")

       .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.appicon))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle("ÖDEME GÜNÜN GELDİ DOSTUM!")
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
            .setContentText(message)


               // .setColor(R.color.anaYesil).setOnlyAlertOnce(true)
                //.addAction(R.drawable.iconum,"Şimdi Öde !",bildirimIntent)

            .setAutoCancel(true)
                .setContentIntent(bildirimIntent)
                .build();





        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
             @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Yeni Ödeme !", NotificationManager.IMPORTANCE_MAX);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationManager.notify(id /* Request Code */, builder);




       Log.d(TAG,"Aman unutayım deme, bugün "+baslik+" isimli ödemenin "+odenecekAy+". taksitini ödeyeceksin. Ödeyeceğin tutar : "+fiyat+" Hadi görüşürüz bu kıyağımı da unutma :)");

    }


    private boolean bildirimGerekli(int odemeHatirlatmaAyGunu,int kalanTaksitSayisi) {
        Calendar calendar = Calendar.getInstance();
        int bugun = calendar.get(Calendar.DAY_OF_MONTH);

        Log.e("ay",String.valueOf(calendar.MONTH)+" ss");

        int ay=calendar.MONTH;
        if(ay==2 && (bugun==28 || bugun==29) )
        {
            if(bugun==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }
            else if((bugun+1)==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }
            else if((bugun+2)==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }
            else if((bugun+3)==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }


        }
        else if((ay==4 || ay==6 || ay==9 || ay ==11) && bugun==30)
        {
            if(bugun==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }
            else if((bugun+1)==odemeHatirlatmaAyGunu)
            {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            }
        }

        else {
            if (bugun == odemeHatirlatmaAyGunu) {
                if (kalanTaksitSayisi > 0)
                    return true;
                else
                    return false;
            } else
                return false;
        }
        return false;


    }



    private ArrayList<Odemeler> odemeZamaniGelenOdemeleriGetir() {

        ArrayList<Odemeler> odenmesiGerekenler = new ArrayList<>();

        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor = getContentResolver()
                .query(CONTENT_URI, new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu,OdemeParaBirimi"}, "OdemeAylikHatirlat=?", new String[]{"1"}, null);


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
                geciciOdeme.setOdemeParaBirimi(cursor.getString(cursor.getColumnIndex("OdemeParaBirimi")));
                odenmesiGerekenler.add(geciciOdeme);


            }
        }
        return odenmesiGerekenler;
    }


}
