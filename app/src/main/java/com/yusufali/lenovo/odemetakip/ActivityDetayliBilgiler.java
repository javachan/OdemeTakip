package com.yusufali.lenovo.odemetakip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class ActivityDetayliBilgiler extends AppCompatActivity {

    private TextView detayliBaslik, detayliMiktar,detaliKalanTaksit,detayliOdenenTaksit;


    private Toolbar mTolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detayli_bilgiler);


        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");






        detayliBaslik=findViewById(R.id.txt_detayliBaslik);
        detayliMiktar=findViewById(R.id.txt_detayliFiyat);
        detaliKalanTaksit=findViewById(R.id.txt_detayliKalanTaksit);
        detayliOdenenTaksit=findViewById(R.id.txt_detayliOdenenTaksit);


        detayliBaslik.setText(
                getIntent().getExtras().getString("detayliBaslik")
        );

        detayliMiktar.setText(
                getIntent().getExtras().getString("detayliMiktar")+" TL"
        );

        detaliKalanTaksit.setText(
                "Kalan:"+getIntent().getExtras().getString("detayliKalan")+" Ay"
        );

        detayliOdenenTaksit.setText(
                "Ödenen:"+getIntent().getExtras().getString("detayliOdenen")+" Ay"
        );
    }
}
