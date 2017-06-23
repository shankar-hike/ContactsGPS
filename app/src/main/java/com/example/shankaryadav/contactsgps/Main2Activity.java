package com.example.shankaryadav.contactsgps;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {

    private TextView l;
    double lt = 1.0;
    double lo = 1.0;
    private LocationManager lm;
    private LocationListener ll;
    String mob = "9721551097";
    User U = new User();
    Contact C = new Contact();
    Location1 loc = new Location1();
    Gson gson = new Gson();
    String jsonInString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        l = (TextView) findViewById(R.id.tv);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);


        ll = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {

                lt = location.getLatitude();
                lo = location.getLongitude();

                Log.i(".........", Double.toString(lt));
                Log.i(".........", Double.toString(lo));

                l.setMovementMethod(new ScrollingMovementMethod());
                l.append("\nMy Location......   :   " + Double.toString(lt) + "    " + Double.toString(lo) + "\n\n\n");

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
                                Mobile[count] = strfun(pno);
                                count++;
                            }

                        }
                    }
                }
                cur.close();

                loc.setLatitude(lt);
                loc.setLongitude(lo);

                C.setMobile(mob);
                C.setLocation(loc);


                Contact [] cont = new Contact[count];

                for(int i = 0 ; i < count; i++) {
                    cont[i] = new Contact();
                    cont[i].setMobile(Mobile[i]);
                    cont[i].setLocation(loc);
                }

                U.setMe(C);
                U.setContacts(cont);

                jsonInString = gson.toJson(U);
                Log.i("resopnse...", jsonInString);


               final RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);

                String url1 = "http://172.16.3.38:8000/init";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1, jsonInString,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("resopnse...", response.toString());
                                l.setMovementMethod(new ScrollingMovementMethod());
                                l.append(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", "Error in POST Response..........");
                        l.append("Error in POST Response........");
                    }
                });
                Log.i("resopnse...", jsonObjectRequest.toString());
                requestQueue.add(jsonObjectRequest);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, 10);
                return;
            }
        }
        else {
            conf();
        }





    }
    public String strfun(String s)
    {
        String st = "";
        for(int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9')
                st = st + s.charAt(i);
        }
        st = st.substring(0,10);
        st = new StringBuilder(st).reverse().toString();

        return st;
    }
    public void onRequestPermissionResult(int requestCode, String[] permisiion , int[] grantResult) {
        switch(requestCode) {
            case 10:
                if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED)
                    conf();
                return;
        }
    }
    private void conf() {
        Log.i("fmh", "location leasing.........");
        l.setMovementMethod(new ScrollingMovementMethod());
        l.append("location Posting.........\n");
        lm.requestLocationUpdates("gps", 5000, 0, ll);

    }
}
