package com.example.shankaryadav.contactsgps;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.StringTokenizer;

public class Main3Activity extends AppCompatActivity {

    private TextView l;
    private EditText et;
    private Button b;
    double lt = 0.0;
    double lo = 0.0;
    String mob = "9721551097";

    Gson gson = new Gson();
    String jsonInString;
    String nmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        l = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.ET);
        b = (Button)findViewById(R.id.sb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmob = et.getText().toString();
                et.setText("");

                Context context = getApplicationContext();
                CharSequence text = nmob;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                ContentResolver con = getContentResolver();
                Cursor cur = con.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                int count = 0;
                int tt = cur.getCount();
                String s = "";
                String [] nam = new String[tt];
                String [] Mobile = new String[tt];
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        int pn = Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                        String ss = "";
                        if (pn > 0) {
                            String pno = "";
                            Cursor cur2 = con.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            while (cur2.moveToNext()) {
                                pno = cur2.getString(cur2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                StringTokenizer multiTokenizer = new StringTokenizer(name, " ");
                                s = multiTokenizer.nextToken();
                                ss = Integer.toString(count + 1) + ".  " + s + "        " + pno;
                            }
                            cur2.close();
                            if(pno.length() >= 10) {
                                nam[count] = s;
                                Mobile[count] = pno.substring(pno.length()-10, pno.length());
                                count++;
                            }

                        }
                    }
                }
                cur.close();


                final RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);
                Contact cont1 = new Contact();
                Location1 loc2 = new Location1();

                loc2.setLatitude(lt);
                loc2.setLongitude(lo);

                cont1.setMobile(nmob);
                cont1.setLocation(loc2);


                jsonInString = gson.toJson(cont1);

                String url1 = "http://172.16.3.38:8000/updateLocation";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1, jsonInString,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("resopnse...", response.toString());
                                l.setText(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", "Error in Json..........");
                        l.setText("Error......");
                    }
                });

                requestQueue.add(jsonObjectRequest);


            }
        });


    }
}
