package com.yusufali.lenovo.odemetakip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusufali.lenovo.odemetakip.R;
import com.yusufali.lenovo.odemetakip.data.Odemeler;

@SuppressLint("ValidFragment")
public class FragmentTab1 extends Fragment {
    private TextView detayliBaslik, detayliMiktar,detayliKalanTaksit,detayliOdenenTaksit,detayliHatirlatmaGun,detayliHatirlatilsinMi;
    private Odemeler putExtrasOdemeler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detayli_bilgiler_tab1, container, false);
        return view;
    }

    @SuppressLint("ValidFragment")
    public FragmentTab1(Odemeler putExtrasOdemeler) {

        this.putExtrasOdemeler=putExtrasOdemeler;

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        detayliBaslik=view.findViewById(R.id.txt_detayliBaslik);
        detayliMiktar=view.findViewById(R.id.txt_detayliFiyat);
        detayliKalanTaksit=view.findViewById(R.id.txt_detayliKalanTaksit);
        detayliOdenenTaksit=view.findViewById(R.id.txt_detayliOdenenTaksit);
        detayliHatirlatilsinMi=view.findViewById(R.id.txt_detayliAylikHatirlansinmi);
        detayliHatirlatmaGun=view.findViewById(R.id.txt_detayliOdemeGunu);



        detayliBaslik.setText(putExtrasOdemeler.getOdemeBaslik());
        detayliMiktar.setText(String.valueOf(putExtrasOdemeler.getOdemeAylikFiyat())+" "+putExtrasOdemeler.getOdemeParaBirimi());
        detayliKalanTaksit.setText("Kalan Taksit Sayısı : "+String.valueOf(putExtrasOdemeler.getOdemeKalanTaksitSayisi()));
        detayliOdenenTaksit.setText("Ödenen Taksit Sayısı : "+String.valueOf(putExtrasOdemeler.getOdemeOdenenTaksitSayisi()));

        String hatirlatma="Kapalı";
        if(putExtrasOdemeler.getOdemeAylikHatirlat()==1)
        {
            hatirlatma="Açık";
        }
        detayliHatirlatilsinMi.setText("Aylık Hatırlatma : "+hatirlatma);
        detayliHatirlatmaGun.setText("Ödeme her ayın "+String.valueOf(putExtrasOdemeler.getOdemeHatirlatmaAyGunu()+". günüdür." ));


    }


    public void veriAnlıkGuncelle(int hatirlansinMi, String odemeBasligi, String kategori, int hatirlatmaAyGunu, int odenecekMiktar, int taksitSayisi, String paraBirimi)
    {
        String hatirlatma="Kapalı";

        detayliBaslik.setText(odemeBasligi);
        detayliMiktar.setText(paraBirimi);
        detayliKalanTaksit.setText("Kalan Taksit Sayısı : "+String.valueOf(odenecekMiktar));
        if(hatirlansinMi==1)
            hatirlatma="Açık";
        detayliHatirlatilsinMi.setText("Aylık Hatırlatma : "+hatirlatma);
        detayliHatirlatmaGun.setText("Ödeme her ayın "+String.valueOf(hatirlatmaAyGunu+". günüdür." ));
    }
}
