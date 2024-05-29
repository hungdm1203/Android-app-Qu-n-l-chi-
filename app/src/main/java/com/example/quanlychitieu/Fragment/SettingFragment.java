package com.example.quanlychitieu.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlychitieu.Adapter.ReminderAdapter;
import com.example.quanlychitieu.MainActivity;
import com.example.quanlychitieu.Models.KeHoachTN;
import com.example.quanlychitieu.Models.KeHoachCT;
import com.example.quanlychitieu.Models.Money;
import com.example.quanlychitieu.Models.Reminder;
import com.example.quanlychitieu.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnLogout,btnDoiMK,btnDoiIMG,btnLoiNhac,btnVote,btnKeHoach;
    private TextView tvTK;
    private ImageView imgAccount;



    private FragmentAListener listener;

    // khai bao interface de tro ve homefragment khi dang xuat
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentAListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    public interface FragmentAListener {
        void onCallMainActivityMethod();
    }



    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        btnDoiMK=view.findViewById(R.id.buttonDoiMK);
        btnDoiIMG=view.findViewById(R.id.buttonDoiIMG);
        btnLoiNhac=view.findViewById(R.id.buttonLoiNhac);
        btnVote=view.findViewById(R.id.buttonVote);
        btnKeHoach = view.findViewById(R.id.buttonKeHoach);
        btnLogout=view.findViewById(R.id.buttonLogout);
        imgAccount=view.findViewById(R.id.imageAccount);
        tvTK=view.findViewById(R.id.textViewTK);

        if(MainActivity.account.getHinhanh()!=null){
            byte[] hinhanh=MainActivity.account.getHinhanh();
            Bitmap bitmap= BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
            imgAccount.setImageBitmap(bitmap);
        }


        load();
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVoteClick();
            }
        });


        btnKeHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKeHoachClick();
            }
        });


        btnLoiNhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoiNhacClick();
            }
        });


        btnDoiIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });


        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDoiMKClick();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogoutClick();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void btnVoteClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.votelayout);

        EditText edt=dialog.findViewById(R.id.edtVote);
        Button btnGui=dialog.findViewById(R.id.btnGui),
                btnHuy=dialog.findViewById(R.id.btnHuy);
        ImageView start1=dialog.findViewById(R.id.start1),
                start2=dialog.findViewById(R.id.start2),
                start3=dialog.findViewById(R.id.start3),
                start4=dialog.findViewById(R.id.start4),
                start5=dialog.findViewById(R.id.start5),
                cancelBtn=dialog.findViewById(R.id.cancelButton);

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start1.setImageResource(R.drawable.baseline_star_24);
                start2.setImageResource(R.drawable.baseline_star_border_24);
                start3.setImageResource(R.drawable.baseline_star_border_24);
                start4.setImageResource(R.drawable.baseline_star_border_24);
                start5.setImageResource(R.drawable.baseline_star_border_24);
            }
        });
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start1.setImageResource(R.drawable.baseline_star_24);
                start2.setImageResource(R.drawable.baseline_star_24);
                start3.setImageResource(R.drawable.baseline_star_border_24);
                start4.setImageResource(R.drawable.baseline_star_border_24);
                start5.setImageResource(R.drawable.baseline_star_border_24);
            }
        });
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start1.setImageResource(R.drawable.baseline_star_24);
                start2.setImageResource(R.drawable.baseline_star_24);
                start3.setImageResource(R.drawable.baseline_star_24);
                start4.setImageResource(R.drawable.baseline_star_border_24);
                start5.setImageResource(R.drawable.baseline_star_border_24);
            }
        });
        start4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start1.setImageResource(R.drawable.baseline_star_24);
                start2.setImageResource(R.drawable.baseline_star_24);
                start3.setImageResource(R.drawable.baseline_star_24);
                start4.setImageResource(R.drawable.baseline_star_24);
                start5.setImageResource(R.drawable.baseline_star_border_24);
            }
        });
        start5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start1.setImageResource(R.drawable.baseline_star_24);
                start2.setImageResource(R.drawable.baseline_star_24);
                start3.setImageResource(R.drawable.baseline_star_24);
                start4.setImageResource(R.drawable.baseline_star_24);
                start5.setImageResource(R.drawable.baseline_star_24);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText("");
                start1.setImageResource(R.drawable.baseline_star_border_24);
                start2.setImageResource(R.drawable.baseline_star_border_24);
                start1.setImageResource(R.drawable.baseline_star_border_24);
                start1.setImageResource(R.drawable.baseline_star_border_24);
                start1.setImageResource(R.drawable.baseline_star_border_24);
                dialog.dismiss();
                Toast.makeText(getActivity(), "Cảm ơn bạn đã đánh giá sản phầm", Toast.LENGTH_SHORT).show();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
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

    public void btnLogoutClick(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setMessage("Bạn có muốn đăng xuất??");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.account=null;
                listener.onCallMainActivityMethod();
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void btnDoiMKClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.changepasswordlayout);

        EditText edtMK=dialog.findViewById(R.id.password),
                edtnewMK=dialog.findViewById(R.id.newpassword),
                edtConfirm=dialog.findViewById(R.id.confirmpassword);
        Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
        ImageView cancelBtn=dialog.findViewById(R.id.cancelButton);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ok=1;
                String mk=edtMK.getText().toString(),
                        newmk=edtnewMK.getText().toString(),
                        confirmmk=edtConfirm.getText().toString();
                if(MainActivity.account.getMk().equals(mk)){
                    if(newmk.length()<6){
                        ok=0;
                        Toast.makeText(getActivity(), "Mật khẩu mới phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                    } else{
                        if(!newmk.equals(confirmmk)){
                            ok=0;
                            Toast.makeText(getActivity(), "Bạn phải xác nhận đúng mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else{
                    ok=0;
                    Toast.makeText(getActivity(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                }

                if(ok==1){
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.account.setMk(newmk);
                    MainActivity.databaseSQLite.QueryData("UPDATE account SET mk='"+newmk+"' WHERE tk='"+MainActivity.account.getTk()+"'");
                }

            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
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

    public void btnLoiNhacClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reminder_layout);

        ListView lv= dialog.findViewById(R.id.listReminder);
        ImageView cancelBtn=dialog.findViewById(R.id.cancelButton);
        Button btn=dialog.findViewById(R.id.btnSave);
        ImageView imgTime = dialog.findViewById(R.id.changeTime);
        TextView tvTime = dialog.findViewById(R.id.time);
        EditText edtNote=dialog.findViewById(R.id.note);
        ArrayList<Reminder> arrReminder=new ArrayList<>();
        ReminderAdapter adapter=new ReminderAdapter(getActivity(), R.layout.line_reminder, arrReminder);


        getDataReminder(lv, arrReminder, adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder r = arrReminder.get(position);
                if (r.getStatus() == 0) {
                    r.setStatus(1);
                    MainActivity.databaseSQLite.QueryData("UPDATE reminder SET status=1 WHERE id='"+r.getId()+"'");
                } else {
                    r.setStatus(0);
                    MainActivity.databaseSQLite.QueryData("UPDATE reminder SET status=0 WHERE id='"+r.getId()+"'");
                }
                getDataReminder(lv,arrReminder,adapter);
            }
        });



        cancelBtn.setOnClickListener(new View.OnClickListener() {
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

    public void load() {
        tvTK.setText(MainActivity.account.getTk());
        if(MainActivity.account.getHinhanh()!=null){
            //chuyen tu byte[]->image hinh anh
            byte[] hinhanh=MainActivity.account.getHinhanh();
            Bitmap bm= BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
            imgAccount.setImageBitmap(bm);
        } else{
            imgAccount.setImageResource(R.drawable.baseline_account);
        }
    }

    private void btnKeHoachClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.planlayout);

        LinearLayout layoutCP= dialog.findViewById(R.id.layoutChiPhi),
                        layoutTN = dialog.findViewById(R.id.layoutThuNhap);
        ScrollView cp= dialog.findViewById(R.id.chiPhi), tn = dialog.findViewById(R.id.thuNhap);
        EditText edt01 = dialog.findViewById(R.id.edt01), edt02= dialog.findViewById(R.id.edt02),
                edt03 = dialog.findViewById(R.id.edt03), edt04 = dialog.findViewById(R.id.edt04),
                edt05 = dialog.findViewById(R.id.edt05), edt06 = dialog.findViewById(R.id.edt06),
                edt07 = dialog.findViewById(R.id.edt07), edt08 = dialog.findViewById(R.id.edt08),
                edt09 = dialog.findViewById(R.id.edt09), edt10 = dialog.findViewById(R.id.edt10),
                edt11 = dialog.findViewById(R.id.edt11), edt12 = dialog.findViewById(R.id.edt12),
                edt001 = dialog.findViewById(R.id.edt001), edt002 = dialog.findViewById(R.id.edt002),
                edt003 = dialog.findViewById(R.id.edt003), edt004 = dialog.findViewById(R.id.edt004),
                edt005 = dialog.findViewById(R.id.edt005), edt006 = dialog.findViewById(R.id.edt006);
        Button btnHuy = dialog.findViewById(R.id.btnHuy),
                btnOk = dialog.findViewById(R.id.btnOk);
        TextView sumCP = dialog.findViewById(R.id.sumCP), sumTN = dialog.findViewById(R.id.sumTN),
                tvMonth = dialog.findViewById(R.id.tvMonth);
        ImageView imgMonth = dialog.findViewById(R.id.imgMonth);

        layoutCP.setBackgroundColor(Color.parseColor("#8692f7"));
        layoutTN.setBackgroundColor(Color.WHITE);
        cp.setVisibility(View.VISIBLE);
        tn.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        int m= calendar.get(Calendar.MONTH)+1;
        int y= calendar.get(Calendar.YEAR);
        tvMonth.setText("Kế hoạch chi tiêu tháng "+m+"-"+y);
        KeHoachCT k=getKhChiTieu(m+"-"+y);

        if(k!=null){
            edt01.setText(String.valueOf(k.getCt1())); edt02.setText(String.valueOf(k.getCt2()));
            edt03.setText(String.valueOf(k.getCt3())); edt04.setText(String.valueOf(k.getCt4()));
            edt05.setText(String.valueOf(k.getCt5())); edt06.setText(String.valueOf(k.getCt6()));
            edt07.setText(String.valueOf(k.getCt7())); edt08.setText(String.valueOf(k.getCt8()));
            edt09.setText(String.valueOf(k.getCt9())); edt10.setText(String.valueOf(k.getCt10()));
            edt11.setText(String.valueOf(k.getCt11())); edt12.setText(String.valueOf(k.getCt12()));
            sumCP.setText(String.format("%,d", k.getSum()));
        } else{
            edt01.setText("0"); edt02.setText("0");
            edt03.setText("0"); edt04.setText("0");
            edt05.setText("0"); edt06.setText("0");
            edt07.setText("0"); edt08.setText("0");
            edt09.setText("0"); edt10.setText("0");
            edt11.setText("0"); edt12.setText("0");
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
                String month=tvMonth.getText().toString().substring(24);
                KeHoachCT khCT=getKhChiTieu(month);


                if(khCT!=null){
                    edt01.setText(String.valueOf(khCT.getCt1())); edt02.setText(String.valueOf(khCT.getCt2()));
                    edt03.setText(String.valueOf(khCT.getCt3())); edt04.setText(String.valueOf(khCT.getCt4()));
                    edt05.setText(String.valueOf(khCT.getCt5())); edt06.setText(String.valueOf(khCT.getCt6()));
                    edt07.setText(String.valueOf(khCT.getCt7())); edt08.setText(String.valueOf(khCT.getCt8()));
                    edt09.setText(String.valueOf(khCT.getCt9())); edt10.setText(String.valueOf(khCT.getCt10()));
                    edt11.setText(String.valueOf(khCT.getCt11())); edt12.setText(String.valueOf(khCT.getCt12()));
                    sumCP.setText(String.format("%,d", khCT.getSum()));
                } else{
                    edt01.setText("0"); edt02.setText("0");
                    edt03.setText("0"); edt04.setText("0");
                    edt05.setText("0"); edt06.setText("0");
                    edt07.setText("0"); edt08.setText("0");
                    edt09.setText("0"); edt10.setText("0");
                    edt11.setText("0"); edt12.setText("0");
                    sumCP.setText("0");
                }

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
                        if(khCT==null){
                            String tk=MainActivity.account.getTk();
                            int ct1 = Integer.parseInt(edt01.getText().toString()), ct2 = Integer.parseInt(edt02.getText().toString()),
                                    ct3 = Integer.parseInt(edt03.getText().toString()), ct4 = Integer.parseInt(edt04.getText().toString()),
                                    ct5 = Integer.parseInt(edt05.getText().toString()), ct6 = Integer.parseInt(edt06.getText().toString()),
                                    ct7 = Integer.parseInt(edt07.getText().toString()), ct8 = Integer.parseInt(edt08.getText().toString()),
                                    ct9 = Integer.parseInt(edt09.getText().toString()), ct10 = Integer.parseInt(edt10.getText().toString()),
                                    ct11 = Integer.parseInt(edt11.getText().toString()), ct12 = Integer.parseInt(edt12.getText().toString());
                            String month=tvMonth.getText().toString().substring(24);
                            KeHoachCT k=new KeHoachCT(tk, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12,month);
                            MainActivity.databaseSQLite.InsertKeHoachCT(k);
                            Toast.makeText(getActivity(), "Tạo kế hoạch thu chi thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            khCT.setCt1(Integer.parseInt(edt01.getText().toString()));khCT.setCt2(Integer.parseInt(edt02.getText().toString()));
                            khCT.setCt3(Integer.parseInt(edt03.getText().toString()));khCT.setCt4(Integer.parseInt(edt04.getText().toString()));
                            khCT.setCt5(Integer.parseInt(edt05.getText().toString()));khCT.setCt6(Integer.parseInt(edt06.getText().toString()));
                            khCT.setCt7(Integer.parseInt(edt07.getText().toString()));khCT.setCt8(Integer.parseInt(edt08.getText().toString()));
                            khCT.setCt9(Integer.parseInt(edt09.getText().toString()));khCT.setCt10(Integer.parseInt(edt10.getText().toString()));
                            khCT.setCt11(Integer.parseInt(edt11.getText().toString()));khCT.setCt12(Integer.parseInt(edt12.getText().toString()));
                            MainActivity.databaseSQLite.UpdateKeHoachCT(khCT);
                        }
                    }
                });
            }
        });

        layoutTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutTN.setBackgroundColor(Color.parseColor("#8692f7"));
                layoutCP.setBackgroundColor(Color.WHITE);
                tn.setVisibility(View.VISIBLE);
                cp.setVisibility(View.GONE);
                String month = tvMonth.getText().toString().substring(24);
                KeHoachTN khTN = getKhThuNhap(month);

                if(khTN!=null){
                    edt001.setText(String.valueOf(khTN.getTn1())); edt002.setText(String.valueOf(khTN.getTn2()));
                    edt003.setText(String.valueOf(khTN.getTn3())); edt004.setText(String.valueOf(khTN.getTn4()));
                    edt005.setText(String.valueOf(khTN.getTn5())); edt006.setText(String.valueOf(khTN.getTn6()));
                    sumTN.setText(String.format("%,d", khTN.getSum()));
                } else{
                    edt001.setText("0"); edt002.setText("0");
                    edt003.setText("0"); edt004.setText("0");
                    edt005.setText("0"); edt006.setText("0");
                    sumTN.setText("0");
                }

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "456", Toast.LENGTH_SHORT).show();
                        if(khTN==null){
                            String tk=MainActivity.account.getTk();
                            int tn1 = Integer.parseInt(edt001.getText().toString()), tn2 = Integer.parseInt(edt002.getText().toString()),
                                    tn3 = Integer.parseInt(edt003.getText().toString()), tn4 = Integer.parseInt(edt004.getText().toString()),
                                    tn5 = Integer.parseInt(edt005.getText().toString()), tn6 = Integer.parseInt(edt006.getText().toString());
                            String month=tvMonth.getText().toString().substring(24);
                            KeHoachTN k=new KeHoachTN(tk, tn1, tn2, tn3, tn4, tn5, tn6, month);
                            MainActivity.databaseSQLite.InsertKeHoachTN(k);
                            Toast.makeText(getActivity(), "Tạo kế hoạch thu chi thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            khTN.setTn1(Integer.parseInt(edt001.getText().toString()));khTN.setTn2(Integer.parseInt(edt002.getText().toString()));
                            khTN.setTn3(Integer.parseInt(edt003.getText().toString()));khTN.setTn4(Integer.parseInt(edt004.getText().toString()));
                            khTN.setTn5(Integer.parseInt(edt005.getText().toString()));khTN.setTn6(Integer.parseInt(edt006.getText().toString()));
                            MainActivity.databaseSQLite.UpdateKeHoachTN(khTN);
                            Toast.makeText(getActivity(), edt002.getText().toString()+"-", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String month = tvMonth.getText().toString().substring(24);
                KeHoachCT khCT=getKhChiTieu(month);
                if(khCT==null){
                    String tk=MainActivity.account.getTk();
                    int ct1 = Integer.parseInt(edt01.getText().toString()), ct2 = Integer.parseInt(edt02.getText().toString()),
                            ct3 = Integer.parseInt(edt03.getText().toString()), ct4 = Integer.parseInt(edt04.getText().toString()),
                            ct5 = Integer.parseInt(edt05.getText().toString()), ct6 = Integer.parseInt(edt06.getText().toString()),
                            ct7 = Integer.parseInt(edt07.getText().toString()), ct8 = Integer.parseInt(edt08.getText().toString()),
                            ct9 = Integer.parseInt(edt09.getText().toString()), ct10 = Integer.parseInt(edt10.getText().toString()),
                            ct11 = Integer.parseInt(edt11.getText().toString()), ct12 = Integer.parseInt(edt12.getText().toString());
                    KeHoachCT k=new KeHoachCT(tk, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12,month);
                    MainActivity.databaseSQLite.InsertKeHoachCT(k);
                    Toast.makeText(getActivity(), "Tạo kế hoạch thu chi thành công", Toast.LENGTH_SHORT).show();
                } else {
                    khCT.setCt1(Integer.parseInt(edt01.getText().toString()));khCT.setCt2(Integer.parseInt(edt02.getText().toString()));
                    khCT.setCt3(Integer.parseInt(edt03.getText().toString()));khCT.setCt4(Integer.parseInt(edt04.getText().toString()));
                    khCT.setCt5(Integer.parseInt(edt05.getText().toString()));khCT.setCt6(Integer.parseInt(edt06.getText().toString()));
                    khCT.setCt7(Integer.parseInt(edt07.getText().toString()));khCT.setCt8(Integer.parseInt(edt08.getText().toString()));
                    khCT.setCt9(Integer.parseInt(edt09.getText().toString()));khCT.setCt10(Integer.parseInt(edt10.getText().toString()));
                    khCT.setCt11(Integer.parseInt(edt11.getText().toString()));khCT.setCt12(Integer.parseInt(edt12.getText().toString()));
                    MainActivity.databaseSQLite.UpdateKeHoachCT(khCT);
                }
            }
        });

        imgMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /******show month picker dialog********/
                Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.month_picker);
                NumberPicker monthPicker = d.findViewById(R.id.monthPicker);
                NumberPicker yearPicker = d.findViewById(R.id.yearPicker);
                Button okButton = d.findViewById(R.id.okButton);


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
                        tvMonth.setText("Kế hoạch chi tiêu tháng "+selectedMonth+"-"+selectedYear);
                        d.dismiss();

                        String res=tvMonth.getText().toString().substring(24);
                        KeHoachCT tmp1 = getKhChiTieu(res);
                        KeHoachTN tmp2 = getKhThuNhap(res);

                        if(tmp1!=null){
                            edt01.setText(String.valueOf(tmp1.getCt1())); edt02.setText(String.valueOf(tmp1.getCt2()));
                            edt03.setText(String.valueOf(tmp1.getCt3())); edt04.setText(String.valueOf(tmp1.getCt4()));
                            edt05.setText(String.valueOf(tmp1.getCt5())); edt06.setText(String.valueOf(tmp1.getCt6()));
                            edt07.setText(String.valueOf(tmp1.getCt7())); edt08.setText(String.valueOf(tmp1.getCt8()));
                            edt09.setText(String.valueOf(tmp1.getCt9())); edt10.setText(String.valueOf(tmp1.getCt10()));
                            edt11.setText(String.valueOf(tmp1.getCt11())); edt12.setText(String.valueOf(tmp1.getCt12()));
                            sumCP.setText(String.format("%,d", tmp1.getSum()));
                        } else{
                            edt01.setText("0"); edt02.setText("0");
                            edt03.setText("0"); edt04.setText("0");
                            edt05.setText("0"); edt06.setText("0");
                            edt07.setText("0"); edt08.setText("0");
                            edt09.setText("0"); edt10.setText("0");
                            edt11.setText("0"); edt12.setText("0");
                            sumCP.setText("0");
                        }

                        if(tmp2!=null){
                            edt001.setText(String.valueOf(tmp2.getTn1())); edt002.setText(String.valueOf(tmp2.getTn2()));
                            edt003.setText(String.valueOf(tmp2.getTn3())); edt004.setText(String.valueOf(tmp2.getTn4()));
                            edt005.setText(String.valueOf(tmp2.getTn5())); edt006.setText(String.valueOf(tmp2.getTn6()));
                            sumTN.setText(String.format("%,d", tmp2.getSum()));
                        } else{
                            edt001.setText("0"); edt002.setText("0");
                            edt003.setText("0"); edt004.setText("0");
                            edt005.setText("0"); edt006.setText("0");
                            sumTN.setText("0");
                        }
                    }
                });
                d.show();
                /****************/


                layoutCP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        layoutCP.setBackgroundColor(Color.parseColor("#8692f7"));
                        layoutTN.setBackgroundColor(Color.WHITE);
                        cp.setVisibility(View.VISIBLE);
                        tn.setVisibility(View.GONE);
                        String month=tvMonth.getText().toString().substring(24);
                        KeHoachCT khCT=getKhChiTieu(month);


                        if(khCT!=null){
                            edt01.setText(String.valueOf(khCT.getCt1())); edt02.setText(String.valueOf(khCT.getCt2()));
                            edt03.setText(String.valueOf(khCT.getCt3())); edt04.setText(String.valueOf(khCT.getCt4()));
                            edt05.setText(String.valueOf(khCT.getCt5())); edt06.setText(String.valueOf(khCT.getCt6()));
                            edt07.setText(String.valueOf(khCT.getCt7())); edt08.setText(String.valueOf(khCT.getCt8()));
                            edt09.setText(String.valueOf(khCT.getCt9())); edt10.setText(String.valueOf(khCT.getCt10()));
                            edt11.setText(String.valueOf(khCT.getCt11())); edt12.setText(String.valueOf(khCT.getCt12()));
                            sumCP.setText(String.format("%,d", khCT.getSum()));
                        } else{
                            edt01.setText("0"); edt02.setText("0");
                            edt03.setText("0"); edt04.setText("0");
                            edt05.setText("0"); edt06.setText("0");
                            edt07.setText("0"); edt08.setText("0");
                            edt09.setText("0"); edt10.setText("0");
                            edt11.setText("0"); edt12.setText("0");
                            sumCP.setText("0");
                        }

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                if(khCT==null){
                                    String tk=MainActivity.account.getTk();
                                    int ct1 = Integer.parseInt(edt01.getText().toString()), ct2 = Integer.parseInt(edt02.getText().toString()),
                                            ct3 = Integer.parseInt(edt03.getText().toString()), ct4 = Integer.parseInt(edt04.getText().toString()),
                                            ct5 = Integer.parseInt(edt05.getText().toString()), ct6 = Integer.parseInt(edt06.getText().toString()),
                                            ct7 = Integer.parseInt(edt07.getText().toString()), ct8 = Integer.parseInt(edt08.getText().toString()),
                                            ct9 = Integer.parseInt(edt09.getText().toString()), ct10 = Integer.parseInt(edt10.getText().toString()),
                                            ct11 = Integer.parseInt(edt11.getText().toString()), ct12 = Integer.parseInt(edt12.getText().toString());
                                    KeHoachCT k=new KeHoachCT(tk, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, ct10, ct11, ct12,month);
                                    MainActivity.databaseSQLite.InsertKeHoachCT(k);
                                } else {
                                    khCT.setCt1(Integer.parseInt(edt01.getText().toString()));khCT.setCt2(Integer.parseInt(edt02.getText().toString()));
                                    khCT.setCt3(Integer.parseInt(edt03.getText().toString()));khCT.setCt4(Integer.parseInt(edt04.getText().toString()));
                                    khCT.setCt5(Integer.parseInt(edt05.getText().toString()));khCT.setCt6(Integer.parseInt(edt06.getText().toString()));
                                    khCT.setCt7(Integer.parseInt(edt07.getText().toString()));khCT.setCt8(Integer.parseInt(edt08.getText().toString()));
                                    khCT.setCt9(Integer.parseInt(edt09.getText().toString()));khCT.setCt10(Integer.parseInt(edt10.getText().toString()));
                                    khCT.setCt11(Integer.parseInt(edt11.getText().toString()));khCT.setCt12(Integer.parseInt(edt12.getText().toString()));
                                    MainActivity.databaseSQLite.UpdateKeHoachCT(khCT);
                                }
                            }
                        });
                    }
                });

                layoutTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutTN.setBackgroundColor(Color.parseColor("#8692f7"));
                        layoutCP.setBackgroundColor(Color.WHITE);
                        tn.setVisibility(View.VISIBLE);
                        cp.setVisibility(View.GONE);
                        String month=tvMonth.getText().toString().substring(24);
                        KeHoachTN khTN = getKhThuNhap(month);

                        if(khTN!=null){
                            edt001.setText(String.valueOf(khTN.getTn1())); edt002.setText(String.valueOf(khTN.getTn2()));
                            edt003.setText(String.valueOf(khTN.getTn3())); edt004.setText(String.valueOf(khTN.getTn4()));
                            edt005.setText(String.valueOf(khTN.getTn5())); edt006.setText(String.valueOf(khTN.getTn6()));
                            sumTN.setText(String.format("%,d", khTN.getSum()));
                        } else{
                            edt001.setText("0"); edt002.setText("0");
                            edt003.setText("0"); edt004.setText("0");
                            edt005.setText("0"); edt006.setText("0");
                            sumTN.setText("0");
                        }

                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "456", Toast.LENGTH_SHORT).show();
                                if(khTN==null){
                                    String tk=MainActivity.account.getTk();
                                    int tn1 = Integer.parseInt(edt001.getText().toString()), tn2 = Integer.parseInt(edt002.getText().toString()),
                                            tn3 = Integer.parseInt(edt003.getText().toString()), tn4 = Integer.parseInt(edt004.getText().toString()),
                                            tn5 = Integer.parseInt(edt005.getText().toString()), tn6 = Integer.parseInt(edt006.getText().toString());
                                    KeHoachTN k=new KeHoachTN(tk, tn1, tn2, tn3, tn4, tn5, tn6, month);
                                    MainActivity.databaseSQLite.InsertKeHoachTN(k);
                                } else {
                                    khTN.setTn1((Integer.parseInt(edt001.getText().toString())));khTN.setTn2((Integer.parseInt(edt002.getText().toString())));
                                    khTN.setTn3((Integer.parseInt(edt003.getText().toString())));khTN.setTn4((Integer.parseInt(edt004.getText().toString())));
                                    khTN.setTn5((Integer.parseInt(edt005.getText().toString())));khTN.setTn6((Integer.parseInt(edt006.getText().toString())));
                                    MainActivity.databaseSQLite.UpdateKeHoachTN(khTN);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                bitmap=getCircularBitmap(bitmap);
                imgAccount.setImageBitmap(bitmap);
                Toast.makeText(getActivity(), "Đổi ảnh thành công!!", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
            }
            BitmapDrawable bitmapDrawable= (BitmapDrawable) imgAccount.getDrawable();
            Bitmap bm=bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] newHinhAnh=byteArrayOutputStream.toByteArray();
            MainActivity.databaseSQLite.InsertIMG(MainActivity.account.getTk(),newHinhAnh);
            MainActivity.account.setHinhanh(newHinhAnh);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void getDataReminder(ListView lv, ArrayList<Reminder> arrReminder, ReminderAdapter adapter){
        String res=MainActivity.account.getTk();
        Cursor getData = MainActivity.databaseSQLite.GetData("SELECT * FROM reminder WHERE tk='"+res+"'");
        arrReminder = new ArrayList<>();
        while (getData.moveToNext()) {
            int id = getData.getInt(0);
            String tk = getData.getString(1);
            String time = getData.getString(2);
            String note = getData.getString(3);
            int status = getData.getInt(4);
            arrReminder.add(new Reminder(id,tk, time, note, status));
        }
        adapter = new ReminderAdapter(getActivity(), R.layout.line_reminder, arrReminder);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    //doi anh thanh hinh tron
    public Bitmap getCircularBitmap(Bitmap srcBitmap) {
        int diameter = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas;
        canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, diameter, diameter);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        return output;
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