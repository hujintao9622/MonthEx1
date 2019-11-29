package com.bawei.monthex1;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 功能:  适配器
 * 作者:  胡锦涛
 * 时间:  2019/11/21 0021 下午 12:01
 */
public class MyAdapter extends BaseAdapter {
    private List<LawyerBean.ListdataBean> list;

    public MyAdapter(List<LawyerBean.ListdataBean> list) {

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        if (type==1){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        int itemViewType = getItemViewType(position);
        if (convertView==null){
            holder=new ViewHolder();
            if (itemViewType==0){
                convertView=View.inflate(parent.getContext(),R.layout.itemv,null);
            }else {
                convertView=View.inflate(parent.getContext(),R.layout.itemh,null);
            }
            holder.img=convertView.findViewById(R.id.it_img);
            holder.name=convertView.findViewById(R.id.it_name);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        LawyerBean.ListdataBean listdataBean = list.get(position);
        holder.name.setText(listdataBean.getName());
        String avatar = listdataBean.getAvatar();
        NetUtils.getInstance().getBitmap(avatar,holder.img);
        return convertView;
    }
    class ViewHolder{
        ImageView img;
        TextView name;
    }
}
