package com.zaldivar.tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Cancelled extends Fragment {

    private Context context;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cancelled, container, false);
        context = getContext();
        fetch_data();
        return rootView;
    }

    private void fetch_data() {

        String url = "https://zaldivarservices.com/android_new/customer_app/Cancelled/cancelled.php";

        LinearLayoutCompat container = rootView.findViewById(R.id.cancelled_container);

        SharedPreferences get_username = requireContext().getSharedPreferences("this_preferences", Context.MODE_PRIVATE);
        String user = get_username.getString("user", "");

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()){

                    String[] row = response.split("<br>");

                    for (String column : row) {
                        String[] data = column.split(";");

                        View newView = LayoutInflater.from(context).inflate(R.layout.info_cancelled, container, false);

                        TextView ref_number, date, quantity, status;
                        ref_number = newView.findViewById(R.id.reference);
                        date = newView.findViewById(R.id.date);
                        quantity = newView.findViewById(R.id.quantity);
                        status = newView.findViewById(R.id.status);

                        ref_number.setText(data[0]);
                        date.setText(data[1]);

                        String[] quantity_split = data[2].split("\\.");
                        double decimal_con = Double.parseDouble(data[2]);
                        DecimalFormat df = new DecimalFormat("#,###");
                        DecimalFormat df_1 = new DecimalFormat("#,###.00");

                        if(quantity_split[1].equals("00")){
                            if (Integer.parseInt(quantity_split[0]) > 1){
                                quantity.setText(df.format(decimal_con).concat(" Tons"));
                            } else {
                                quantity.setText(df.format(decimal_con).concat(" Ton"));
                            }
                        } else {
                            if (Integer.parseInt(quantity_split[1]) > 0){
                                quantity.setText(df_1.format(decimal_con).concat(" Tons"));
                            } else {
                                quantity.setText(df_1.format(decimal_con).concat(" Ton"));
                            }
                        }

                        status.setText(data[3]);

                        if (data[3].equals("Cancelled")){
                            status.setTextColor(Color.parseColor("#F0923848"));
                        } else {
                            status.setTextColor(Color.parseColor("#F0958504"));
                        }

                        container.addView(newView);
                    }
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
                params.put("user", user);
                return params;
            }
        };
        queue.add(sr);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Release resources here if needed
    }
}