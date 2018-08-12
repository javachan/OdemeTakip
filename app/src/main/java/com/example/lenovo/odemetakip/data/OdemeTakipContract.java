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
        public static final String COLUMN_ODEME_PARA_BIRIMI="OdemeParaBirimi";
    }

    public  static final class GunuGelenOdemeler
    {
        public static final String TABLE_NAME="gunu_gelen_odemeler";
        public static final String _ID="GunOdemeId";
        public static final String COLUMN_ODEME_BASLIK="GunOdemeBaslik";
        public static final String COLUMN_ODEME_ODENEN_TAKSIT_SAYISI="GunOdemeOdenenTaksitSayisi";
        public static final String COLUMN_ODEME_KALAN_TAKSIT_SAYISI="GunOdemeKalanTaksitSayisi";
        public static final String COLUMN_ODEME_AYLIK_FIYAT="GunOdemeAylikFiyat";
        public static final String COLUMN_ODEME_ODENDIMI="GunOdemeOdendimi";
        public static final String COLUMN_ODEME_PARA_BIRIMI="GunOdemeParaBirimi";
    }




    //her ödedindi oldgunda bu tabloya eklenecek.
    //detaylı sayfası 2. fragment de ilgili id ye ait olan ödemeler gelecek sadece listeye.
    //bu yüzden id primary key olmayacak.
    public  static final class GecmisOdemeler
    {
        public static final String TABLE_NAME="gecmis_odemeler";
        public static final String _ID="GecmisOdemeId";
        public static final String COLUMN_ODEME_BASLIK="GecmisOdemeBaslik";
        public static final String COLUMN_ODEME_ODENEN_TAKSIT_SAYISI="GecmisOdemeOdenenTaksitSayisi";
        public static final String COLUMN_ODEME_ODEME_TARIHI="GecmisOdemeOdemeTarihi";
        public static final String COLUMN_ODEME_AYLIK_FIYAT="GecmisOdemeAylikFiyat";
        public static final String COLUMN_ODEME_PARA_BIRIMI="GecmisOdemeParaBirimi";

    }


}
