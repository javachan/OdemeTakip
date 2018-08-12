package com.example.lenovo.odemetakip;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.odemetakip.adapter.AdapterGecmisOdemelerListesi;
import com.example.lenovo.odemetakip.data.GecmisOdemeler;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class FragmentTab2 extends Fragment {

    static final Uri CONTENT_URI_GECMIS_ODEMELER= OdemelerProvider.CONTENT_URI_GECMIS_ODEMELER; //ana linki aldık.
    private RecyclerView rv_gecmisOdemelerRecycListe;
    private AdapterGecmisOdemelerListesi mAdapterGecmisOdemelerListesi;
    ArrayList<GecmisOdemeler> tumIDselGecmisOdemeDetayli;
    int gelenID;


    @SuppressLint("ValidFragment")
    public FragmentTab2(int id)
    {
        gelenID=id;


    }


    private ArrayList<GecmisOdemeler> IDyeGoreTumEskiOdemeleriGetir()
    {
        ArrayList<GecmisOdemeler> gecmisOdemeler=new ArrayList<>();



        //tüm eski ödemeleri aldım. YALNIZCA ID Sİ İLGİLİ ÖDEME OLANIN ESKİ ÖDEME BİLGİLERİNİ GETİRDİM.
        Cursor cursor=getActivity().getContentResolver().query(CONTENT_URI_GECMIS_ODEMELER,new String[]{"GecmisOdemeId,GecmisOdemeBaslik,GecmisOdemeOdenenTaksitSayisi,GecmisOdemeAylikFiyat,GecmisOdemeOdemeTarihi"},"GecmisOdemeId=?",new String[]{String.valueOf(gelenID)},null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {

                GecmisOdemeler gecmisOdeme = new GecmisOdemeler();
                gecmisOdeme.setGecmisOdemeId(cursor.getInt(cursor.getColumnIndex("GecmisOdemeId")));
                gecmisOdeme.setGecmisOdemeBaslik(cursor.getString(cursor.getColumnIndex("GecmisOdemeBaslik")));
                gecmisOdeme.setGecmisOdemeOdenenTaksitSayisi(cursor.getInt(cursor.getColumnIndex("GecmisOdemeOdenenTaksitSayisi")));
                gecmisOdeme.setGecmisOdemeAylikFiyat(cursor.getInt(cursor.getColumnIndex("GecmisOdemeAylikFiyat")));
                gecmisOdeme.setGecmisOdemeOdemeTarihi(cursor.getString(cursor.getColumnIndex("GecmisOdemeOdemeTarihi")));


                gecmisOdemeler.add(gecmisOdeme);

            }
        }


        return gecmisOdemeler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detayli_bilgiler_tab2,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rv_gecmisOdemelerRecycListe=view.findViewById(R.id.rc_gecmis_odemeler_Liste_idsel);

        tumIDselGecmisOdemeDetayli=IDyeGoreTumEskiOdemeleriGetir();

        if(tumIDselGecmisOdemeDetayli.size()<1)
        {
            Toast.makeText(getContext(), "Bu isme ait henüz geçmiş bir ödemeniz yok", Toast.LENGTH_LONG).show();
        }

        else {
            LinearLayoutManager manager = new LinearLayoutManager(getContext());

            rv_gecmisOdemelerRecycListe.setLayoutManager(manager);

            mAdapterGecmisOdemelerListesi = new AdapterGecmisOdemelerListesi(getContext(), tumIDselGecmisOdemeDetayli);

            rv_gecmisOdemelerRecycListe.setAdapter(mAdapterGecmisOdemelerListesi);

            dataGuncelle();
        }
    }

    public void dataGuncelle()
    {
        tumIDselGecmisOdemeDetayli.clear();
        tumIDselGecmisOdemeDetayli=IDyeGoreTumEskiOdemeleriGetir();
        mAdapterGecmisOdemelerListesi.update(tumIDselGecmisOdemeDetayli);
    }
}
