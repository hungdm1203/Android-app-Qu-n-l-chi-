package com.example.quanlychitieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MoneyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Money> arrayList;


    public MoneyAdapter(Context context, int layout, ArrayList<Money> arrayList) {
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

    private class ViewHolder {
        TextView date, info;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHolder.imageView=convertView.findViewById(R.id.showIMG);
            viewHolder.date=convertView.findViewById(R.id.textViewDate);
            viewHolder.info=convertView.findViewById(R.id.textViewInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Money money=arrayList.get(position);
        viewHolder.date.setText(money.getDate2());
        if(money.getTypePrice().equals("Chi phí")){
            if(money.getType().equals("Mua sắm")){
                viewHolder.imageView.setImageResource(R.drawable.baseline_shopping_cart);
            } else if (money.getType().equals("Sức khỏe")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_local_hospital);
            } else if (money.getType().equals("Thực phẩm")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_food_bank);
            } else if (money.getType().equals("Hóa đơn")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_wysiwyg);
            } else if (money.getType().equals("Học tập")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_menu_book);
            } else if (money.getType().equals("Đồ điện tử")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_smartphone);
            } else if (money.getType().equals("Đi lại")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_directions_subway);
            } else if (money.getType().equals("Giải trí")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_add_reaction);
            } else if (money.getType().equals("Quà tặng")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_card_giftcard);
            } else if (money.getType().equals("Du lịch")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_airplanemode_active);
            } else if (money.getType().equals("Thể thao")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_sports_basketball);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.baseline_difference);
            }
            String price = String.format("%,d", money.getPrice());
            viewHolder.info.setText(money.getTypePrice()+":     - "+price+"\n"+money.getType()+" ("+money.getNote()+")");

        }else {
            if(money.getType().equals("Lương")){
                viewHolder.imageView.setImageResource(R.drawable.baseline_credit_card);
            } else if (money.getType().equals("Tiền tiết kiệm")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_savings);
            } else if (money.getType().equals("Bán thời gian")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_access_time);
            } else if (money.getType().equals("Khoản đầu tư")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_moving);
            } else if (money.getType().equals("Giải thưởng")) {
                viewHolder.imageView.setImageResource(R.drawable.baseline_interests);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.baseline_difference);
            }

            String price = String.format("%,d", money.getPrice());
            viewHolder.info.setText(money.getTypePrice()+":     +"+price+"\n"+money.getType()+" ("+money.getNote()+")");

        }

        return convertView;
    }
}
