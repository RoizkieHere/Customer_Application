package com.zaldivar.tab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Login extends Fragment {

    View rootView;
    TextView error_msg;
    Context context;
    Button login, sign_up;
    EditText username, password;
    String url = "Roi";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pending, container, false);
        context = getContext();

        error_msg.setVisibility(View.GONE);

        login = rootView.findViewById(R.id.login_button);
        sign_up = rootView.findViewById(R.id.sign_up);
        username = rootView.findViewById(R.id.username);
        password = rootView.findViewById(R.id.password);

        error_msg = rootView.findViewById(R.id.error_message);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_input();

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sign_up = new Intent(context, Signup.class);
                startActivity(sign_up);

            }
        });

        return rootView;
    }

    private void validate_input(){

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("Reject")){

                    error_msg.setVisibility(View.VISIBLE);

                } else if (response.equals("Not Existing")){

                    error_msg.setVisibility(View.VISIBLE);
                    error_msg.setText("Not existing. Please try again or Sign-up");

                } else {
                    error_msg.setVisibility(View.GONE);

                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("username", usernameString);

                    Intent login =  new Intent(context, MainActivity.class);
                    startActivity(login);

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
                params.put("username", usernameString);
                params.put("password", passwordString);
                return params;
            }
        };
        queue.add(sr);







    }










}