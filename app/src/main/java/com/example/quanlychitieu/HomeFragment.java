package com.example.quanlychitieu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lv;

    TextView tvChiPhi,tvThuNhap,tvSoDu,tvYear,tvMonth;
    public static ArrayList<Money> arrayListMoney;
    MoneyAdapter moneyAdapter;
    ImageView imageView;


    public interface OnDataLoadedListener {
        void onDataLoaded();
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        lv=view.findViewById(R.id.listViewShow);
        tvChiPhi=view.findViewById(R.id.tvChiPhi);
        tvThuNhap = view.findViewById(R.id.tvThuNhap);
        tvSoDu = view.findViewById(R.id.tvSoDu);
        tvYear = view.findViewById(R.id.tvYear);
        tvMonth = view.findViewById(R.id.tvMonth);
        imageView = view.findViewById(R.id.imageView);

        Calendar calendar = Calendar.getInstance();
        tvYear.setText(calendar.get(Calendar.YEAR)+"");
        tvMonth.setText("Thg "+(calendar.get(Calendar.MONTH)+1));
        getDataFirst();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectMonth();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfoMoney(position);
            }
        });

        return view;
    }


    //hiển thị thông tin cho chi tiêu
    private void showInfoMoney(int pos) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);

        ImageView img=dialog.findViewById(R.id.showIMG),
                cancelBtn=dialog.findViewById(R.id.cancelButton);
        TextView type=dialog.findViewById(R.id.type),
                typePrice=dialog.findViewById(R.id.typePrice),
                date=dialog.findViewById(R.id.date);
        EditText edtPrice=dialog.findViewById(R.id.price),
                edtNote=dialog.findViewById(R.id.note);
        Button btnSua=dialog.findViewById(R.id.btnFix),
                btnXoa=dialog.findViewById(R.id.btnDelete);

        type.setText(arrayListMoney.get(pos).getType());
        typePrice.setText(arrayListMoney.get(pos).getTypePrice());
        edtPrice.setText(String.valueOf(arrayListMoney.get(pos).getPrice()));
        date.setText(arrayListMoney.get(pos).getDate());
        edtNote.setText(arrayListMoney.get(pos).getNote());
        for(Item i:MainActivity.arrChiPhi){
            if(arrayListMoney.get(pos).getType().equals(i.getType())){
                img.setImageResource(i.getImg());
            }
        }
        for(Item i:MainActivity.arrThuNhap){
            if(arrayListMoney.get(pos).getType().equals(i.getType())){
                img.setImageResource(i.getImg());
            }
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                                date.setText(sdf.format(calendar.getTime()));

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=arrayListMoney.get(pos).getId();
                int price=Integer.valueOf(edtPrice.getText().toString());
                String strDate=date.getText().toString();
                String strNote=edtNote.getText().toString();
                MainActivity.databaseSQLite.QueryData("UPDATE money SET price='"+price+"', date='"+strDate+"', note='"+strNote+"' WHERE id='"+id+"'");
                dialog.dismiss();
                arrayListMoney.get(pos).setPrice(price);
                arrayListMoney.get(pos).setDate(strDate);
                arrayListMoney.get(pos).setNote(strNote);
                moneyAdapter.notifyDataSetChanged();
                update();
                Toast.makeText(getActivity(), "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=arrayListMoney.get(pos).getId();
                MainActivity.databaseSQLite.QueryData("DELETE FROM money WHERE id='"+id+"'");
                arrayListMoney.remove(pos);
                moneyAdapter.notifyDataSetChanged();
                update();
                dialog.dismiss();
                Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void getData() {
//        arrayListMoney=MainActivity.moneyArrayList;
        moneyAdapter = new MoneyAdapter(getActivity(), R.layout.line_money, arrayListMoney);
        lv.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
        update();
    }

    public void update(){
        int cp=0,tn=0;
        for (Money m:arrayListMoney) {
            if(m.getTypePrice().equals("Chi phí")) cp+= m.getPrice();
            else tn+= m.getPrice();
        }

        String strCP = String.format("%,d", cp),
                strTN = String.format("%,d", tn),
                strSD = String.format("%,d", tn - cp);
        tvChiPhi.setText(String.valueOf(strCP));
        tvThuNhap.setText(String.valueOf(strTN));
        tvSoDu.setText(String.valueOf(strSD));
    }


    //update sau khi chọn tháng
    public void selectMonth(int month, int year){
        String yearStr = String.valueOf(year),
                monthStr = String.valueOf(month);
        if(monthStr.length()<2) monthStr = "0" + monthStr;

        String res=MainActivity.account.getTk();
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData("SELECT * FROM money WHERE tk='"+res+"' AND SUBSTR(date, 4, 2)='"+monthStr+"' AND SUBSTR(date,7,4)='"+yearStr+"'");
        arrayListMoney = new ArrayList<>();
        while (getDataMoney.moveToNext()) {
            int id = getDataMoney.getInt(0);
            String tk = getDataMoney.getString(1);
            String typePrice = getDataMoney.getString(2);
            String type = getDataMoney.getString(3);
            String date = getDataMoney.getString(4);
            int price = getDataMoney.getInt(5);
            String note = getDataMoney.getString(6);
            arrayListMoney.add(new Money(id, tk, typePrice, type, date, price, note));
        }
        Collections.sort(arrayListMoney);
        moneyAdapter = new MoneyAdapter(getActivity(), R.layout.line_money, arrayListMoney);
        lv.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
        update();
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
                tvMonth.setText("Thg " + selectedMonth);
                tvYear.setText(String.valueOf(selectedYear));
                selectMonth(selectedMonth,selectedYear);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void getDataFirst() {

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR)),
                month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(month.length()<2) month = "0" + month;

        String res=MainActivity.account.getTk();
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData("SELECT * FROM money WHERE tk='"+res+"' AND SUBSTR(date, 4, 2)='"+month+"' AND SUBSTR(date,7,4)='"+year+"'");
        arrayListMoney = new ArrayList<>();
        while (getDataMoney.moveToNext()) {
            int id = getDataMoney.getInt(0);
            String tk = getDataMoney.getString(1);
            String typePrice = getDataMoney.getString(2);
            String type = getDataMoney.getString(3);
            String date = getDataMoney.getString(4);
            int price = getDataMoney.getInt(5);
            String note = getDataMoney.getString(6);
            HomeFragment.arrayListMoney.add(new Money(id, tk, typePrice, type, date, price, note));
        }
        Collections.sort(HomeFragment.arrayListMoney);
        moneyAdapter = new MoneyAdapter(getActivity(), R.layout.line_money, arrayListMoney);
        lv.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
        update();
    }

}