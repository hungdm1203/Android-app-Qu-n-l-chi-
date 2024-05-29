package com.example.quanlychitieu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlychitieu.Adapter.ItemAdapter;
import com.example.quanlychitieu.Adapter.ReminderAdapter;
import com.example.quanlychitieu.Fragment.ChartFragment;
import com.example.quanlychitieu.Fragment.HomeFragment;
import com.example.quanlychitieu.Fragment.SearchFragment;
import com.example.quanlychitieu.Fragment.SettingFragment;
import com.example.quanlychitieu.Models.Account;
import com.example.quanlychitieu.Models.Item;
import com.example.quanlychitieu.Models.KeHoachCT;
import com.example.quanlychitieu.Models.Money;
import com.example.quanlychitieu.Models.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SettingFragment.FragmentAListener {

    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private Handler handler;
    private Runnable runnable;
    private BottomNavigationView bottomNavigationView;
    public static ArrayList<Item> arrChiPhi=new ArrayList<>(),
            arrThuNhap=new ArrayList<>();
    public static Account account;
    public static DatabaseSQLite databaseSQLite;
    public static ArrayList<Account> accountArrayList;
//    public  static ArrayList<Money> moneyArrayList;

    public static ArrayList<Reminder> reminderArrayList;

    private HomeFragment fragmentHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        Login();
        arrChiPhi.add(new Item("Mua sắm",R.drawable.baseline_shopping_cart_24));
        arrChiPhi.add(new Item("Sức khỏe",R.drawable.baseline_local_hospital_24));
        arrChiPhi.add(new Item("Thực phẩm", R.drawable.baseline_food_bank_24));
        arrChiPhi.add(new Item("Hóa đơn", R.drawable.baseline_wysiwyg_24));
        arrChiPhi.add(new Item("Học tập", R.drawable.baseline_menu_book_24));
        arrChiPhi.add(new Item("Đồ điện tử", R.drawable.baseline_smartphone_24));
        arrChiPhi.add(new Item("Đi lại", R.drawable.baseline_directions_subway_24));
        arrChiPhi.add(new Item("Giải trí",R.drawable.baseline_add_reaction_24));
        arrChiPhi.add(new Item("Quà tặng", R.drawable.baseline_card_giftcard_24));
        arrChiPhi.add(new Item("Du lịch", R.drawable.baseline_airplanemode_active_24));
        arrChiPhi.add(new Item("Thể thao", R.drawable.baseline_sports_basketball_24));
        arrChiPhi.add(new Item("Khác", R.drawable.baseline_difference_24));

        arrThuNhap.add(new Item("Lương",R.drawable.baseline_credit_card_24));
        arrThuNhap.add(new Item("Tiền tiết kiệm", R.drawable.baseline_savings_24));
        arrThuNhap.add(new Item("Bán thời gian", R.drawable.baseline_access_time_24));
        arrThuNhap.add(new Item("Khoản đầu tư", R.drawable.baseline_moving_24));
        arrThuNhap.add(new Item("Giải thưởng", R.drawable.baseline_interests_24));
        arrThuNhap.add(new Item("Khác", R.drawable.baseline_difference_24));


        handler = new Handler();

    }


    @Override
    public void onResume() {
        super.onResume();
        getDataAccount();
    }

    @Override
    public void onCallMainActivityMethod() {
        onResume();
        Login();
    }

    private void createDB() {
        databaseSQLite=new DatabaseSQLite(this,"qlct.sqlite",null,1);

        //tao bang account
        databaseSQLite.QueryData("CREATE TABLE IF NOT EXISTS account (tk TEXT PRIMARY KEY, mk TEXT, hinhanh BLOB)");
        databaseSQLite.QueryData("CREATE TABLE IF NOT EXISTS money (id INTEGER PRIMARY KEY AUTOINCREMENT, tk TEXT, " +
                "typePrice TEXT, type TEXT, date TEXT, price INTEGER, note TEXT,FOREIGN KEY (tk) REFERENCES account(tk))");
        databaseSQLite.QueryData("CREATE TABLE IF NOT EXISTS reminder (id INTEGER PRIMARY KEY AUTOINCREMENT, tk TEXT, " +
                "time TEXT, note TEXT, status INTEGER, FOREIGN KEY (tk) REFERENCES account(tk))");
        databaseSQLite.QueryData("CREATE TABLE IF NOT EXISTS khChiTieu (id INTEGER PRIMARY KEY AUTOINCREMENT, tk TEXT," +
                "ct1 INTEGER, ct2 INTEGER, ct3 INTEGER, ct4 INTEGER, ct5 INTEGER, ct6 INTEGER, ct7 INTEGER, ct8 INTEGER," +
                "ct9 INTEGER, ct10 INTEGER, ct11 INTEGER, ct12 INTEGER, month TEXT, FOREIGN KEY (tk) REFERENCES account(tk))");
        databaseSQLite.QueryData("CREATE TABLE IF NOT EXISTS khThuNhap (id INTEGER PRIMARY KEY AUTOINCREMENT, tk TEXT," +
                "tn1 INTEGER, tn2 INTEGER, tn3 INTEGER, tn4 INTEGER, tn5 INTEGER, tn6 INTEGER," +
                "month TEXT, FOREIGN KEY (tk) REFERENCES account(tk))");

        getDataAccount();
    }

    public void getDataAccount() {
        //select dataAccount cho vao Arraylist
        Cursor getDataAcc=databaseSQLite.GetData("SELECT * FROM account");
        accountArrayList=new ArrayList<>();
        while (getDataAcc.moveToNext()){
            String tk=getDataAcc.getString(0);
            String mk=getDataAcc.getString(1);
            byte[] hinhanh=getDataAcc.getBlob(2);
            accountArrayList.add(new Account(tk,mk,hinhanh));
        }
    }

    public void getDataMoney() {

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR)),
                month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(month.length()<2) month = "0" + month;

        String res=account.getTk();
        Cursor getDataMoney = databaseSQLite.GetData("SELECT * FROM money WHERE tk='"+res+"' AND SUBSTR(date, 4, 2)='"+month+"' AND SUBSTR(date,7,4)='"+year+"'");
        HomeFragment.arrayListMoney = new ArrayList<>();
//        moneyArrayList=new ArrayList<>();
        while (getDataMoney.moveToNext()) {
            int id = getDataMoney.getInt(0);
            String tk = getDataMoney.getString(1);
            String typePrice = getDataMoney.getString(2);
            String type = getDataMoney.getString(3);
            String date = getDataMoney.getString(4);
            int price = getDataMoney.getInt(5);
            String note = getDataMoney.getString(6);
            HomeFragment.arrayListMoney.add(new Money(id, tk, typePrice, type, date, price, note));
//            moneyArrayList.add(new Money(id, tk, typePrice, type, date, price, note));
        }
//        Collections.sort(moneyArrayList);
        Collections.sort(HomeFragment.arrayListMoney);
    }


    //chuc nang dang nhap
    private void Login() {
        Intent intent=new Intent(MainActivity.this,Login_Signup.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            String res=data.getStringExtra("res");
            for (Account acc:accountArrayList) {
                if(acc.getTk().equals(res)){
                    account=acc;
                }
            }
            getDataMoney();
//            replaceFragment(fragmentHome);
            Anhxa();
            startRepeatingTask();

        }
    }

    private void Anhxa() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        fab=findViewById(R.id.fab);
        drawerLayout=findViewById(R.id.drawer_layout);
        fragmentHome=new HomeFragment();
        replaceFragment(fragmentHome);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home) replaceFragment(new HomeFragment());
            else if(item.getItemId()==R.id.chart) replaceFragment(new ChartFragment());
            else if(item.getItemId()==R.id.search) replaceFragment(new SearchFragment());
            else if(item.getItemId()==R.id.setting) replaceFragment(new SettingFragment());
            return true;
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
                onResume();
            }
        });
    }


    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        final GridView gridView;
        final String date,date2;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);


        LinearLayout themChiPhiLayout = dialog.findViewById(R.id.layoutThemChiPhi);
        LinearLayout themThuNhapLayout = dialog.findViewById(R.id.layoutThemThuNhap);
        LinearLayout taoLoiNhacLayout = dialog.findViewById(R.id.layoutTaoLoiNhac);
        LinearLayout noteLayout = dialog.findViewById(R.id.addNote);
        LinearLayout addCPTNLayout=dialog.findViewById(R.id.addCpTn);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        ImageView imgChangeTime= dialog.findViewById(R.id.changeTime);
        TextView tvChangeTime= dialog.findViewById(R.id.time);
        TextView tvTypeCPTN= dialog.findViewById(R.id.typeCpTn);
        TextView tvDateCPTN = dialog.findViewById(R.id.dateCpTn);
        EditText edtNoteLN= dialog.findViewById(R.id.note);
        EditText edtMoney= dialog.findViewById(R.id.moneyCpTn);
        EditText edtNoteCPTN = dialog.findViewById(R.id.noteCpTn);
        Button btnAddLN=dialog.findViewById(R.id.btnAddNote);
        Button btnAddCPTN = dialog.findViewById(R.id.btnAddCpTn);


        ItemAdapter itemChiphi= new ItemAdapter(MainActivity.this, R.layout.line_item, arrChiPhi);
        ItemAdapter itemThuNhap = new ItemAdapter(MainActivity.this, R.layout.line_item, arrThuNhap);
        gridView = dialog.findViewById(R.id.gridview);


        themChiPhiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onResume();
                themChiPhiLayout.setBackgroundColor(Color.parseColor("#8692f7"));
                themThuNhapLayout.setBackgroundColor(Color.WHITE);
                taoLoiNhacLayout.setBackgroundColor(Color.WHITE);
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(itemChiphi);
                noteLayout.setVisibility(View.GONE);
                addCPTNLayout.setVisibility(View.GONE);


                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        addCPTNLayout.setVisibility(View.VISIBLE);
                        tvTypeCPTN.setText(arrChiPhi.get(position).getType());

                        tvDateCPTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                calendar.set(Calendar.YEAR, year);
                                                calendar.set(Calendar.MONTH, month);
                                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                                                tvDateCPTN.setText(sdf.format(calendar.getTime()));

                                            }
                                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                                datePickerDialog.show();
                            }
                        });
                        btnAddCPTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(tvDateCPTN.getText().equals("Chọn ngày")){
                                    Toast.makeText(MainActivity.this, "Bạn hãy chọn ngày!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    String tk=account.getTk(),
                                            typePrice= "Chi phí",
                                            type = arrChiPhi.get(position).getType(),
                                            date = tvDateCPTN.getText().toString(),
                                            note= edtNoteCPTN.getText().toString();

                                    try {
                                        int price = Integer.parseInt(edtMoney.getText().toString());
                                        Money m=new Money(tk,typePrice,type,date,price,note);
                                        databaseSQLite.InsertMoney(m);
                                        getDataMoney();
                                        fragmentHome.getData();
                                        dialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Thêm chi phí thành công!!", Toast.LENGTH_SHORT).show();
                                        KeHoachCT k=getKhChiTieu();
                                        int tmp[]={k.getCt1(),k.getCt2(),k.getCt3(),k.getCt4(),k.getCt5(),k.getCt6(),k.getCt7(),k.getCt8(),k.getCt9(),k.getCt10(),k.getCt11(),k.getCt12()};
                                        int sum=0, total=0;
                                        for (Money i:HomeFragment.arrayListMoney){
                                            if(i.getType().equals(tvTypeCPTN.getText().toString())) sum+=i.getPrice();
                                            if(i.getTypePrice().equals("Chi phí")){
                                                total -= i.getPrice();
                                            } else  total += i.getPrice();
                                        }
                                        if(sum>=tmp[position]){
                                            NotificationReceiver receiver = new NotificationReceiver();
                                            receiver.onReceive(getApplicationContext(),1, arrChiPhi.get(position).getType());
                                        }
                                        if(total<=0){
                                            NotificationReceiver receiver = new NotificationReceiver();
                                            receiver.onReceive(getApplicationContext(),2, "");
                                        }
                                        if(total>0 && total<=1000000){
                                            NotificationReceiver receiver = new NotificationReceiver();
                                            receiver.onReceive(getApplicationContext(),3, total+"");
                                        }
                                    } catch (Exception e){
                                        Toast.makeText(MainActivity.this, "Vui lòng nhập số tiền!!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                    }
                });

            }
        });

        themThuNhapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onResume();
                themThuNhapLayout.setBackgroundColor(Color.parseColor("#8692f7"));
                themChiPhiLayout.setBackgroundColor(Color.WHITE);
                taoLoiNhacLayout.setBackgroundColor(Color.WHITE);
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(itemThuNhap);
                noteLayout.setVisibility(View.GONE);
                addCPTNLayout.setVisibility(View.GONE);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        addCPTNLayout.setVisibility(View.VISIBLE);
                        tvTypeCPTN.setText(arrThuNhap.get(position).getType());

                        tvDateCPTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                calendar.set(Calendar.YEAR, year);
                                                calendar.set(Calendar.MONTH, month);
                                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                                                tvDateCPTN.setText(sdf.format(calendar.getTime()));

                                            }
                                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                                datePickerDialog.show();
                            }
                        });
                        btnAddCPTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(tvDateCPTN.getText().equals("Chọn ngày")){
                                    Toast.makeText(MainActivity.this, "Bạn hãy chọn ngày!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    String tk=account.getTk(),
                                            typePrice= "Thu nhập",
                                            type = arrThuNhap.get(position).getType(),
                                            date = tvDateCPTN.getText().toString(),
                                            note= edtNoteCPTN.getText().toString();

                                    try {
                                        int price = Integer.parseInt(edtMoney.getText().toString());
                                        Money m=new Money(tk,typePrice,type,date,price,note);
                                        databaseSQLite.InsertMoney(m);
//                                        databaseSQLite.QueryData("INSERT INTO money(tk,typePrice,type,date,price,note) VALUES('"+tk+"','"+typePrice+"','"+type+"','"+date+"','"+price+"','"+note+"')");
                                        getDataMoney();
                                        fragmentHome.getData();
                                        dialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Thêm thu nhập thành công!!", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e){
                                        Toast.makeText(MainActivity.this, "Vui lòng nhập số tiền!!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                    }
                });


            }
        });

        taoLoiNhacLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onResume();
                taoLoiNhacLayout.setBackgroundColor(Color.parseColor("#8692f7"));
                themChiPhiLayout.setBackgroundColor(Color.WHITE);
                themThuNhapLayout.setBackgroundColor(Color.WHITE);
                gridView.setVisibility(View.GONE);
                addCPTNLayout.setVisibility(View.GONE);
                noteLayout.setVisibility(View.VISIBLE);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                tvChangeTime.setText(df.format(c.getTime()));

                imgChangeTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, (view, hourOfDay, minute) -> {
                            // Khi người dùng chọn thời gian, hiển thị thời gian đã chọn trên TextView
                            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            tvChangeTime.setText(selectedTime);
                        },
                                // Thiết lập giờ và phút mặc định
                                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                Calendar.getInstance().get(Calendar.MINUTE),
                                true // Cho phép chọn 24h
                        );
                        timePickerDialog.show();
                    }
                });

                btnAddLN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Reminder r=new Reminder(account.getTk(),tvChangeTime.getText().toString(),edtNoteLN.getText().toString());
                        databaseSQLite.InsertReminder(r);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Bạn đã thêm lời nhắc thành công!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void startRepeatingTask() {
        runnable = new Runnable() {
            @Override
            public void run() {
                getDataReminder();
                showCurrentTime();
                handler.postDelayed(this, 60000); // Repeat every 1 minute (60000 milliseconds)
            }
        };
        handler.post(runnable);
    }

    private void showCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < reminderArrayList.size(); i++) {
            String s[]= reminderArrayList.get(i).getTime().split(":");
            int hour = Integer.parseInt(s[0]), minute = Integer.parseInt(s[1]);
            if (calendar.get(Calendar.HOUR_OF_DAY)==hour&& calendar.get(Calendar.MINUTE) == minute && reminderArrayList.get(i).getStatus()==1){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                NotificationReceiver receiver = new NotificationReceiver();
                receiver.onReceive(this,0, "");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    private void stopRepeatingTask() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }


    public void getDataReminder(){
        String res=MainActivity.account.getTk();
        Cursor getData = MainActivity.databaseSQLite.GetData("SELECT * FROM reminder WHERE tk='"+res+"'");
        reminderArrayList = new ArrayList<>();
        while (getData.moveToNext()) {
            int id = getData.getInt(0);
            String tk = getData.getString(1);
            String time = getData.getString(2);
            String note = getData.getString(3);
            int status = getData.getInt(4);
            reminderArrayList.add(new Reminder(id,tk, time, note, status));
        }
    }

    public KeHoachCT getKhChiTieu(){
        Calendar calendar = Calendar.getInstance();
        int m=calendar.get(Calendar.MONTH) + 1, y= calendar.get(Calendar.YEAR);
        String month = m+"-"+y;
        String res=MainActivity.account.getTk();
        Cursor c = MainActivity.databaseSQLite.GetData("SELECT * FROM khChiTieu WHERE tk='"+res+"' AND month='"+month+"'");
        if(c.getCount()>0){
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String tk = c.getString(1);
                int ct1 = c.getInt(2), ct2 = c.getInt(3),
                        ct3 = c.getInt(4),ct4=c.getInt(5),
                        ct5 = c.getInt(6), ct6 = c.getInt(7),
                        ct7 = c.getInt(8), ct8 = c.getInt(9),
                        ct9 = c.getInt(10), ct10 = c.getInt(11),
                        ct11 = c.getInt(12), ct12 = c.getInt(13);
                String mon = c.getString(14);
                KeHoachCT k = new KeHoachCT(id, tk, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12, mon);
                return k;
            }

        }
        return null;
    }

}