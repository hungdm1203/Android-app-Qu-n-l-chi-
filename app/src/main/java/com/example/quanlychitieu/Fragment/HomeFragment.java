package com.example.quanlychitieu.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlychitieu.Adapter.MoneyAdapter;
import com.example.quanlychitieu.MainActivity;
import com.example.quanlychitieu.Models.Item;
import com.example.quanlychitieu.Models.KeHoachCT;
import com.example.quanlychitieu.Models.KeHoachTN;
import com.example.quanlychitieu.Models.Money;
import com.example.quanlychitieu.R;

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
    private String month;

    TextView tvChiPhi,tvThuNhap,tvSoDu,tvYear,tvMonth;
    public static ArrayList<Money> arrayListMoney;
    MoneyAdapter moneyAdapter;
    ImageView imageView, imgKeHoach;


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
        imgKeHoach = view.findViewById(R.id.keHoach);

        Calendar calendar = Calendar.getInstance();
        tvYear.setText(calendar.get(Calendar.YEAR)+"");
        tvMonth.setText("Thg "+(calendar.get(Calendar.MONTH)+1));
        month = (calendar.get(Calendar.MONTH) + 1) + "-"+calendar.get(Calendar.YEAR);
        getDataFirst();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectMonth();
            }
        });

        imgKeHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeHoach();
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
        for(Item i: MainActivity.arrChiPhi){
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
                String tk=MainActivity.account.getTk();
                int id=arrayListMoney.get(pos).getId();
                int price=Integer.valueOf(edtPrice.getText().toString());
                String strDate=date.getText().toString();
                String strNote=edtNote.getText().toString();
                MainActivity.databaseSQLite.QueryData("UPDATE money SET price='"+price+"', date='"+strDate+"', note='"+strNote+"' WHERE id='"+id+"' AND tk='"+tk+"'");
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
                String tk=MainActivity.account.getTk();
                MainActivity.databaseSQLite.QueryData("DELETE FROM money WHERE id='"+id+"' AND tk='"+tk+"'");
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
        tvChiPhi.setText(strCP);
        tvThuNhap.setText(strTN);
        tvSoDu.setText(strSD);
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
                month= selectedMonth+"-"+selectedYear;
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

    public void showKeHoach(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.home_plan_layout);

        LinearLayout layoutCP= dialog.findViewById(R.id.layoutChiPhi),
                layoutTN = dialog.findViewById(R.id.layoutThuNhap);
        ScrollView cp= dialog.findViewById(R.id.chiPhi), tn = dialog.findViewById(R.id.thuNhap);
        TextView tv01 = dialog.findViewById(R.id.tv01), tv02= dialog.findViewById(R.id.tv02),
                tv03 = dialog.findViewById(R.id.tv03), tv04 = dialog.findViewById(R.id.tv04),
                tv05 = dialog.findViewById(R.id.tv05), tv06 = dialog.findViewById(R.id.tv06),
                tv07 = dialog.findViewById(R.id.tv07), tv08 = dialog.findViewById(R.id.tv08),
                tv09 = dialog.findViewById(R.id.tv09), tv10 = dialog.findViewById(R.id.tv10),
                tv11 = dialog.findViewById(R.id.tv11), tv12 = dialog.findViewById(R.id.tv12),
                tv001 = dialog.findViewById(R.id.tv001), tv002 = dialog.findViewById(R.id.tv002),
                tv003 = dialog.findViewById(R.id.tv003), tv004 = dialog.findViewById(R.id.tv004),
                tv005 = dialog.findViewById(R.id.tv005), tv006 = dialog.findViewById(R.id.tv006),
                sumCP = dialog.findViewById(R.id.sumCP), sumTN = dialog.findViewById(R.id.sumTN),
                monthKeHoach = dialog.findViewById(R.id.monthKeHoach);

        layoutCP.setBackgroundColor(Color.parseColor("#8692f7"));
        layoutTN.setBackgroundColor(Color.WHITE);
        cp.setVisibility(View.VISIBLE);
        tn.setVisibility(View.GONE);

        monthKeHoach.setText("Kế hoạch chi tiêu tháng "+month);
        KeHoachCT k=getKhChiTieu(month);


        if(k!=null){
            tv01.setText(String.format("%,d", k.getCt1())); tv02.setText(String.format("%,d", k.getCt2()));
            tv03.setText(String.format("%,d", k.getCt3())); tv04.setText(String.format("%,d", k.getCt4()));
            tv05.setText(String.format("%,d", k.getCt5())); tv06.setText(String.format("%,d", k.getCt6()));
            tv07.setText(String.format("%,d", k.getCt7())); tv08.setText(String.format("%,d", k.getCt8()));
            tv09.setText(String.format("%,d", k.getCt9())); tv10.setText(String.format("%,d", k.getCt10()));
            tv11.setText(String.format("%,d", k.getCt11())); tv12.setText(String.format("%,d", k.getCt12()));
            sumCP.setText(String.format("%,d", k.getSum()));
        } else{
            tv01.setText("0"); tv02.setText("0");
            tv03.setText("0"); tv04.setText("0");
            tv05.setText("0"); tv06.setText("0");
            tv07.setText("0"); tv08.setText("0");
            tv09.setText("0"); tv10.setText("0");
            tv11.setText("0"); tv12.setText("0");
            sumCP.setText("0");
        }

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        layoutCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutCP.setBackgroundColor(Color.parseColor("#8692f7"));
                layoutTN.setBackgroundColor(Color.WHITE);
                cp.setVisibility(View.VISIBLE);
                tn.setVisibility(View.GONE);
                KeHoachCT khCT=getKhChiTieu(month);


                if(khCT!=null){
                    tv01.setText(String.format("%,d", khCT.getCt1())); tv02.setText(String.format("%,d", khCT.getCt2()));
                    tv03.setText(String.format("%,d", khCT.getCt3())); tv04.setText(String.format("%,d", khCT.getCt4()));
                    tv05.setText(String.format("%,d", khCT.getCt5())); tv06.setText(String.format("%,d", khCT.getCt6()));
                    tv07.setText(String.format("%,d", khCT.getCt7())); tv08.setText(String.format("%,d", khCT.getCt8()));
                    tv09.setText(String.format("%,d", khCT.getCt9())); tv10.setText(String.format("%,d", khCT.getCt10()));
                    tv11.setText(String.format("%,d", khCT.getCt11())); tv12.setText(String.format("%,d", khCT.getCt12()));
                    sumCP.setText(String.format("%,d", khCT.getSum()));
                } else{
                    tv01.setText("0"); tv02.setText("0");
                    tv03.setText("0"); tv04.setText("0");
                    tv05.setText("0"); tv06.setText("0");
                    tv07.setText("0"); tv08.setText("0");
                    tv09.setText("0"); tv10.setText("0");
                    tv11.setText("0"); tv12.setText("0");
                    sumCP.setText("0");
                }
            }
        });

        layoutTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutTN.setBackgroundColor(Color.parseColor("#8692f7"));
                layoutCP.setBackgroundColor(Color.WHITE);
                tn.setVisibility(View.VISIBLE);
                cp.setVisibility(View.GONE);
                KeHoachTN khTN = getKhThuNhap(month);

                if(khTN!=null){
                    tv001.setText(String.format("%,d", khTN.getTn1())); tv002.setText(String.format("%,d", khTN.getTn2()));
                    tv003.setText(String.format("%,d", khTN.getTn3())); tv004.setText(String.format("%,d", khTN.getTn4()));
                    tv005.setText(String.format("%,d", khTN.getTn5())); tv006.setText(String.format("%,d", khTN.getTn6()));
                    sumTN.setText(String.format("%,d", khTN.getSum()));
                } else{
                    tv001.setText("0"); tv002.setText("0");
                    tv003.setText("0"); tv004.setText("0");
                    tv005.setText("0"); tv006.setText("0");
                    sumTN.setText("0");
                }
            }
        });

    }


    public KeHoachCT getKhChiTieu(String month){
        String res=MainActivity.account.getTk();
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData("SELECT * FROM khChiTieu WHERE tk='"+res+"' AND month='"+month+"'");
        if(getDataMoney.getCount()>0){
            while (getDataMoney.moveToNext()) {
                int id = getDataMoney.getInt(0);
                String tk = getDataMoney.getString(1);
                int ct1 = getDataMoney.getInt(2), ct2 = getDataMoney.getInt(3),
                        ct3 = getDataMoney.getInt(4),ct4=getDataMoney.getInt(5),
                        ct5 = getDataMoney.getInt(6), ct6 = getDataMoney.getInt(7),
                        ct7 = getDataMoney.getInt(8), ct8 = getDataMoney.getInt(9),
                        ct9 = getDataMoney.getInt(10), ct10 = getDataMoney.getInt(11),
                        ct11 = getDataMoney.getInt(12), ct12 = getDataMoney.getInt(13);
                String mon = getDataMoney.getString(14);
                KeHoachCT k = new KeHoachCT(id, tk, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, mon);
                return k;
            }

        }
        return null;
    }

    public KeHoachTN getKhThuNhap(String month){
        String res=MainActivity.account.getTk();
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData("SELECT * FROM khThuNhap WHERE tk='"+res+"' AND month='"+month+"'");
        if(getDataMoney.getCount()>0){
            while (getDataMoney.moveToNext()) {
                int id = getDataMoney.getInt(0);
                String tk = getDataMoney.getString(1);
                int tn1 = getDataMoney.getInt(2), tn2 = getDataMoney.getInt(3),
                        tn3 = getDataMoney.getInt(4),tn4=getDataMoney.getInt(5),
                        tn5 = getDataMoney.getInt(6), tn6 = getDataMoney.getInt(7);
                String mon = getDataMoney.getString(8);
                KeHoachTN k = new KeHoachTN(id, tk, tn1, tn2, tn3, tn4, tn5, tn6,mon);
                return k;
            }

        }
        return null;
    }

}