package com.yusufali.lenovo.odemetakip.data;

public class GunuGelenOdemeler {
    private int GunOdemeId;
    private String GunOdemeBaslik;
    private int GunOdemeOdenenTaksitSayisi;
    private int GunOdemeKalanTaksitSayisi;
    private int GunOdemeAylikFiyat;
    private String GunOdemeOdendimi;
    private String GunOdemeParaBirimi;


    public GunuGelenOdemeler()
    {

    }

    public GunuGelenOdemeler(int gunOdemeId, String gunOdemeBaslik, int gunOdemeOdenenTaksitSayisi, int gunOdemeKalanTaksitSayisi, int gunOdemeAylikFiyat,String GunOdemeOdendimi,String GunOdemeParaBirimi) {
        GunOdemeId = gunOdemeId;
        GunOdemeBaslik = gunOdemeBaslik;
        GunOdemeOdenenTaksitSayisi = gunOdemeOdenenTaksitSayisi;
        GunOdemeKalanTaksitSayisi = gunOdemeKalanTaksitSayisi;
        GunOdemeAylikFiyat = gunOdemeAylikFiyat;
        this.GunOdemeOdendimi=GunOdemeOdendimi;
        this.GunOdemeParaBirimi=GunOdemeParaBirimi;
    }

    public int getGunOdemeId() {
        return GunOdemeId;
    }

    public void setGunOdemeId(int gunOdemeId) {
        GunOdemeId = gunOdemeId;
    }

    public String getGunOdemeBaslik() {
        return GunOdemeBaslik;
    }

    public void setGunOdemeBaslik(String gunOdemeBaslik) {
        GunOdemeBaslik = gunOdemeBaslik;
    }

    public int getGunOdemeOdenenTaksitSayisi() {
        return GunOdemeOdenenTaksitSayisi;
    }

    public void setGunOdemeOdenenTaksitSayisi(int gunOdemeOdenenTaksitSayisi) {
        GunOdemeOdenenTaksitSayisi = gunOdemeOdenenTaksitSayisi;
    }

    public int getGunOdemeKalanTaksitSayisi() {
        return GunOdemeKalanTaksitSayisi;
    }

    public void setGunOdemeKalanTaksitSayisi(int gunOdemeKalanTaksitSayisi) {
        GunOdemeKalanTaksitSayisi = gunOdemeKalanTaksitSayisi;
    }

    public int getGunOdemeAylikFiyat() {
        return GunOdemeAylikFiyat;
    }

    public void setGunOdemeAylikFiyat(int gunOdemeAylikFiyat) {
        GunOdemeAylikFiyat = gunOdemeAylikFiyat;
    }

    public String getGunOdemeOdendimi() {
        return GunOdemeOdendimi;
    }

    public void setGunOdemeOdendimi(String gunOdemeOdendimi) {
        GunOdemeOdendimi = gunOdemeOdendimi;
    }

    public String getGunOdemeParaBirimi() {
        return GunOdemeParaBirimi;
    }

    public void setGunOdemeParaBirimi(String gunOdemeParaBirimi) {
        GunOdemeParaBirimi = gunOdemeParaBirimi;
    }
}
