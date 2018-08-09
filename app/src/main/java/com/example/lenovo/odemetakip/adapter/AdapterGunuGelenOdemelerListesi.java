package com.example.lenovo.odemetakip.adapter;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.odemetakip.ActivityGunuGelenler;
import com.example.lenovo.odemetakip.FragmentDialogOdeme;
import com.example.lenovo.odemetakip.R;
import com.example.lenovo.odemetakip.data.GunuGelenOdemeler;
import com.example.lenovo.odemetakip.data.OdemelerProvider;

import java.util.ArrayList;

public class AdapterGunuGelenOdemelerListesi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final Uri CONTENT_URI_GUNU_GELEN_ODEMELER= OdemelerProvider.CONTENT_URI_GUNU_GELEN_ODEMELER;
    LayoutInflater mInflater;
    ArrayList<GunuGelenOdemeler> tumGunuGelenOdemeler;

    Context mContext;

    private ContentResolver resolver;

    public AdapterGunuGelenOdemelerListesi(Context context, ArrayList<GunuGelenOdemeler> tumGunuGelenOdemeler) {
        this.mInflater = LayoutInflater.from(context);
        this.tumGunuGelenOdemeler = tumGunuGelenOdemeler;
        this.resolver = context.getContentResolver();
        //context alıp kolayca hallettik.
        mContext=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.custom_recycler_liste_gunu_gelenler, viewGroup, false);
        final GunuGelenOdemeHolder holder = new GunuGelenOdemeHolder(view);

        holder.tiklanacakListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                if(tumGunuGelenOdemeler.get(holder.getAdapterPosition()).getGunOdemeOdendimi().equals("Ödenmedi")) {
                    FragmentDialogOdeme fragmentDialogOdeme = new FragmentDialogOdeme(Integer.valueOf(tumGunuGelenOdemeler.get(holder.getAdapterPosition()).getGunOdemeId()));


                    //Activity değil de class/adapter dan çağırdığımız için transaction işlemi yaptık.
                    FragmentTransaction ft = ((ActivityGunuGelenler) mContext).getSupportFragmentManager()
                            .beginTransaction();

                    fragmentDialogOdeme.show(ft, "asd");
                }
                else
                {
                    Toast.makeText(mContext, "Bu ödeme zaten yapılmış", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return holder;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        //holder ile itemlerı aldık. içerdeki textview buton vs. erişebiliriz burdan. burası super metodu.

        //i ile listemizin uzunluğu kadar döneriz. getItemcount metodu sayesinde.

        if(holder instanceof GunuGelenOdemeHolder)
        {
            GunuGelenOdemeHolder gunuGelenOdemeHolder= (GunuGelenOdemeHolder) holder;


            gunuGelenOdemeHolder.mTextGunuGelenOdemeBaslik.setText(tumGunuGelenOdemeler.get(i).getGunOdemeBaslik());
            gunuGelenOdemeHolder.mTextGunuGelenOdemeMiktar.setText(String.valueOf(tumGunuGelenOdemeler.get(i).getGunOdemeAylikFiyat()));
            gunuGelenOdemeHolder.mTextGunuGelenKalanAySayisi.setText(String.valueOf(tumGunuGelenOdemeler.get(i).getGunOdemeKalanTaksitSayisi()));
            gunuGelenOdemeHolder.mTextGunuGelenOdenenAySayisi.setText(String.valueOf(tumGunuGelenOdemeler.get(i).getGunOdemeOdenenTaksitSayisi()));
            gunuGelenOdemeHolder.mTextGunuGelenOdendiMiBilgisi.setText(String.valueOf(tumGunuGelenOdemeler.get(i).getGunOdemeOdendimi()));

            gunuGelenOdemeHolder.geciciIDBilgisi.setText(String.valueOf(tumGunuGelenOdemeler.get(i).getGunOdemeId()));
            //yalnızca fragmente yollayalım da ilgili veriyi ödendi-ödenmedi diye güncelleyebilsin diye bu bilgiyi aldık.

            if(gunuGelenOdemeHolder.mTextGunuGelenOdendiMiBilgisi.getText().toString().equals("Ödendi"))
            {
                gunuGelenOdemeHolder.mTextGunuGelenOdendiMiBilgisi.setTextColor(Color.GREEN);
            }
            else
                gunuGelenOdemeHolder.mTextGunuGelenOdendiMiBilgisi.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        if(tumGunuGelenOdemeler==null ||  tumGunuGelenOdemeler.isEmpty()) {
            return 0;
        }
        else
        {
            return tumGunuGelenOdemeler.size();
        }
    }



    public void update(ArrayList<GunuGelenOdemeler> tumOdemeler)
    {
        this.tumGunuGelenOdemeler=tumOdemeler;
        notifyDataSetChanged();
    }

    public static class GunuGelenOdemeHolder extends RecyclerView.ViewHolder
    {

        TextView mTextGunuGelenOdemeBaslik;
        TextView mTextGunuGelenOdemeMiktar;
        TextView mTextGunuGelenOdenenAySayisi;
        TextView mTextGunuGelenKalanAySayisi;
        TextView mTextGunuGelenOdendiMiBilgisi;
        LinearLayout tiklanacakListe;

        TextView geciciIDBilgisi;


        public GunuGelenOdemeHolder(@NonNull View itemView) {
            super(itemView);

            mTextGunuGelenOdemeBaslik=itemView.findViewById(R.id.custom_liste2_odemeBaslik);
            mTextGunuGelenOdemeMiktar=itemView.findViewById(R.id.custom_liste2_aylik_odeme_miktar);
            mTextGunuGelenOdenenAySayisi=itemView.findViewById(R.id.custom_liste2_odenen_ay_sayisi);
            mTextGunuGelenKalanAySayisi=itemView.findViewById(R.id.custom_liste2_kalan_ay_sayisi);
            mTextGunuGelenOdendiMiBilgisi=itemView.findViewById(R.id.custom_liste2_odenme_durumu);

            geciciIDBilgisi=itemView.findViewById(R.id.geciciIDbilgisiInVisible);

            tiklanacakListe=itemView.findViewById(R.id.tiklanacakListem);
        }
    }
}
