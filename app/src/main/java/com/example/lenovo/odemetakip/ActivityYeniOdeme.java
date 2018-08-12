package com.example.lenovo.odemetakip;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.odemetakip.data.OdemelerProvider;


public class ActivityYeniOdeme extends AppCompatActivity {

    private Toolbar mTolbar;
    private EditText odemeBaslik_ed;
    private Spinner  odemeKategori_sp;
    private Spinner  odemeGunler_sp;
    private EditText odemeMiktar_ed;
    private Spinner  odemeTaksitSayisi_sp;
    private Switch   odemeAylikHatirlasinMi_sw;
    private Button   yeniOdemeEkleButon;
    private Spinner  odemeParaBirimi_sp;


    public static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_odeme);

        arkaplaniDegistir();

        odemeBaslik_ed=findViewById(R.id.ed_yeniOdeme_baslik);
        odemeKategori_sp=findViewById(R.id.sp_yeniOdeme_kategori);
        odemeGunler_sp=findViewById(R.id.sp_yeniOdeme_gunler);
        odemeMiktar_ed=findViewById(R.id.ed_yeniOdeme_odemeMiktar);
        odemeTaksitSayisi_sp=findViewById(R.id.sp_yeniOdeme_taksitSayisi);
        odemeAylikHatirlasinMi_sw=findViewById(R.id.sw_yeniOdeme_aylikHatirlansinMi);
        yeniOdemeEkleButon=findViewById(R.id.btn_yeniOdeme_ekleButon);
        odemeParaBirimi_sp=findViewById(R.id.sp_yeniOdeme_paraBirimi);






        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        yeniOdemeEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eklenecekVerileriKontrolEt(); //null olan giriş var mı, boş kutucuk kalmaması için kontrol et.
            }
        });




    }

    private void eklenecekVerileriKontrolEt()
    {
        if(TextUtils.isEmpty(odemeBaslik_ed.getText().toString()) || TextUtils.isEmpty(odemeMiktar_ed.getText().toString()))
        {
            Toast.makeText(this, "Lütfen tüm zorunlu alanları doldurun", Toast.LENGTH_SHORT).show();
        }
        else
        {
            verileriEkle();
        }
    }

    private void verileriEkle()
    {
        int hatirlansinMi=0;
        String odemeBasligi=odemeBaslik_ed.getText().toString();
        String kategori=odemeKategori_sp.getSelectedItem().toString();
        int hatirlatmaAyGunu=Integer.valueOf(odemeGunler_sp.getSelectedItem().toString());
        int odenecekMiktar=Integer.valueOf(odemeMiktar_ed.getText().toString());
        int taksitSayisi=Integer.valueOf(odemeTaksitSayisi_sp.getSelectedItem().toString());
        String paraBirimi=odemeParaBirimi_sp.getSelectedItem().toString();



        if(odemeAylikHatirlasinMi_sw.isChecked())
                hatirlansinMi=1;
        else
                hatirlansinMi=0;


        ContentValues values=new ContentValues();
        values.put("OdemeBaslik", odemeBasligi);
        values.put("OdemeKategoriAdi",kategori);
        values.put("OdemeKalanTaksitSayisi",taksitSayisi);
        values.put("OdemeAylikFiyat",odenecekMiktar);
        values.put("OdemeAylikHatirlat",hatirlansinMi);
        values.put("OdemeHatirlatmaAyGunu",hatirlatmaAyGunu);
        values.put("OdemeParaBirimi",paraBirimi);
        //id ve onenenTaksitSayisi n, eklemedik. id oto increment, onenenTaksitSayisi da default 0 old için.


        Uri uri=getContentResolver().insert(CONTENT_URI, values);
        //fragment olmadigi için getActivity yazma gereği duymadik.. activity de old için direk getContentResolverdan basladk.

        Toast.makeText(this, "Ödeme eklendi.. "+uri, Toast.LENGTH_SHORT).show();



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
