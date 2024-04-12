package com.zaldivar.tab;

import android.content.Context;
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

public class Transit extends Fragment {

    private Context context;
    private View rootView;
    private String url = "https://zaldivarservices.com/android_new/customer_app/in_transit/in_transit.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_transit, container, false);
        context = getContext();
        fetch_data();
        return rootView;
    }

    private void fetch_data() {
        LinearLayoutCompat container = rootView.findViewById(R.id.transit_container);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String[] row = response.split("<br>");

                for (String column : row) {
                    String[] data = column.split(";");

                    View newView = LayoutInflater.from(context).inflate(R.layout.transit_table_row, container, false);

                    TextView weight = newView.findViewById(R.id.weight);
                    TextView date = newView.findViewById(R.id.date);
                    TextView unit = newView.findViewById(R.id.unit);
                    TextView pahenante = newView.findViewById(R.id.pahenante);
                    TextView status = newView.findViewById(R.id.status);

                    weight.setText(data[0]);
                    date.setText(data[1]);
                    unit.setText("Ton");
                    pahenante.setText(data[2]);
                    status.setText(data[3]);

                    container.addView(newView);
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
                params.put("user", "Abrera.123");
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