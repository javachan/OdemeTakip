package com.example.lenovo.odemetakip.adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.odemetakip.ActivityDetayliBilgiler;
import com.example.lenovo.odemetakip.ActivityDetayliBilgiler2;
import com.example.lenovo.odemetakip.R;
import com.example.lenovo.odemetakip.data.Odemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class AdapterOdemelerListesi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final Uri CONTENT_URI= OdemelerProvider.CONTENT_URI; //ana linki aldık.

    LayoutInflater mInflater;
    ArrayList<Odemeler> tumOdemeler;

    private ContentResolver resolver;

    private Context mContext;





    public AdapterOdemelerListesi(Context context,ArrayList<Odemeler> tumOdemeler) {
        this.mInflater = LayoutInflater.from(context);
        this.tumOdemeler = tumOdemeler;
        this.resolver = context.getContentResolver();
        //context alıp kolayca hallettik.

        this.mContext=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.custom_recycler_liste, viewGroup, false);
        final OdemeHolder holder = new OdemeHolder(view);


        holder.mTextCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aa", tumOdemeler.get(holder.getAdapterPosition()).getOdemeBaslik());
                //burdan put extra ile verileri tüm bilgiler adlı activity e yollanacak.

                Activity activity = (Activity) mContext;


                Intent detayliSonuc=new Intent(mContext, ActivityDetayliBilgiler2.class);

                detayliSonuc.putExtra("detayliBaslik", tumOdemeler.get(holder.getAdapterPosition()).getOdemeBaslik());
                detayliSonuc.putExtra("detayliMiktar", String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeAylikFiyat()));
                detayliSonuc.putExtra("detayliKalan", String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeKalanTaksitSayisi()));
                detayliSonuc.putExtra("detayliOdenen", String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeOdenenTaksitSayisi()));
                detayliSonuc.putExtra("OdemeId",String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeId()));
                detayliSonuc.putExtra("OdemeAylikHatirlat",String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeAylikHatirlat()));
                detayliSonuc.putExtra("OdemeHatirlatmaAyGunu",String.valueOf(tumOdemeler.get(holder.getAdapterPosition()).getOdemeHatirlatmaAyGunu()));



                activity.startActivity(detayliSonuc);



                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {


        //holder ile itemlerı aldık. içerdeki textview buton vs. erişebiliriz burdan. burası super metodu.

        //i ile listemizin uzunluğu kadar döneriz. getItemcount metodu sayesinde.

      /*  if(holder instanceof OdemeHolder)
        {
            OdemeHolder odemetHolder= (OdemeHolder) holder;
            odemetHolder.mTextOdemeBaslik.setText(tumOdemeler.get(i).getOdemeBaslik());
            odemetHolder.mTextOdemeMiktar.setText(String.valueOf(tumOdemeler.get(i).getOdemeAylikFiyat()));
            odemetHolder.mTextKalanAySayisi.setText(String.valueOf(tumOdemeler.get(i).getOdemeKalanTaksitSayisi()));
            odemetHolder.mTextOdenenAySayisi.setText(String.valueOf(tumOdemeler.get(i).getOdemeOdenenTaksitSayisi()));
        }
        */

        if(holder instanceof OdemeHolder)
        {
            OdemeHolder odemetHolder= (OdemeHolder) holder;
            odemetHolder.mTextOdemeBaslik.setText(tumOdemeler.get(i).getOdemeBaslik());

        }



    }

    @Override
    public int getItemCount() {
        if(tumOdemeler==null ||  tumOdemeler.isEmpty()) {
            return 0;
        }
        else
        {
            return tumOdemeler.size();
        }
    }






    public void update(ArrayList<Odemeler> tumOdemeler)
    {
        this.tumOdemeler=tumOdemeler;
        notifyDataSetChanged();
    }


    public static class OdemeHolder extends RecyclerView.ViewHolder
    {
/*
        TextView mTextOdemeBaslik;
        TextView mTextOdemeMiktar;
        TextView mTextOdenenAySayisi;
        TextView mTextKalanAySayisi;


        public OdemeHolder(@NonNull View itemView) {
            super(itemView);

            mTextOdemeBaslik=itemView.findViewById(R.id.custom_liste_odemeBaslik);
            mTextOdemeMiktar=itemView.findViewById(R.id.custom_liste_aylik_odeme_miktar);
            mTextOdenenAySayisi=itemView.findViewById(R.id.custom_liste_odenen_ay_sayisi);
            mTextKalanAySayisi=itemView.findViewById(R.id.custom_liste_kalan_ay_sayisi);

        }
        */

        TextView mTextOdemeBaslik;
        ImageButton mTextCircleButton;


        public OdemeHolder(@NonNull View itemView) {
            super(itemView);
            mTextOdemeBaslik=itemView.findViewById(R.id.tx_tumOdemelerBaslik);
            mTextCircleButton=itemView.findViewById(R.id.btn_tumOdemelerCircleButon);

        }
    }

}
