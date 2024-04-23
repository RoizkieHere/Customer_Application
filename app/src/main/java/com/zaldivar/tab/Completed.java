package com.zaldivar.tab;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Map;

public class Completed extends Fragment {

    private Context context;
    private View rootView;
    private String url = "https://zaldivarservices.com/android_new/customer_app/completed/completed.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_completed, container, false);
        context = getContext();
        fetch_data();
        return rootView;
    }

    private void fetch_data() {
        LinearLayoutCompat container = rootView.findViewById(R.id.completed_container);


        SharedPreferences get_username = requireContext().getSharedPreferences("this_preferences", Context.MODE_PRIVATE);
        String user = get_username.getString("user", "");

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.isEmpty()){

                    String[] row = response.split("<br>");

                    for (String column : row) {
                        String[] data = column.split(";");

                        View newView = LayoutInflater.from(context).inflate(R.layout.info_completed, container, false);

                        TextView reference_number, order_date, quantity, price, fulfilled_date, pahenante;
                        reference_number = newView.findViewById(R.id.reference_number);
                        order_date = newView.findViewById(R.id.order_date);
                        quantity = newView.findViewById(R.id.quantity);
                        price = newView.findViewById(R.id. price);
                        fulfilled_date = newView.findViewById(R.id.fulfilled_date);
                        pahenante = newView.findViewById(R.id.pahenante);

                        reference_number.setText(data[0]);
                        order_date.setText(data[1]);
                        quantity.setText(data[2]);
                        price.setText(data[3]);
                        fulfilled_date.setText(data[4]);
                        pahenante.setText(data[5]);

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