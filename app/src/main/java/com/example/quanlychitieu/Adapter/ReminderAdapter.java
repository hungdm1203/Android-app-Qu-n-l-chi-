package com.example.quanlychitieu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.quanlychitieu.Models.Reminder;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class ReminderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Reminder> arrayList;

    public ReminderAdapter(Context context, int layout, ArrayList<Reminder> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        Switch sc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(viewHolder==null){
            viewHolder=new ReminderAdapter.ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHolder.sc=convertView.findViewById(R.id.switch1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ReminderAdapter.ViewHolder) convertView.getTag();
        }

        Reminder r=arrayList.get(position);
        viewHolder.sc.setText(r.getTime()+"\n"+r.getNote());
        if(r.getStatus()==1){
            viewHolder.sc.setChecked(true);
        } else {
            viewHolder.sc.setChecked(false);
        }




        return convertView;
    }

}
