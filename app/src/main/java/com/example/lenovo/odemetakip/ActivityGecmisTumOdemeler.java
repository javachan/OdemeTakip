package com.example.lenovo.odemetakip;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.lenovo.odemetakip.data.GecmisOdemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class ActivityGecmisTumOdemeler extends AppCompatActivity {

    static final Uri CONTENT_URI_GECMIS_ODEMELER= OdemelerProvider.CONTENT_URI_GECMIS_ODEMELER; //ana linki aldık.

    private TextView gecmisTxtDeneme;
    private ArrayList<GecmisOdemeler> tumGecmisOdemeler;
    private Toolbar mTolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis_tum_odemeler);



        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");



        gecmisTxtDeneme=findViewById(R.id.txt_deneme_gecmis);

        tumGecmisOdemeler=tumGecmisOdemeleriGetir();

        for(int i=0;i<tumGecmisOdemeler.size();i++) {
            gecmisTxtDeneme.setText(tumGecmisOdemeler.get(i).getGecmisOdemeBaslik()+" nın \n"+
            tumGecmisOdemeler.get(i).getGecmisOdemeOdenenTaksitSayisi()+". taksiti "+
            tumGecmisOdemeler.get(i).getGecmisOdemeOdemeTarihi()+" tarihinde ödenmiştir.");
        }

    }

    private ArrayList<GecmisOdemeler> tumGecmisOdemeleriGetir()
    {
        ArrayList<GecmisOdemeler> tumEskiOdemeler=new ArrayList<>();

        //tüm eski ödemeleri aldım.
        Cursor cursor=getContentResolver().query(CONTENT_URI_GECMIS_ODEMELER,new String[]{"GecmisOdemeId,GecmisOdemeBaslik,GecmisOdemeOdenenTaksitSayisi,GecmisOdemeAylikFiyat,GecmisOdemeOdemeTarihi"},null,null,null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {

                GecmisOdemeler gecmisOdeme = new GecmisOdemeler();
                gecmisOdeme.setGecmisOdemeId(cursor.getInt(cursor.getColumnIndex("GecmisOdemeId")));
                gecmisOdeme.setGecmisOdemeBaslik(cursor.getString(cursor.getColumnIndex("GecmisOdemeBaslik")));
                gecmisOdeme.setGecmisOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("GecmisOdemeOdenenTaksitSayisi")));
                gecmisOdeme.setGecmisOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("GecmisOdemeAylikFiyat")));
                gecmisOdeme.setGecmisOdemeOdemeTarihi(cursor.getString(cursor.getColumnIndex("GecmisOdemeOdemeTarihi")));


                tumEskiOdemeler.add(gecmisOdeme);

            }
        }


        return tumEskiOdemeler;
    }
}
