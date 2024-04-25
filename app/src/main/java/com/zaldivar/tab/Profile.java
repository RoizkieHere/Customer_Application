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

    EditText new_username, contact_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        new_username = findViewById(R.id.profile_username);
        contact_num = findViewById(R.id.profile_contact);
        TextView delete_account = findViewById(R.id.delete_acc);
        Button update = findViewById(R.id.update_button);

        update_info(0);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_info(1);
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_info(2);
            }
        });

    }

    private void update_info(int choose){

        SharedPreferences get = getSharedPreferences("this_preferences", MODE_PRIVATE);
        String user = get.getString("user", "");


        String url;

        if (choose == 0){
            url = "https://zaldivarservices.com/android_new/customer_app/account/fetch.php";
        } else if (choose == 1){
            url = "https://zaldivarservices.com/android_new/customer_app/account/update_profile.php";
        } else {
            url = "";
        }


        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Changed")){

                    dialog(0);

                } else if (response.equals("Deleted")){

                    dialog(1);

                } else {
                    String[] the_response = response.split(";");
                    new_username.setText(the_response[0]);
                    contact_num.setText(the_response[1]);
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

                if (choose == 0 || choose == 2){
                    params.put("username", user);
                    return params;
                } else{
                    params.put("username", user);
                    params.put("new_username", new_username.getText().toString());
                    params.put("contact_number", contact_num.getText().toString());
                    return params;
                }


            }
        };
        queue.add(sr);

    }

    private void dialog(int what){

        Dialog success = new Dialog(Profile.this);

        success.setContentView(R.layout.profile_success_dialog);
        success.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        success.setCancelable(false);
        success.getWindow().getAttributes().windowAnimations = R.style.animation;

        TextView message = success.findViewById(R.id.error_message);
        Button button = success.findViewById(R.id.button);

        if (what == 0){

            Toast.makeText(Profile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //Change username in a shared preference...
            editor.putString("user", new_username.getText().toString());
            editor.apply();

            message.setText("Your profile information has been successfully changed.");
            button.setText("Home");

            success.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    success.dismiss();
                    Intent home = new Intent(Profile.this, MainActivity.class);
                    startActivity(home);
                }
            });

        } else {
            message.setText("Your account has been successfully deleted.");
            button.setText("Login");

            success.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);
                    // Clear values of sharedpreferences:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    success.dismiss();

                    Intent login = new Intent(Profile.this, Login_Activity.class);
                    startActivity(login);

                }
            });

        }


    }
}