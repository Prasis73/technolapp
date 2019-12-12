package com.pr.prasis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    public static String userid;
    CardView addtxn, report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        incomingintent();

        //typecasting
        addtxn = findViewById(R.id.add_txn_btn);
        report = findViewById(R.id.report_btn);

        addtxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), account.class));
                finish();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), shopReport.class));
                finish();
            }
        });
    }

    public void incomingintent() {
        Intent intent = getIntent();
        if (intent.getStringExtra("id") != null) {
            userid = intent.getStringExtra("id");

        }
    }
}
