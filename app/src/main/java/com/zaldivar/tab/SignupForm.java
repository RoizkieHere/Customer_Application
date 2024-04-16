package com.zaldivar.tab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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


        //For personal info:
        EditText firstname, lastname, address, phone_number;
        firstname = personal_info.findViewById(R.id.firstname);
        lastname = personal_info.findViewById(R.id.lastname);
        address = personal_info.findViewById(R.id.address);
        phone_number = personal_info.findViewById(R.id.phone_num);

        container.addView(personal_info);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(firstname.getText().toString().isEmpty()){
                    firstname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                }

                if(lastname.getText().toString().isEmpty()){
                    lastname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                }

                if(address.getText().toString().isEmpty()) {
                    address.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                }

                if(phone_number.getText().toString().isEmpty()) {
                    phone_number.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));

                }

            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });







    }
}