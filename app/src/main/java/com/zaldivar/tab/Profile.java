package com.zaldivar.tab;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

public class Profile extends AppCompatActivity {

    EditText contact_num, firstname, lastname;

    String what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        contact_num = findViewById(R.id.profile_contact);
        firstname = findViewById(R.id.profile_firstname);
        lastname = findViewById(R.id.profile_lastname);
        Button update = findViewById(R.id.update_button);

        SharedPreferences get_it = getSharedPreferences("this_preferences", MODE_PRIVATE);

        TextView user_named = findViewById(R.id.username_text_profile);
        user_named.setText(get_it.getString("user", ""));

        what = "0";
        update_info();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                what = "1";
                update_info();
            }
        });


    }

    private void update_info(){

        SharedPreferences get = getSharedPreferences("this_preferences", MODE_PRIVATE);
        String user = get.getString("user", "");


        String url;

        if (what.equals("0")){
            url = "https://zaldivarservices.com/android_new/customer_app/account/fetch.php";
        } else {
            url = "https://zaldivarservices.com/android_new/customer_app/account/update_profile.php";
        }


        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Changed")){
                    dialog();

                } else {
                    String[] the_response = response.split(";");
                    contact_num.setText(the_response[1]);
                    firstname.setText(the_response[0]);
                    lastname.setText(the_response[2]);

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("user", user);
                params.put("contact_number", contact_num.getText().toString());
                params.put("firstname", firstname.getText().toString());
                params.put("lastname", lastname.getText().toString());

                return params;


            }
        };
        queue.add(sr);

    }

    private void dialog(){

        Dialog success = new Dialog(Profile.this);

        success.setContentView(R.layout.profile_success_dialog);
        success.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        success.setCancelable(false);
        success.getWindow().getAttributes().windowAnimations = R.style.animation;

        TextView message = success.findViewById(R.id.message);
        Button button = success.findViewById(R.id.button);


        SharedPreferences sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Change username in a shared preference...
        editor.putString("name", firstname.getText().toString().concat(" ").concat(lastname.getText().toString()));
        editor.apply();

        message.setText("Your profile information has been successfully changed.");
        button.setText("Home");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success.dismiss();
                Intent home = new Intent(Profile.this, MainActivity.class);
                startActivity(home);
            }
        });

        success.show();

    }
}