package com.example.quanlychitieu;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
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
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
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

    private Button btnLogout,btnDoiMK,btnDoiIMG,btnLoiNhac,btnVote;
    private TextView tvTK;
    private ImageView imgAccount;

    private ReminderAdapter adapter;


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

    private void btnVoteClick(){
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

    private void btnLogoutClick(){
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

        String res=MainActivity.account.getTk();
        Cursor getData = MainActivity.databaseSQLite.GetData("SELECT * FROM reminder WHERE tk='"+res+"'");
        ArrayList<Reminder> arrReminder = new ArrayList<>();
        while (getData.moveToNext()) {
            int id = getData.getInt(0);
            String tk = getData.getString(1);
            String time = getData.getString(2);
            String note = getData.getString(3);
            int status = getData.getInt(4);
            arrReminder.add(new Reminder(id,tk, time, note, status));
        }
        Collections.sort(HomeFragment.arrayListMoney);
        adapter = new ReminderAdapter(getActivity(), R.layout.line_reminder, arrReminder);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    private void load() {
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

}