package com.bawei.monthex1.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bawei.monthex1.R;
import com.bawei.monthex1.base.BaseFragment;

/**
 * 功能:  页面
 * 作者:  胡锦涛
 * 时间:  2019/11/20 0020 下午 6:49
 */
public class OtherFragment extends BaseFragment {

    private TextView name;

    @Override
    protected void initView(View view) {
        name = view.findViewById(R.id.name);
    }

    @Override
    protected int layoutId() {
        return R.layout.other;
    }

    @Override
    protected void initData() {
        String key = getArguments().getString("key");
        name.setText(key);
    }

    public static OtherFragment getInstance(String value) {
        OtherFragment other=new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key",value);
        other.setArguments(bundle);
        return other;
    }
}
