package com.example.quanlychitieu.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.quanlychitieu.Adapter.LegendAdapter;
import com.example.quanlychitieu.MainActivity;
import com.example.quanlychitieu.Models.Money;
import com.example.quanlychitieu.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvMonth,tvSodu;
    private PieChart chartCP,chartTN;
    private ImageView imgMonth;
    private ListView lvCP,lvTN;
    private ArrayList<Money> moneyArrayList;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chart, container, false);


        tvMonth = view.findViewById(R.id.tvMonth);
        tvSodu = view.findViewById(R.id.tvSodu);
        chartCP = view.findViewById(R.id.chartCP);
        chartTN = view.findViewById(R.id.chartTN);
        imgMonth = view.findViewById(R.id.imgMonth);
        lvCP = view.findViewById(R.id.lvCP);
        lvTN = view.findViewById(R.id.lvTN);

        Calendar calendar = Calendar.getInstance();
        getData(calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR));

        imgMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectMonth();
            }
        });

        return view;
    }


    //hiện dialog chọn tháng
    private void showSelectMonth() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.month_picker);
        NumberPicker monthPicker = dialog.findViewById(R.id.monthPicker);
        NumberPicker yearPicker = dialog.findViewById(R.id.yearPicker);
        Button okButton = dialog.findViewById(R.id.okButton);


        Calendar calendar = Calendar.getInstance();
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(calendar.get(Calendar.YEAR));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedMonth = monthPicker.getValue();
                int selectedYear = yearPicker.getValue();
                tvMonth.setText("Tháng " + selectedMonth+" năm "+selectedYear);
                getData(selectedMonth,selectedYear);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void getData(int month, int year){
        String yearStr = String.valueOf(year),
                monthStr = String.valueOf(month);
        if(monthStr.length()<2) monthStr = "0" + monthStr;

        String res= MainActivity.account.getTk();
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData("SELECT * FROM money WHERE tk='"+res+"' AND SUBSTR(date, 4, 2)='"+monthStr+"' AND SUBSTR(date,7,4)='"+yearStr+"'");
        moneyArrayList = new ArrayList<>();
        while (getDataMoney.moveToNext()) {
            int id = getDataMoney.getInt(0);
            String tk = getDataMoney.getString(1);
            String typePrice = getDataMoney.getString(2);
            String type = getDataMoney.getString(3);
            String date = getDataMoney.getString(4);
            int price = getDataMoney.getInt(5);
            String note = getDataMoney.getString(6);
            moneyArrayList.add(new Money(id, tk, typePrice, type, date, price, note));
        }
        Collections.sort(moneyArrayList);

        showInfo();
    }

    private void showInfo() {
        int cp=0,tn=0;
        LinkedHashMap<String,Integer> mapCP = new LinkedHashMap<>(),
                mapTN = new LinkedHashMap<>();

        for (Money money : moneyArrayList) {
            if(money.getTypePrice().equals("Chi phí")){
                if (mapCP.containsKey(money.getType())){
                    mapCP.put(money.getType(), mapCP.get(money.getType()) + money.getPrice());
                } else {
                    mapCP.put(money.getType(), money.getPrice());
                }
                cp+= money.getPrice();
            } else {
                if (mapTN.containsKey(money.getType())){
                    mapTN.put(money.getType(), mapTN.get(money.getType()) + money.getPrice());
                } else {
                    mapTN.put(money.getType(), money.getPrice());
                }
                tn+= money.getPrice();
            }
        }

        //ve bieu do chi phi
        ArrayList<PieEntry> pieEntriesCP = new ArrayList<>();
        for (LinkedHashMap.Entry<String, Integer> entry : mapCP.entrySet()) {
            pieEntriesCP.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet dataSetCP = new PieDataSet(pieEntriesCP, "");
        dataSetCP.setValueTextColor(Color.TRANSPARENT);
        dataSetCP.setColors(new int[]{Color.rgb(0, 0, 255),Color.rgb(0, 255, 0), Color.rgb(255, 0, 0),
                Color.rgb(255, 255, 0), Color.rgb(0, 255, 255),Color.rgb(255,0,255),
                Color.rgb(128, 0, 128), Color.rgb(255, 165, 0),Color.rgb(245,245,220),
                Color.rgb(139, 69, 19), Color.rgb(128, 128, 128),Color.rgb(3,218,197)});
        PieData pieDataCP = new PieData(dataSetCP);
        chartCP.setData(pieDataCP);
        chartCP.getDescription().setEnabled(false);
        chartCP.getLegend().setEnabled(false);
        chartCP.setDrawEntryLabels(false);
        chartCP.setDrawMarkers(false);
        chartCP.animateY(1000);
        chartCP.invalidate();


        //ve bieu do thu nhap
        ArrayList<PieEntry> pieEntriesTN = new ArrayList<>();
        for (LinkedHashMap.Entry<String, Integer> entry : mapTN.entrySet()) {
            pieEntriesTN.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet dataSetTN = new PieDataSet(pieEntriesTN, "");
        dataSetTN.setValueTextColor(Color.TRANSPARENT);
        dataSetTN.setColors(new int[]{Color.rgb(0, 0, 255),Color.rgb(0, 255, 0), Color.rgb(255, 0, 0),
                Color.rgb(255, 255, 0), Color.rgb(0, 255, 255), Color.rgb(255, 0, 255)});
        PieData pieDataTN = new PieData(dataSetTN);
        chartTN.setData(pieDataTN);
        chartTN.getDescription().setEnabled(false);
        chartTN.getLegend().setEnabled(false);
        chartTN.setDrawEntryLabels(false);
        chartTN.setDrawMarkers(false);
        chartTN.animateY(1000);
        chartTN.invalidate();

        //cap nhat tong chi phi, tong thu nhap, so du
        String strCP = String.format("%,d", cp),
                strTN = String.format("%,d", tn),
                strSD = String.format("%,d", tn - cp);
        chartCP.setCenterText(String.valueOf(strCP));
        chartCP.setCenterTextSize(18);
        chartTN.setCenterText(String.valueOf(strTN));
        chartTN.setCenterTextSize(18);
        tvSodu.setText(String.valueOf(strSD));



        //tao ghi chu cho bieu do
        ArrayList<LegendEntry> legendEntriesCP = new ArrayList<>();
        List<Integer> colors = dataSetCP.getColors();
        for (int i = 0; i < pieEntriesCP.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors.get(i);
            float n=(pieEntriesCP.get(i).getValue()/cp)*100;
            String res = String.format("%.2f", n);
            int m= (int) pieEntriesCP.get(i).getValue();
            String str = String.format("%,d", m);
            entry.label = pieEntriesCP.get(i).getLabel()+": "+res+"%\n" + str;
            legendEntriesCP.add(entry);
        }
        LegendAdapter adapter = new LegendAdapter(getContext(), legendEntriesCP);
        lvCP.setAdapter(adapter);

        ArrayList<LegendEntry> legendEntriesTN = new ArrayList<>();
        List<Integer> clr = dataSetCP.getColors();
        for (int i = 0; i < pieEntriesTN.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = clr.get(i);
            float n=(pieEntriesTN.get(i).getValue()/tn)*100;
            String res = String.format("%.2f", n);
            int m= (int) pieEntriesTN.get(i).getValue();
            String str = String.format("%,d", m);
            entry.label = pieEntriesTN.get(i).getLabel()+": "+res+"%\n" + str;
            legendEntriesTN.add(entry);
        }

        LegendAdapter adt = new LegendAdapter(getContext(), legendEntriesTN);
        lvTN.setAdapter(adt);
    }
}