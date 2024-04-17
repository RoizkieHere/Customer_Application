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


        //For personal info:
        AppCompatButton next_button = personal_info.findViewById(R.id.next_button);
        EditText firstname, lastname, address, phone_number;
        firstname = personal_info.findViewById(R.id.firstname);
        lastname = personal_info.findViewById(R.id.lastname);
        address = personal_info.findViewById(R.id.address);
        phone_number = personal_info.findViewById(R.id.phone_num);

        String firstname_string = firstname.getText().toString();
        String lastname_string = lastname.getText().toString();
        String phone_string = phone_number.getText().toString();
        String address_string = address.getText().toString();

        //For login_credentials:
        AppCompatButton sign_up_button = login_credentials.findViewById(R.id.sign_up_button);
        EditText username, password, password_confirm;
        username = login_credentials.findViewById(R.id.username);
        password = login_credentials.findViewById(R.id.password);
        password_confirm = login_credentials.findViewById(R.id.password_confirm);
        TextView sign_up_err = login_credentials.findViewById(R.id.sign_up_err);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String confirmString = password_confirm.getText().toString();


        //Error message:
        TextView pe_error_msg = personal_info.findViewById(R.id.error_msg);

        container.addView(personal_info);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(firstname_string.isEmpty() || lastname_string.isEmpty() || phone_string.isEmpty() || address_string.isEmpty()){

                    pe_error_msg.setVisibility(View.VISIBLE);
                    pe_error_msg.setText("Please fill-out empty field/s.");

                    if(firstname_string.isEmpty()){
                        firstname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }

                    if(lastname_string.isEmpty()){
                        lastname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }

                    if(address_string.isEmpty()) {
                        address.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }

                    if(phone_string.isEmpty()) {
                        phone_number.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));

                    }
                } else {
                    pe_error_msg.setVisibility(View.GONE);
                    container.addView(login_credentials);
                }
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(usernameString.isEmpty() || passwordString.isEmpty() || confirmString.isEmpty()){

                    sign_up_err.setVisibility(View.VISIBLE);
                    sign_up_err.setText("Please fill out empty field/s.");

                    if (usernameString.isEmpty()){
                        username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }if (passwordString.isEmpty()){
                        password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }if (confirmString.isEmpty()){
                        password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    }

                    
                }







            }
        });







    }
}