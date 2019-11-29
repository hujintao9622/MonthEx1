package com.bawei.monthex1.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.bawei.monthex1.LawyerBean;
import com.bawei.monthex1.MyAdapter;
import com.bawei.monthex1.NetUtils;
import com.bawei.monthex1.R;
import com.bawei.monthex1.SecondActivity;
import com.bawei.monthex1.base.BaseFragment;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qy.xlistview.XListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能:  页面
 * 作者:  胡锦涛
 * 时间:  2019/11/20 0020 下午 6:48
 */
public class HomeFragment extends BaseFragment {

    private PullToRefreshListView xlist;
    private int page=1;
    private List<LawyerBean.ListdataBean> list=new ArrayList<>();
    private LinearLayout ll;

    @Override
    protected void initView(View view) {
        ll = view.findViewById(R.id.ll);
        xlist = view.findViewById(R.id.xlist);
   /*     xlist.setPullLoadEnable(true);
        xlist.setPullRefreshEnable(true);
        xlist.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page=1;
                // TODO: 2019/11/21 0021 清空数据
                list.clear();
                getData();
                xlist.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                page++;
                getData();
                xlist.stopLoadMore();
            }
        });*/
   xlist.setMode(PullToRefreshBase.Mode.BOTH);
   xlist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
       @Override
       public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
           page=1;
           // TODO: 2019/11/21 0021 清空数据
           list.clear();
           getData();
       }

       @Override
       public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
           page++;
           getData();
       }
   });
        xlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = list.get(position - 1).getUrl();
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra("key",url);
                startActivity(intent);

            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.home;
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        if (NetUtils.getInstance().hasNet(getContext())){
            ll.setVisibility(View.GONE);
            xlist.setVisibility(View.VISIBLE);
            String st="";
            if (page==1){
                st="http://blog.zhaoliang5156.cn/api/news/lawyer.json";
            }else if (page==2){
                st="http://blog.zhaoliang5156.cn/api/news/lawyer2.json";
            }else {
                st="http://blog.zhaoliang5156.cn/api/news/lawyer3.json";
            }
            NetUtils.getInstance().getJson(st, new NetUtils.MyCallBack() {
                @Override
                public void onGetJson(String json) {
                    //解析
                    LawyerBean lawyerBean = new Gson().fromJson(json, LawyerBean.class);
                    List<LawyerBean.ListdataBean> listdata = lawyerBean.getListdata();
                    list.addAll(listdata);
                    xlist.setAdapter(new MyAdapter(list));
                    xlist.onRefreshComplete();

                }
            });
        }else {
            ll.setVisibility(View.VISIBLE);
            xlist.setVisibility(View.GONE);
        }
    }


}
