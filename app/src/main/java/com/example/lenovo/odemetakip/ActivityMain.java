package com.example.lenovo.odemetakip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.bumptech.glide.Glide;





public class ActivityMain extends AppCompatActivity {

    CardView aa;
    private Button yeniOdemeButon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        aa=findViewById(R.id.cardview2);

        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, ":))", Toast.LENGTH_SHORT).show();

            }
        });

        yeniOdemeButon=findViewById(R.id.btn_yeni_odeme);
        yeniOdemeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityMain.this,ActivityYeniOdeme.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


        arkaplaniDegistir();
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
