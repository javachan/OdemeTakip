package com.example.lenovo.odemetakip;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.odemetakip.R;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

@SuppressLint("ValidFragment")
public class FragmentDialogOdeme  extends DialogFragment
{

    static final Uri CONTENT_URI_GUNU_GELENLER= OdemelerProvider.CONTENT_URI_GUNU_GELEN_ODEMELER; //ana linki aldık.
    static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI; //ana linki aldık.

    private Button evetOdendi;
    private Button hayirOdenmedi;

    private int guncellenecekID;


    public FragmentDialogOdeme(int gelenId)
    {
        Log.e("deneme5",String.valueOf(gelenId));
        this.guncellenecekID=gelenId;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_odeme,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        evetOdendi=view.findViewById(R.id.fragment_evetButton);
        hayirOdenmedi=view.findViewById(R.id.fragment_hayirButton);



        hayirOdenmedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        evetOdendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ayrıca en son ödenen taksit sayısını alınır, (örn: 1. taksit ödenecek..)
                /*

                buna +1 eklenir ödenen taksitler update edilir.
                kalantaksit-1 edilir.
                zaten 0 olduktan sonra bildirim göndermiyor.

                 */
                Odemeler anaVeriler=odemelerTablosundakiVerileriGetir(guncellenecekID);


                //ilk tablodaki bilgileri aldıktan sonra bunları update etmek için başka bir metoda yollarız.

                int odenenTaksitSayisi=anaVeriler.getOdemeOdenenTaksitSayisi();
                int kalanTaksitSayisi=anaVeriler.getOdemeKalanTaksitSayisi();

                Log.e("err","odenen: "+String.valueOf(odenenTaksitSayisi+1));



                anaVeriler.setOdemeOdenenTaksitSayisi(odenenTaksitSayisi+1);
                anaVeriler.setOdemeKalanTaksitSayisi(kalanTaksitSayisi-1);


                anaVerileriGuncelle(anaVeriler);



                //update komutu çalışacak.
                //consructor ile gelen baslik adi, kalan ay vs. göre ilgili veriyi bulup güncelleyecek günügelenler tablosunda.



                ContentValues values=new ContentValues();
                values.put("GunOdemeOdendimi","Ödendi");
                values.put("GunOdemeOdenenTaksitSayisi",(odenenTaksitSayisi+1));
                values.put("GunOdemeKalanTaksitSayisi",(kalanTaksitSayisi-1));


                getActivity().getContentResolver().update(CONTENT_URI_GUNU_GELENLER,values,"GunOdemeId=?",new String[]{String.valueOf(guncellenecekID)});
                Log.e("guncelleme","Guncellendi.");
                ((ActivityGunuGelenler)getActivity()).dataGuncelle();






                dismiss();
            }
        });

    }

    private void anaVerileriGuncelle(Odemeler anaVeriler)
    {
        ContentValues values=new ContentValues();
        values.put("OdemeOdenenTaksitSayisi",anaVeriler.getOdemeOdenenTaksitSayisi());
        values.put("OdemeKalanTaksitSayisi",anaVeriler.getOdemeKalanTaksitSayisi());


        getActivity().getContentResolver().update(CONTENT_URI,values,"OdemeId=?",new String[]{String.valueOf(guncellenecekID)});
        Log.e("guncelleme","Guncellendi... Bir sonraki ödenen: "+String.valueOf(anaVeriler.getOdemeOdenenTaksitSayisi())+"Kalan: "+String.valueOf(anaVeriler.getOdemeKalanTaksitSayisi()));

    }

    private Odemeler odemelerTablosundakiVerileriGetir(int guncellenecekID)
    {
        //2. yi null geçtik hepsini getir dedik columnların.
        Cursor cursor=getActivity().getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu"},"OdemeId=?",new String[]{String.valueOf(guncellenecekID)},null);

        Odemeler geciciOdeme = new Odemeler();

        if(cursor!=null) {
            while (cursor.moveToNext()) {


                geciciOdeme.setOdemeId(cursor.getInt(cursor.getColumnIndex("OdemeId")));
                geciciOdeme.setOdemeBaslik(cursor.getString(cursor.getColumnIndex("OdemeBaslik")));
                geciciOdeme.setOdemeKategoriAdi(cursor.getString(cursor.getColumnIndex("OdemeKategoriAdi")));
                geciciOdeme.setOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeOdenenTaksitSayisi")));
                geciciOdeme.setOdemeKalanTaksitSayisi(cursor.getInt(cursor.getColumnIndex("OdemeKalanTaksitSayisi")));
                geciciOdeme.setOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("OdemeAylikFiyat")));
                geciciOdeme.setOdemeAylikHatirlat(cursor.getInt(cursor.getColumnIndex("OdemeAylikHatirlat")));
                geciciOdeme.setOdemeHatirlatmaAyGunu(cursor.getInt(cursor.getColumnIndex("OdemeHatirlatmaAyGunu")));



            }
        }
        return geciciOdeme;
    }

}
