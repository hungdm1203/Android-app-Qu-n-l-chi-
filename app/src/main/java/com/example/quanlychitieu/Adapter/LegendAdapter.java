package com.example.quanlychitieu.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlychitieu.R;
import com.github.mikephil.charting.components.LegendEntry;

import java.util.ArrayList;

public class LegendAdapter extends ArrayAdapter<LegendEntry> {
    private Context context;
    private ArrayList<LegendEntry> legendEntries;

    public LegendAdapter(Context context, ArrayList<LegendEntry> legendEntries) {
        super(context, 0, legendEntries);
        this.context = context;
        this.legendEntries = legendEntries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.line_chart_note, parent, false);
        }

        LegendEntry currentEntry = legendEntries.get(position);


        TextView tv = listItemView.findViewById(R.id.tv);
        ImageView img= listItemView.findViewById(R.id.img);
        tv.setText(currentEntry.label);
        GradientDrawable bgDrawable = new GradientDrawable();
        bgDrawable.setShape(GradientDrawable.RECTANGLE);
        bgDrawable.setColor(currentEntry.formColor);
        img.setBackground(bgDrawable);



        return listItemView;
    }
}
