package com.bawei.monthex1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bawei.monthex1.base.BaseActivity;
import com.bawei.monthex1.fragment.HomeFragment;
import com.bawei.monthex1.fragment.OtherFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //胡锦涛初始化第一周周考代码
    private ViewPager vp;
    private TabLayout tb;
    private ImageView img;
    private List<Fragment> fglist=new ArrayList<>();
    private List<String> stlist=new ArrayList<>();
    @Override
    protected void initData() {
        stlist.clear();
        fglist.clear();
        HomeFragment homeFragment = new HomeFragment();
        OtherFragment ot1 = OtherFragment.getInstance("推荐");
        OtherFragment ot2 = OtherFragment.getInstance("我的");
        fglist.add(homeFragment);
        fglist.add(ot1);
        fglist.add(ot2);
        stlist.add("首页");
        stlist.add("推荐");
        stlist.add("我的");
        //适配器
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fglist.get(position);
            }

            @Override
            public int getCount() {
                return fglist.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return stlist.get(position);
            }
        });
        tb.setupWithViewPager(vp);
    }

    @Override
    protected void initView() {
        img =findViewById(R.id.img);
        vp = findViewById(R.id.vp);
        tb = findViewById(R.id.tb);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            Uri data1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data1);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
