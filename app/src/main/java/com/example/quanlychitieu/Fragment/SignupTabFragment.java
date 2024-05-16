package com.example.quanlychitieu.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quanlychitieu.R;

public class SignupTabFragment extends Fragment {

    public interface OnButtonClickListener{


        void onButtonSignupClick(String tk, String mk, String check);
    }

    public OnButtonClickListener btnSignupClick;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Kiểm tra xem Activity chứa Fragment có implement interface không
        if (context instanceof OnButtonClickListener) {
            btnSignupClick = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnButtonClickListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_signup_tab, container, false);

        Button btn=view.findViewById(R.id.signup_button);
        EditText edt1=view.findViewById(R.id.signup_tk);
        EditText edt2=view.findViewById(R.id.signup_password);
        EditText edt3=view.findViewById(R.id.signup_confirm);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk= edt1.getText().toString();
                String mk= edt2.getText().toString();
                String check=edt3.getText().toString();
                edt2.setText("");
                edt3.setText("");

                btnSignupClick.onButtonSignupClick(tk,mk,check);
            }
        });

        return  view;
    }
}