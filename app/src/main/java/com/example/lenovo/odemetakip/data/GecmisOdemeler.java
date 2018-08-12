package com.example.lenovo.odemetakip.data;

public class GecmisOdemeler {

    private int GecmisOdemeId;
    private String GecmisOdemeBaslik;
    private int GecmisOdemeOdenenTaksitSayisi;
    private int GecmisOdemeAylikFiyat;
    private String  GecmisOdemeOdemeTarihi;
    private String  GecmisOdemeParaBirimi;

    public GecmisOdemeler()
    {

    }

    public GecmisOdemeler(String odemeTarihi,int gecmisOdemeId, String gecmisOdemeBaslik, int gecmisOdemeOdenenTaksitSayisi, int gecmisOdemeAylikFiyat, String GecmisOdemeParaBirimi) {
        GecmisOdemeId = gecmisOdemeId;
        GecmisOdemeBaslik = gecmisOdemeBaslik;
        GecmisOdemeOdenenTaksitSayisi = gecmisOdemeOdenenTaksitSayisi;
        GecmisOdemeAylikFiyat = gecmisOdemeAylikFiyat;
        this.GecmisOdemeOdemeTarihi=odemeTarihi;
        this.GecmisOdemeParaBirimi=GecmisOdemeParaBirimi;
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

    public String getGecmisOdemeParaBirimi() {
        return GecmisOdemeParaBirimi;
    }

    public void setGecmisOdemeParaBirimi(String gecmisOdemeParaBirimi) {
        GecmisOdemeParaBirimi = gecmisOdemeParaBirimi;
    }
}
