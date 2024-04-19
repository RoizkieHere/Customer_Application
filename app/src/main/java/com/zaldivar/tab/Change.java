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
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        SharedPreferences fetch_preference = getSharedPreferences("this_preferences", Context.MODE_PRIVATE);
        username = fetch_preference.getString("username", "");


        error_msg = findViewById(R.id.error_message);

        AppCompatButton enter = findViewById(R.id.enter);
        EditText new_password = findViewById(R.id.new_password);
        EditText confirm_password = findViewById(R.id.confirm_password);

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

    }

    private void change_password(String new_password){

        String url = "https://zaldivarservices.com/android_new/customer_app/account/update_pass.php";

        RequestQueue queue = Volley.newRequestQueue(Change.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")){
                    Intent intent = new Intent(Change.this, PasswordChanged.class);
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
                params.put("username", username);
                return params;
            }
        };
        queue.add(sr);
    }
}