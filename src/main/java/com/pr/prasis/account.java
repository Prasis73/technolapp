package com.pr.prasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class account extends AppCompatActivity {
    TextView date;
    EditText amount, remarks,address,cname,cno;
    Button save;
    RadioGroup radioGroup;
    String checkedText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final String currDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        date = findViewById(R.id.currDate);
        amount = findViewById(R.id.amount);
        remarks = findViewById(R.id.remarks);
        save = findViewById(R.id.saveaccount);
        radioGroup = findViewById(R.id.radiogroup);
        address = findViewById(R.id.cadd);
        cname = findViewById(R.id.cname);
        cno= findViewById(R.id.cno);


        date.setText(currDate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                checkedText = radioButton.getText().toString();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amt = amount.getText().toString();
                String rem = remarks.getText().toString();
                String cnam = cname.getText().toString();
                String cad = address.getText().toString();
                String no = cno.getText().toString();

                new saveaccount().execute(checkedText, amt, rem,cnam , cad, no, currDate );

            }
        });
    }



    public class saveaccount extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url = "http://testprasis.000webhostapp.com/add_txn.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String type, amount, remarks, currdate, cname,cadd,cno;
            type = params[0];
            amount = params[1];
            remarks = params[2];
            cname = params[3];
            cadd = params[4];
            cno = params[5];
            currdate = params[6];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&" +
                        URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8") + "&" +
                        URLEncoder.encode("cname", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8") + "&" +
                        URLEncoder.encode("cadd", "UTF-8") + "=" + URLEncoder.encode(cadd, "UTF-8") + "&" +
                        URLEncoder.encode("cno", "UTF-8") + "=" + URLEncoder.encode(cno, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(currdate, "UTF-8") + "&" +
                        URLEncoder.encode("shopid", "UTF-8") + "=" + URLEncoder.encode(Dashboard.userid, "UTF-8") + "&" +
                        URLEncoder.encode("remarks", "UTF-8") + "=" + URLEncoder.encode(remarks, "UTF-8");
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

                return "Transaction added Succesfully";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
        Toast.makeText(account.this, s, Toast.LENGTH_SHORT).show();
        finish();


        }
    }
}
