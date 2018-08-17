package com.example.lenovo.odemetakip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

@SuppressLint("ValidFragment")
public class FragmentDialogGuncelle extends DialogFragment {

    static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI; //ana linki aldık.

    static final Uri CONTENT_URI_GECMIS_ODEMELER= OdemelerProvider.CONTENT_URI_GECMIS_ODEMELER; //ana linki aldık.

    private Button guncelleButton;
    private ImageButton cancelButton;
    private EditText guncelleBaslik_ed;
    private Spinner guncelleKategori_sp;
    private Spinner  guncelleGunler_sp;
    private EditText guncelleMiktar_ed;

    private Switch guncelleAylikHatirlasinMi_sw;
    private Spinner  guncelleParaBirimi_sp;
    private EditText guncelleKalanTaksit_ed;
    private EditText guncelleOdenenTaksit_ed;

    Odemeler guncellenecekOdeme;

    @SuppressLint("ValidFragment")
    public FragmentDialogGuncelle(Odemeler guncellenecekOdeme)
    {

        this.guncellenecekOdeme=guncellenecekOdeme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guncelle,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            guncelleButton=view.findViewById(R.id.btn_guncelle_button);
            cancelButton=view.findViewById(R.id.imageButton_guncelle_cancel);
             guncelleBaslik_ed=view.findViewById(R.id.ed_guncelle_baslik);
        guncelleKategori_sp=view.findViewById(R.id.sp_guncelle_kategori);
            guncelleGunler_sp=view.findViewById(R.id.sp_guncelle_gunler);
            guncelleMiktar_ed=view.findViewById(R.id.ed_guncelle_odemeMiktar);
           // guncelleTaksitSayisi_sp=view.findViewById(R.id.sp_guncelle_taksitSayisi);
            guncelleAylikHatirlasinMi_sw=view.findViewById(R.id.sw_guncelle_aylikHatirlansinMi);
            guncelleParaBirimi_sp=view.findViewById(R.id.sp_guncelle_paraBirimi);
        guncelleKalanTaksit_ed=view.findViewById(R.id.ed_guncelle_kalanTaksit);
        guncelleOdenenTaksit_ed=view.findViewById(R.id.ed_guncelle_odenenTaksit);


        guncelleKalanTaksit_ed.setText(String.valueOf(guncellenecekOdeme.getOdemeKalanTaksitSayisi()));
        guncelleOdenenTaksit_ed.setText(String.valueOf(guncellenecekOdeme.getOdemeOdenenTaksitSayisi()));


        guncelleBaslik_ed.setText(guncellenecekOdeme.getOdemeBaslik());
        if(guncellenecekOdeme.getOdemeAylikHatirlat()==1)
        {
            guncelleAylikHatirlasinMi_sw.setChecked(true);
        }
        else
            guncelleAylikHatirlasinMi_sw.setChecked(false);

        guncelleGunler_sp.setSelection(guncellenecekOdeme.getOdemeHatirlatmaAyGunu()-1);

        guncelleMiktar_ed.setText(String.valueOf(guncellenecekOdeme.getOdemeAylikFiyat()));





        int TaksitCountIndex=0;
        switch (guncellenecekOdeme.getOdemeKalanTaksitSayisi()+guncellenecekOdeme.getOdemeOdenenTaksitSayisi())
        {
            case 48:
            {
                TaksitCountIndex=10;
                break;
            }
            case 36:
            {
                TaksitCountIndex=9;
                break;
            }
            case 24:
            {
                TaksitCountIndex=8;
                break;
            }
            case 18:
            {
                TaksitCountIndex=7;
                break;
            }
            case 15:
            {
                TaksitCountIndex=6;
                break;
            }
            case 12:
            {
                TaksitCountIndex=5;
                break;
            }
            case 9:
            {
                TaksitCountIndex=4;
                break;
            }
            case 6:
            {
                TaksitCountIndex=3;
                break;
            }
            case 3:
            {
                TaksitCountIndex=2;
                break;
            }
            case 1:
            {
                TaksitCountIndex=1;
                break;
            }
            case 0:
            {
                TaksitCountIndex=0;
                break;
            }
            default: TaksitCountIndex=0;

        }




        int ParaBirimiCountIndex=0;
        switch (guncellenecekOdeme.getOdemeParaBirimi())
        {
            case "₺":ParaBirimiCountIndex=0;
                break;
            case "$":ParaBirimiCountIndex=1;
                break;
            case "€":ParaBirimiCountIndex=2;
                break;
            case "£":ParaBirimiCountIndex=3;
                break;
            case "฿":ParaBirimiCountIndex=4;
                break;
                default:ParaBirimiCountIndex=0;
        }

        guncelleParaBirimi_sp.setSelection(ParaBirimiCountIndex);


        final String gKategori=kategoriBul(guncellenecekOdeme.getOdemeId());

        int KategoriCountIndex=0;
        switch (gKategori)
        {
            case "Ev":KategoriCountIndex=0;
                break;
            case "Alışveriş":KategoriCountIndex=1;
                break;
            case "Kişisel":KategoriCountIndex=2;
                break;
            case "Bitenler":KategoriCountIndex=3;
                break;
                default:KategoriCountIndex=0;
        }


        guncelleKategori_sp.setSelection(KategoriCountIndex);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        guncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Emin misiniz?");
                builder.setMessage("Eğer herhangi bir değişiklik yaptıysanız bunlar keydedilecektir.");
                builder.setPositiveButton("Evet",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                int hatirlansinMi=0;
                                if(guncelleAylikHatirlasinMi_sw.isChecked())
                                        hatirlansinMi=1;

                                String odemeBasligi=guncelleBaslik_ed.getText().toString();
                                String kategori=guncelleKategori_sp.getSelectedItem().toString();
                                int hatirlatmaAyGunu=Integer.valueOf(guncelleGunler_sp.getSelectedItem().toString());
                                int odenecekMiktar=Integer.valueOf(guncelleMiktar_ed.getText().toString());
                               // int taksitSayisi=Integer.valueOf(guncelleTaksitSayisi_sp.getSelectedItem().toString());

                               int kalanTaksit=Integer.valueOf(guncelleKalanTaksit_ed.getText().toString());
                               int odenenTaksit=Integer.valueOf(guncelleOdenenTaksit_ed.getText().toString());
                                String paraBirimi=guncelleParaBirimi_sp.getSelectedItem().toString();

                                verileriGuncelle(guncellenecekOdeme.getOdemeId(),hatirlansinMi,odemeBasligi,kategori,hatirlatmaAyGunu,odenecekMiktar,paraBirimi,kalanTaksit,odenenTaksit);

                               // ((ActivityDetayliBilgiler2)getActivity().veriAnlıkGuncelle(hatirlansinMi,odemeBasligi,kategori,hatirlatmaAyGunu,odenecekMiktar,taksitSayisi,paraBirimi));

                                dismiss();
                                Intent ii=new Intent(getContext(),ActivityMain.class);
                                startActivity(ii);
                                getActivity().finish();
                                Toast.makeText(getContext(), "Bilgileriniz güncellendi", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();



            }


        });
    }

    private void verileriGuncelle(int guncelleID, int hatirlansinMi, String odemeBasligi, String kategori, int hatirlatmaAyGunu, int odenecekMiktar, String paraBirimi,int kalanTaksit, int odenenTaksit)
    {
        ContentValues values=new ContentValues();
        values.put("OdemeBaslik", odemeBasligi);
        values.put("OdemeKategoriAdi",kategori);



        values.put("OdemeKalanTaksitSayisi",kalanTaksit);
        values.put("OdemeOdenenTaksitSayisi",odenenTaksit);
        values.put("OdemeAylikFiyat",odenecekMiktar);
        values.put("OdemeAylikHatirlat",hatirlansinMi);
        values.put("OdemeHatirlatmaAyGunu",hatirlatmaAyGunu);
        values.put("OdemeParaBirimi",paraBirimi);


        getActivity().getContentResolver().update(CONTENT_URI,values,"OdemeId=?",new String[]{String.valueOf(guncelleID)});


        ContentValues values2=new ContentValues();
        values2.put("GecmisOdemeBaslik",odemeBasligi);

        getActivity().getContentResolver().update(CONTENT_URI_GECMIS_ODEMELER,values2,"GecmisOdemeId=?",new String[]{String.valueOf(guncelleID)});
    }

    private String kategoriBul(int odemeId)
    {

        String donecek="";

        Cursor cursor=getActivity().getContentResolver().query(CONTENT_URI,new String[]{"OdemeId,OdemeBaslik,OdemeKategoriAdi,OdemeOdenenTaksitSayisi,OdemeKalanTaksitSayisi,OdemeAylikFiyat,OdemeAylikHatirlat,OdemeHatirlatmaAyGunu,OdemeParaBirimi"},"OdemeId=?",new String []{String.valueOf(odemeId)},null);



        if(cursor!=null) {
            while (cursor.moveToNext()) {


                donecek=(cursor.getString(cursor.getColumnIndex("OdemeKategoriAdi")));


            }
        }
        return donecek;
    }
}
