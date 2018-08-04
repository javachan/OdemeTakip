package com.example.lenovo.odemetakip.data;

import android.provider.BaseColumns;

public class OdemeTakipContract {


    public static final class Odemeler
    {

        public static final String TABLE_NAME="odemeler";
        public static final String _ID="OdemeId";
        public static final String COLUMN_ODEME_BASLIK="OdemeBaslik";
        public static final String COLUMN_ODEME_KATEGORI_ADI="OdemeKategoriAdi";
        public static final String COLUMN_ODEME_ODENEN_TAKSIT_SAYISI="OdemeOdenenTaksitSayisi";
        public static final String COLUMN_ODEME_KALAN_TAKSIT_SAYISI="OdemeKalanTaksitSayisi";
        public static final String COLUMN_ODEME_AYLIK_FIYAT="OdemeAylikFiyat";
        public static final String COLUMN_ODEME_AYLIK_HATIRLAT="OdemeAylikHatirlat";
        public static final String COLUMN_ODEME_HATIRLATMA_AY_GUNU="OdemeHatirlatmaAyGunu";

    }





}