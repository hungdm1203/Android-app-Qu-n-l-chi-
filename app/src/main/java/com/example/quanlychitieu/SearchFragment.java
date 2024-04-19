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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Money> arrayListMoney;
    private String res=MainActivity.account.getTk();
    private ArrayAdapter<String> adapter;
    private String all[]= {
            "Tất cả","Mua sắm","Sức khỏe","Thực phẩm","Hóa đơn","Học tập","Đồ điện tử",
            "Đi lại","Giải trí","Quà tặng","Du lịch","Thể thao",
            "Lương","Tiền tiết kiệm","Bán thời gian","Khoản đầu tư","Giải thưởng","Khác"
    };
    private String tmp="Tất cả";

    SearchView searchView;
    Spinner spinner;
    TextView tvDate;
    ListView lv;
    MoneyAdapter moneyAdapter;
    Button btnRefesh;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        View view= inflater.inflate(R.layout.fragment_search, container, false);

        searchView=view.findViewById(R.id.search);
        spinner = view.findViewById(R.id.spinner);
        tvDate= view.findViewById(R.id.date);
        lv= view.findViewById(R.id.listView);
        btnRefesh = view.findViewById(R.id.btnRefesh);


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, all);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    if(tvDate.getText().toString().equals("Ngày")){
                        getData("SELECT * FROM money WHERE tk='" + res + "'");
                    } else {
                        getData("SELECT * FROM money WHERE tk='" + res + "' AND date='" + tvDate.getText().toString() + "'");
                    }
                } else {
                    if(tvDate.getText().toString().equals("Ngày")){
                        getData("SELECT * FROM money WHERE tk='" + res + "' AND type='"+all[position]+"'");
                    } else {
                        getData("SELECT * FROM money WHERE tk='" + res + "' AND date='" + tvDate.getText().toString() + "' AND type='"+all[position]+"'");
                    }
                }
                tmp=parent.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), tvDate.getText().toString(), Toast.LENGTH_SHORT).show();
                showData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                showData();
            }
        });


        tvDate.setOnClickListener(new View.OnClickListener() {
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
                                String d = sdf.format(calendar.getTime());
                                tvDate.setText(d);

                                if(tmp.equals("Tất cả")){
                                    getData("SELECT * FROM money WHERE tk='"+res+"' AND date='"+d+"'");
                                } else {
                                    getData("SELECT * FROM money WHERE tk='" + res + "' AND date='" + d + "' AND type='" + tmp + "'");
                                }
                                showData();

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(tmp.equals("Tất cả")){
                    if(tvDate.getText().toString().equals("Ngày")){
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "'", query);
                    } else {
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND date='"+tvDate.getText().toString()+"'", query);
                    }
                } else {
                    if(tvDate.getText().toString().equals("Ngày")){
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND type='"+tmp+"'", query);
                    } else {
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND type='"+tmp+"' AND date='"+tvDate.getText().toString()+"'", query);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(tmp.equals("Tất cả")){
                    if(tvDate.getText().toString().equals("Ngày")){
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "'", newText);
                    } else {
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND date='"+tvDate.getText().toString()+"'", newText);
                    }
                } else {
                    if(tvDate.getText().toString().equals("Ngày")){
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND type='"+tmp+"'", newText);
                    } else {
                        getDataSearch("SELECT * FROM money WHERE tk='" + res + "' AND type='"+tmp+"' AND date='"+tvDate.getText().toString()+"'", newText);
                    }
                }
                showData();
                return false;
            }
        });

        btnRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("",false);
                spinner.setSelection(0);
                tvDate.setText("Ngày");
                getData("SELECT * FROM money WHERE tk='"+res+"'");
                showData();
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

    private void getData(String s) {
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData(s);
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
    }

    private void showData(){
        moneyAdapter = new MoneyAdapter(getActivity(), R.layout.line_money, arrayListMoney);
        lv.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
    }

    private void getDataSearch(String sql,String s) {
        Cursor getDataMoney = MainActivity.databaseSQLite.GetData(sql);
        arrayListMoney = new ArrayList<>();
        while (getDataMoney.moveToNext()) {
            int id = getDataMoney.getInt(0);
            String tk = getDataMoney.getString(1);
            String typePrice = getDataMoney.getString(2);
            String type = getDataMoney.getString(3);
            String date = getDataMoney.getString(4);
            int price = getDataMoney.getInt(5);
            String note = getDataMoney.getString(6);
            if(note.toLowerCase().contains(s.trim())){
                arrayListMoney.add(new Money(id, tk, typePrice, type, date, price, note));
            }
        }
        Collections.sort(arrayListMoney);
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
}