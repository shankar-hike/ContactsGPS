package com.example.shankaryadav.contactsgps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Main4Activity extends AppCompatActivity {

    private TextView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        l = (TextView) findViewById(R.id.tv);

        String url1 ="http://172.16.3.38:8000/locate/9721551097";
        final RequestQueue requestQueue = Volley.newRequestQueue(Main4Activity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("fk", "wdfewd");
                        l.setText("working.." + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fdv", "Error in Locating");
                l.setText("Error in Locating .......");
            }
        });
        requestQueue.add(stringRequest);

    }
}
