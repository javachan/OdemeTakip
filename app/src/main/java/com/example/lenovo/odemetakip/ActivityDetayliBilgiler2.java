package com.example.lenovo.odemetakip;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.odemetakip.data.Odemeler;

public class ActivityDetayliBilgiler2 extends AppCompatActivity {
    private Toolbar mTolbar;

    private ViewPager mViewPager;
    private TabLayout mTablayout;

    Odemeler putExtasOdemeler=new Odemeler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detayli_bilgiler2);


        //tum ödemeler activ. den gelen(aslında adapter dan) ödemeye ait bilgileri extralarla alalım.

        putExtasOdemeler.setOdemeBaslik(getIntent().getExtras().getString("detayliBaslik"));
        putExtasOdemeler.setOdemeId(Integer.valueOf(getIntent().getExtras().getString("OdemeId")));
        putExtasOdemeler.setOdemeOdenenTaksitSayisi(Integer.valueOf(getIntent().getExtras().getString("detayliOdenen")));
        putExtasOdemeler.setOdemeKalanTaksitSayisi(Integer.valueOf(getIntent().getExtras().getString("detayliKalan")));
        putExtasOdemeler.setOdemeAylikFiyat(Integer.valueOf(getIntent().getExtras().getString("detayliMiktar")));
        putExtasOdemeler.setOdemeAylikHatirlat(Integer.valueOf(getIntent().getExtras().getString("OdemeAylikHatirlat")));
        putExtasOdemeler.setOdemeHatirlatmaAyGunu(Integer.valueOf(getIntent().getExtras().getString("OdemeHatirlatmaAyGunu")));


        Log.e("put",getIntent().getExtras().getString("detayliBaslik"));
        Log.e("put",getIntent().getExtras().getString("OdemeId"));
        Log.e("put",getIntent().getExtras().getString("detayliOdenen"));
        Log.e("put",getIntent().getExtras().getString("detayliKalan"));
        Log.e("put",getIntent().getExtras().getString("detayliMiktar"));
        Log.e("put",getIntent().getExtras().getString("OdemeAylikHatirlat"));
        Log.e("put",getIntent().getExtras().getString("OdemeHatirlatmaAyGunu"));

        //







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTablayout=findViewById(R.id.tabs);
        mTablayout.addTab(mTablayout.newTab().setText("Genel Bilgiler"));
        mTablayout.addTab(mTablayout.newTab().setText("Geçmiş Ödemeler"));
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(putExtasOdemeler.getOdemeBaslik());





        mViewPager = (ViewPager) findViewById(R.id.container);
        Pager adapter=new Pager(getSupportFragmentManager(),mTablayout.getTabCount());
        mViewPager.setAdapter(adapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                mTablayout.setScrollPosition(i,0,true);
                mTablayout.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });




        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }



    class Pager extends FragmentStatePagerAdapter {


        int tabCount;

        public Pager(FragmentManager fm, int tabCount)
        {
            super(fm);
            this.tabCount=tabCount;
        }





        @Override
        public Fragment getItem(int i) {

            switch (i)
            {
                case 0:
                    FragmentTab1 fragmentTab1=new FragmentTab1(putExtasOdemeler);
                    return fragmentTab1;
                case 1:
                    FragmentTab2 fragmentTab2=new FragmentTab2((putExtasOdemeler.getOdemeId()));
                    return fragmentTab2;
                default:return null;
            }


        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.fragment_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1_bilgileri_guncelle:
            {
                Toast.makeText(this, "Güncelle sayfası yakında!", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.item2_anaSayfa:
            {
                Intent anaSayfaIntent=new Intent(ActivityDetayliBilgiler2.this,ActivityMain.class);
                startActivity(anaSayfaIntent);
                finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }

    }
}
