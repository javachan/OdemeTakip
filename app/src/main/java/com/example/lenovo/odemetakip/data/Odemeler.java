package com.example.lenovo.odemetakip.data;

public class Odemeler {

    private int OdemeId;
    private String OdemeBaslik;
    private String OdemeKategoriAdi;
    private int OdemeOdenenTaksitSayisi;
    private int OdemeKalanTaksitSayisi;
    private int OdemeAylikFiyat;
    private int OdemeAylikHatirlat;
    private int OdemeHatirlatmaAyGunu;

    public Odemeler(int odemeId, String odemeBaslik, String odemeKategoriAdi, int odemeOdenenTaksitSayisi, int odemeKalanTaksitSayisi, int odemeAylikFiyat, int odemeAylikHatirlat, int odemeHatirlatmaAyGunu) {
        OdemeId = odemeId;
        OdemeBaslik = odemeBaslik;
        OdemeKategoriAdi = odemeKategoriAdi;
        OdemeOdenenTaksitSayisi = odemeOdenenTaksitSayisi;
        OdemeKalanTaksitSayisi = odemeKalanTaksitSayisi;
        OdemeAylikFiyat = odemeAylikFiyat;
        OdemeAylikHatirlat = odemeAylikHatirlat;
        OdemeHatirlatmaAyGunu = odemeHatirlatmaAyGunu;
    }



    public Odemeler()
    {

    }

    public int getOdemeId() {
        return OdemeId;
    }

    public void setOdemeId(int odemeId) {
        OdemeId = odemeId;
    }

    public String getOdemeBaslik() {
        return OdemeBaslik;
    }

    public void setOdemeBaslik(String odemeBaslik) {
        OdemeBaslik = odemeBaslik;
    }

    public String getOdemeKategoriAdi() {
        return OdemeKategoriAdi;
    }

    public void setOdemeKategoriAdi(String odemeKategoriAdi) {
        OdemeKategoriAdi = odemeKategoriAdi;
    }



    public int getOdemeOdenenTaksitSayisi() {
        return OdemeOdenenTaksitSayisi;
    }

    public void setOdemeOdenenTaksitSayisi(int odemeOdenenTaksitSayisi) {
        OdemeOdenenTaksitSayisi = odemeOdenenTaksitSayisi;
    }


    public int getOdemeKalanTaksitSayisi() {
        return OdemeKalanTaksitSayisi;
    }

    public void setOdemeKalanTaksitSayisi(int odemeKalanTaksitSayisi) {
        OdemeKalanTaksitSayisi = odemeKalanTaksitSayisi;
    }

    public int getOdemeAylikFiyat() {
        return OdemeAylikFiyat;
    }

    public void setOdemeAylikFiyat(int odemeAylikFiyat) {
        OdemeAylikFiyat = odemeAylikFiyat;
    }

    public int getOdemeAylikHatirlat() {
        return OdemeAylikHatirlat;
    }

    public void setOdemeAylikHatirlat(int odemeAylikHatirlat) {
        OdemeAylikHatirlat = odemeAylikHatirlat;
    }

    public int getOdemeHatirlatmaAyGunu() {
        return OdemeHatirlatmaAyGunu;
    }

    public void setOdemeHatirlatmaAyGunu(int odemeHatirlatmaAyGunu) {
        OdemeHatirlatmaAyGunu = odemeHatirlatmaAyGunu;
    }
}
