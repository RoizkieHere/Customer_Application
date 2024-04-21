package com.zaldivar.tab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Change extends AppCompatActivity {


    TextView error_msg;
    EditText new_password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);


        error_msg = findViewById(R.id.error_message);

        AppCompatButton enter = findViewById(R.id.enter);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new_password.getText().toString().equals(confirm_password.getText().toString())){
                    change_password();
                } else {
                    error_msg.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void change_password(){

        SharedPreferences get = getSharedPreferences("this_preferences", MODE_PRIVATE);
        String user = get.getString("user", "");

        String url = "https://zaldivarservices.com/android_new/customer_app/account/update_pass.php";

        Intent intent = getIntent();
        String this_username = intent.getStringExtra("username");

        RequestQueue queue = Volley.newRequestQueue(Change.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")){
                    Intent intent = new Intent(Change.this, PasswordChanged.class);
                    intent.putExtra("from", "0");
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
                params.put("password", new_password.getText().toString());
                params.put("username", user);
                return params;
            }
        };
        queue.add(sr);
    }
}