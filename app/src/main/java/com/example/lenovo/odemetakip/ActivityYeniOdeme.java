package com.example.lenovo.odemetakip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class ActivityYeniOdeme extends AppCompatActivity {

    private Toolbar mTolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_odeme);

        arkaplaniDegistir();


        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");





    }

    private void arkaplaniDegistir()
    {
        ImageView arkaplanResim=findViewById(R.id.iv_yeniOArkaplanImage);
        Glide.with(this)
                .load(R.drawable.yeni_odeme_arkaplan)
                .into(arkaplanResim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        Log.d("ara","finish1");
    }



}
