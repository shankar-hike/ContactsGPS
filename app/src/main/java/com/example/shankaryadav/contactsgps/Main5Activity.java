package com.example.shankaryadav.contactsgps;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.StringTokenizer;

public class Main5Activity extends AppCompatActivity {
    private ListView l;
    double lt = 0.0;
    double lo = 0.0;

    //lv = (ListView) findViewById(R.id.t2);
    //Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
    //Network network = new BasicNetwork(new HurlStack());
    //requestQueue = new RequestQueue(cache, network);
    //requestQueue.start();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        l = (ListView)findViewById(R.id.l);
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
        String [] sss = new String[count];

        for(int i =0 ; i < count; i++) {
           sss[i] = Integer.toString(i + 1) + ".  " + nam[i] + "      " + Mobile[i];
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sss);

        l.setAdapter(adapter1);

        Log.i("jghv", "Working....");
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
}
