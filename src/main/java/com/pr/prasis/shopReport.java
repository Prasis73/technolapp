package com.pr.prasis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class shopReport extends AppCompatActivity {
    String date,remarks,amount,txn,cname,cadd,cno,cid;
    ArrayList<String> Date=new ArrayList<String>();
    ArrayList<String> Remarks=new ArrayList<String>();
    ArrayList<String> Amount=new ArrayList<String>();
    ArrayList<String> Txn=new ArrayList<String>();
    ArrayList<String> Cname=new ArrayList<String>();
    ArrayList<String> Cadd=new ArrayList<String>();
    ArrayList<String> Cno=new ArrayList<String>();
    ArrayList<String> Cid=new ArrayList<String>();


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_report);
        new getInfo().execute();
    }

    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;



        @Override
        protected void onPreExecute() {
           progressDialog=ProgressDialog.show(shopReport.this,"","fetching report...",true);
            //   Toast.makeText(newsFeed.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://testprasis.000webhostapp.com/report.php";

        }

        @Override
        protected String doInBackground(String... args) {



            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                 String data_string = URLEncoder.encode("shop_id", "UTF-8") + "=" + URLEncoder.encode(Dashboard.userid, "UTF-8");
                  bufferedWriter.write(data_string);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuffer buffer = new StringBuffer();
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String data = stringBuilder.toString().trim();

                String json;

                InputStream stream = null;
                stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));

                int size = stream.available();
                byte[] buffer1 = new byte[size];
                stream.read(buffer1);
                stream.close();

                json = new String(buffer1, "UTF-8");
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("id") != null) {

                        date = jsonObject.getString("date");
                        remarks = jsonObject.getString("remark");
                        amount = jsonObject.getString("amount");
                        txn = jsonObject.getString("type");
                        cadd = jsonObject.getString("cadd");
                        cname = jsonObject.getString("cname");
                        cno = jsonObject.getString("cno");
                        cid = jsonObject.getString("id");



                        Date.add(date);
                        Remarks.add(remarks);
                        Amount.add(amount);
                        Txn.add(txn);
                        Cname.add(cname);
                        Cadd.add(cadd);
                        Cno.add(cno);
                        Cid.add(cid);


                    }
                }
                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
           progressDialog.dismiss();
            RecyclerView recyclerView = findViewById(R.id.recycler_report);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new adapterReport(Cid,Date,Remarks,Amount,Txn,Cname,Cadd,Cno));
        }
    }
}
