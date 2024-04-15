package com.zaldivar.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Signup_Activity extends AppCompatActivity {

    String url = "https://zaldivarservices.com/android_new/customer_app/account/send_otp.php";

    EditText email_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        AppCompatButton sign_up = findViewById(R.id.sign_up);
        email_add = findViewById(R.id.email_address);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int random_num = 100000  + (int)(Math.random() * ((999999 - 100000) + 1));

                String otp = Integer.toString(random_num);

                Intent to_otp = new Intent(Signup_Activity.this, Otp.class);
                startActivity(to_otp);

                send_otp(otp);
            }
        });

    }

    private void send_otp(String otp) {

        String emailString = email_add.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(Signup_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


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
                params.put("receiver", emailString);
                params.put("otp", otp);
                return params;
            }
        };
        queue.add(sr);

    }
}