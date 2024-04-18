package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
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

public class Otp extends AppCompatActivity {

    String email_from_act, otp_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Intent intent = getIntent();
        email_from_act = intent.getStringExtra("email");


        //Generate random pin:
        int random_num = 100000  + (int)(Math.random() * ((999999 - 100000) + 1));
        otp_string = Integer.toString(random_num);

        //All about edittext
        EditText otp_editText = findViewById(R.id.otp);
        String otp_string_editText = otp_editText.getText().toString();
        send_otp(otp_string_editText);

        AppCompatTextView error = findViewById(R.id.error_message);
        AppCompatButton enter = findViewById(R.id.enter);


        enter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (otp_string.equals(otp_string_editText)){
                    error.setVisibility(View.GONE);
                    otp_editText.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    Intent to_form = new Intent(Otp.this, SignupForm.class);
                    startActivity(to_form);
                } else {
                    otp_editText.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Invalid input!");
                }
            }
        });


    }

    private void send_otp(String otp) {

        String url = "https://zaldivarservices.com/android_new/customer_app/account/send_otp.php";

        RequestQueue queue = Volley.newRequestQueue(Otp.this);
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
                params.put("receiver", email_from_act);
                params.put("otp", otp_string);
                return params;
            }
        };
        queue.add(sr);

    }
}