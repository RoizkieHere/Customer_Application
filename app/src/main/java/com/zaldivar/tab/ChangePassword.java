package com.zaldivar.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends Fragment {
    Context context;
    View rootView;
    String url = "Roi";

    TextView error_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        context = getContext();

        error_msg = rootView.findViewById(R.id.error_message);

        AppCompatButton enter = rootView.findViewById(R.id.enter);
        EditText new_password = rootView.findViewById(R.id.new_password);
        EditText confirm_password = rootView.findViewById(R.id.confirm_password);

        String new_passwordSt = new_password.getText().toString();
        String confirm_passwordSt = confirm_password.getText().toString();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new_passwordSt.equals(confirm_passwordSt)){
                    change_password(new_passwordSt);
                } else {
                    error_msg.setVisibility(View.VISIBLE);
                }

            }
        });

        return  rootView;
    }

    private void change_password(String new_password){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Good")){
                    Intent intent = new Intent(context, ConfirmChanged.class);
                    startActivity(intent);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("new_password", new_password);
                return params;
            }
        };
        queue.add(sr);


    }
}