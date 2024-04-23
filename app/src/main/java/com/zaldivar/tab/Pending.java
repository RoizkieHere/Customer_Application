package com.zaldivar.tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Pending extends Fragment {

    private Context context;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pending, container, false);
        context = getContext();
        fetch_data();
        return rootView;
    }

    private void fetch_data() {
        LinearLayoutCompat container = rootView.findViewById(R.id.pending_container);

        String url = "https://zaldivarservices.com/android_new/customer_app/pending_api/pending.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        SharedPreferences get_username = requireContext().getSharedPreferences("this_preferences", Context.MODE_PRIVATE);
        String user = get_username.getString("user", "");


        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (!response.isEmpty()){

                    container.setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.no_pending).setVisibility(View.GONE);

                    String[] row = response.split("<br>");

                    for (String column : row) {
                        String[] data = column.split(";");

                        View newView = LayoutInflater.from(context).inflate(R.layout.info_pending, container, false);

                        TextView reference_number, order_date, quantity, price;
                        reference_number = newView.findViewById(R.id.reference_number);
                        order_date = newView.findViewById(R.id.order_date);
                        quantity = newView.findViewById(R.id.quantity);
                        price = newView.findViewById(R.id. price);

                        reference_number.setText(data[0]);
                        order_date.setText(data[1]);
                        quantity.setText(data[2]);
                        price.setText(data[3]);

                        container.addView(newView);
                    }
                } else {

                    container.setVisibility(View.GONE);
                    rootView.findViewById(R.id.no_pending).setVisibility(View.VISIBLE);


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
                params.put("username", user);
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
