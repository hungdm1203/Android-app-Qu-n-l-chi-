package com.example.quanlychitieu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginTabFragment extends Fragment {

    public interface OnButtonClickListener{


        void onButtonLoginClick(String tk, String mk);
    }

    public OnButtonClickListener btnLoginClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Kiểm tra xem Activity chứa Fragment có implement interface không
        if (context instanceof OnButtonClickListener) {
            btnLoginClick = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnButtonClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_login_tab, container, false);

        Button btn=view.findViewById(R.id.login_button);
        EditText edt1=view.findViewById(R.id.login_tk);
        EditText edt2=view.findViewById(R.id.login_password);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk= edt1.getText().toString();
                String mk= edt2.getText().toString();
                edt2.setText("");
                btnLoginClick.onButtonLoginClick(tk,mk);
            }
        });

        return  view;
    }



}