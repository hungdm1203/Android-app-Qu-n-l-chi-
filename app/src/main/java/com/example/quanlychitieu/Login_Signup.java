package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.quanlychitieu.Adapter.ViewPagerAdapter;
import com.example.quanlychitieu.Fragment.LoginTabFragment;
import com.example.quanlychitieu.Fragment.SignupTabFragment;
import com.example.quanlychitieu.Models.Account;
import com.google.android.material.tabs.TabLayout;

public class Login_Signup extends AppCompatActivity implements LoginTabFragment.OnButtonClickListener, SignupTabFragment.OnButtonClickListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
//        setContentView(R.layout.fragment_login_tab);


        tabLayout=findViewById(R.id.tab_layout);
        viewPager2=findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Đăng nhập"));
        tabLayout.addTab(tabLayout.newTab().setText("Đăng kí"));

        FragmentManager fragmentManager=getSupportFragmentManager();
        adapter=new ViewPagerAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onButtonSignupClick(String tk, String mk, String check) {
        int ok=1;
        for(Account account:MainActivity.accountArrayList){
            if(tk.equals(account.getTk())){
                ok=0;break;
            }
        }
        if(ok==0){
            Toast.makeText(this, "Tài khoản đã tồn tại!!", Toast.LENGTH_LONG).show();
        } else{
            if(mk.length()<6){
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 kí tự!!", Toast.LENGTH_LONG).show();
            } else if(mk.length()>=6&&!mk.equals(check)){
                Toast.makeText(this, "Bạn xác nhận chính xác mật khẩu!!", Toast.LENGTH_LONG).show();
            } else{
//                MainActivity.databaseSQLite.QueryData("INSERT INTO account (tk,mk) VALUES('"+tk+"','"+mk+"')");
                Account acc = new Account(tk, mk);
                MainActivity.databaseSQLite.InsertUser(acc);
                MainActivity.accountArrayList.add(acc);
                Toast.makeText(this, "Tạo tài khoản thành công, xin mời đăng nhập!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onButtonLoginClick(String tk,String mk) {
        int ok=0;
        String res = null;
        for(Account acc:MainActivity.accountArrayList){
            if(tk.equals(acc.getTk())&&mk.equals(acc.getMk())){
                res=acc.getTk();
                MainActivity.account = acc;
                ok=1;break;
            }
        }
        if(ok==1){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("res", res);
            setResult(1, resultIntent);
            finish();
        } else{
            Toast.makeText(this, "Thông tin đăng nhập không đúng!!", Toast.LENGTH_LONG).show();
        }
    }

}