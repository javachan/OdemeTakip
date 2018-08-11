package com.example.lenovo.odemetakip.data;

public class GecmisOdemeler {

    private int GecmisOdemeId;
    private String GecmisOdemeBaslik;
    private int GecmisOdemeOdenenTaksitSayisi;
    private int GecmisOdemeAylikFiyat;
    private String  GecmisOdemeOdemeTarihi;


    public GecmisOdemeler()
    {

    }

    public GecmisOdemeler(String odemeTarihi,int gecmisOdemeId, String gecmisOdemeBaslik, int gecmisOdemeOdenenTaksitSayisi, int gecmisOdemeAylikFiyat) {
        GecmisOdemeId = gecmisOdemeId;
        GecmisOdemeBaslik = gecmisOdemeBaslik;
        GecmisOdemeOdenenTaksitSayisi = gecmisOdemeOdenenTaksitSayisi;
        GecmisOdemeAylikFiyat = gecmisOdemeAylikFiyat;
        this.GecmisOdemeOdemeTarihi=odemeTarihi;
    }

    public String getGecmisOdemeOdemeTarihi() {
        return GecmisOdemeOdemeTarihi;
    }

    public void setGecmisOdemeOdemeTarihi(String odemeTarihi) {
        this.GecmisOdemeOdemeTarihi = odemeTarihi;
    }

    public int getGecmisOdemeId() {
        return GecmisOdemeId;
    }

    public void setGecmisOdemeId(int gecmisOdemeId) {
        GecmisOdemeId = gecmisOdemeId;
    }

    public String getGecmisOdemeBaslik() {
        return GecmisOdemeBaslik;
    }

    public void setGecmisOdemeBaslik(String gecmisOdemeBaslik) {
        GecmisOdemeBaslik = gecmisOdemeBaslik;
    }

    public int getGecmisOdemeOdenenTaksitSayisi() {
        return GecmisOdemeOdenenTaksitSayisi;
    }

    public void setGecmisOdemeOdenenTaksitSayisi(int gecmisOdemeOdenenTaksitSayisi) {
        GecmisOdemeOdenenTaksitSayisi = gecmisOdemeOdenenTaksitSayisi;
    }

    public int getGecmisOdemeAylikFiyat() {
        return GecmisOdemeAylikFiyat;
    }

    public void setGecmisOdemeAylikFiyat(int gecmisOdemeAylikFiyat) {
        GecmisOdemeAylikFiyat = gecmisOdemeAylikFiyat;
    }
}
