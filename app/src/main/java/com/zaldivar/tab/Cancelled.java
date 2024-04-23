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

                if (!response.isEmpty()) {

                    String[] row = response.split("<br>");

                    for (String column : row) {
                        String[] data = column.split(";");
                        View newView = LayoutInflater.from(context).inflate(R.layout.info_cancelled, container, false);

                        TextView reference_number, order_date, quantity, cancel_date;
                        reference_number = newView.findViewById(R.id.reference_number);
                        order_date = newView.findViewById(R.id.order_date);
                        quantity = newView.findViewById(R.id.quantity);
                        cancel_date = newView.findViewById(R.id. cancel_date);

                        reference_number.setText(data[0]);
                        order_date.setText(data[1]);
                        quantity.setText(data[2]);
                        cancel_date.setText(data[3]);


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