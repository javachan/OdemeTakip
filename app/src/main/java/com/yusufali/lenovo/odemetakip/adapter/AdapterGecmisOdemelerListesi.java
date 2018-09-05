package com.yusufali.lenovo.odemetakip.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusufali.lenovo.odemetakip.R;
import com.yusufali.lenovo.odemetakip.data.GecmisOdemeler;
import com.yusufali.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;
import java.util.Random;

public class AdapterGecmisOdemelerListesi  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static final Uri CONTENT_URI_GECMIS_ODEMELER= OdemelerProvider.CONTENT_URI_GECMIS_ODEMELER;
    LayoutInflater mInflater;
    ArrayList<GecmisOdemeler> tumGecmisOdemeler;

    Context mContext;

    private ContentResolver resolver;

    public AdapterGecmisOdemelerListesi(Context context, ArrayList<GecmisOdemeler> tumGecmisOdemeler) {
        this.mInflater = LayoutInflater.from(context);
        this.tumGecmisOdemeler = tumGecmisOdemeler;
        this.resolver = context.getContentResolver();
        //context alıp kolayca hallettik.
        mContext=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.custom_recycler_liste_tum_gecmis_odemeler, viewGroup, false);
        final GecmisOdemeHolder holder = new GecmisOdemeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof GecmisOdemeHolder)
        {
            GecmisOdemeHolder gecmisOdemeHolder= (GecmisOdemeHolder) viewHolder;


            gecmisOdemeHolder.mTextGecmisOdemeBaslik.setText(tumGecmisOdemeler.get(i).getGecmisOdemeBaslik());
            gecmisOdemeHolder.mTextGecmisOdemeTarih.setText(String.valueOf(tumGecmisOdemeler.get(i).getGecmisOdemeOdemeTarihi()));
            gecmisOdemeHolder.mTextGecmisOdemeAciklama.setText(String.valueOf(tumGecmisOdemeler.get(i).getGecmisOdemeOdenenTaksitSayisi()+
            ". Taksit "+tumGecmisOdemeler.get(i).getGecmisOdemeAylikFiyat()+" "+tumGecmisOdemeler.get(i).getGecmisOdemeParaBirimi()+" olarak ödenmiştir."));





            Random random = new Random();

            int color = Color.argb(200, random.nextInt(156), random.nextInt(156), random.nextInt(156));
            gecmisOdemeHolder.gecmisArkaplan.setBackgroundColor(color);


        }
    }

    @Override
    public int getItemCount() {
        if(tumGecmisOdemeler==null ||  tumGecmisOdemeler.isEmpty()) {
            return 0;
        }
        else
        {
            return tumGecmisOdemeler.size();
        }
    }






    public void update(ArrayList<GecmisOdemeler> tumOdemeler)
    {
        this.tumGecmisOdemeler=tumOdemeler;
        notifyDataSetChanged();
    }



    public static class GecmisOdemeHolder extends RecyclerView.ViewHolder
    {

        TextView mTextGecmisOdemeBaslik;
        TextView mTextGecmisOdemeTarih;
        TextView mTextGecmisOdemeAciklama;

        LinearLayout gecmisArkaplan;


        public GecmisOdemeHolder(@NonNull View itemView) {
            super(itemView);

            mTextGecmisOdemeBaslik=itemView.findViewById(R.id.custom_liste3_gecmis_odeme_baslik);
            mTextGecmisOdemeTarih=itemView.findViewById(R.id.custom_liste3_gecmis_odeme_tarih);
            mTextGecmisOdemeAciklama=itemView.findViewById(R.id.custom_liste3_gecmis_odeme_aciklama);

            gecmisArkaplan=itemView.findViewById(R.id.custom_liste3_gecmis_arkaplan);

        }
    }
}
