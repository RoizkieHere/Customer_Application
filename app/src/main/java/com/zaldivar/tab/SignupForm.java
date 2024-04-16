package com.zaldivar.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SignupForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        String[] personal;
        String[] login_credential;


        LinearLayoutCompat container = findViewById(R.id.container);

        View personal_info = LayoutInflater.from(SignupForm.this).inflate(R.layout.personal_information, container, false);
        View login_credentials = LayoutInflater.from(SignupForm.this).inflate(R.layout.login_credentials, container, false);



        AppCompatButton next_button = personal_info.findViewById(R.id.next_button);
        AppCompatButton sign_in_button = login_credentials.findViewById(R.id.sign_in_button);

        container.addView(personal_info);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(personal_info);
                container.addView(login_credentials);
            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hi

            }
        });







    }
}