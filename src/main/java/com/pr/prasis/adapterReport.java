package com.pr.prasis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterReport extends RecyclerView.Adapter<adapterReport.ViewHolder> {

    private ArrayList<String> date;
    private ArrayList<String> remarks;
    private ArrayList<String> amount;
    private ArrayList<String> txn;
    private ArrayList<String> cname;
    private ArrayList<String> cadd;
    private ArrayList<String> cid;
    private ArrayList<String> cno;


    public adapterReport(ArrayList<String> cid,ArrayList<String> date, ArrayList<String> remarks, ArrayList<String> amount, ArrayList<String> txn, ArrayList<String> cname, ArrayList<String> cadd, ArrayList<String> cno) {
        this.date = date;
        this.cid = cid;
        this.remarks = remarks;
        this.amount = amount;
        this.txn = txn;
        this.cname = cname;
        this.cadd = cadd;
        this.cno = cno;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reportnew_layout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Context context = holder.itemView.getContext();
        final String Date = date.get(position);
        final String Remark = remarks.get(position);
        final String Amount = amount.get(position);
        final String Cid = cid.get(position);
        final String Txn = txn.get(position);
        final String Cname = cname.get(position);
        final String Cadd = cadd.get(position);
        final String Cno = cno.get(position);


        holder.date.setText(Date);
        holder.remark.setText(Remark);
        holder.cname.setText(Cname);
        holder.cadd.setText(Cadd);
        holder.cno.setText(Cno);

        if (Txn != null && Txn.equals("withdrawn")) {
            holder.amount.setTextColor(Color.parseColor("#ff0000"));
            holder.amount.setText(Amount);
        }
        if (Txn != null && Txn.equals("Deposited")){
            holder.amount.setTextColor(Color.parseColor("#00ff00"));
            holder.amount.setText(Amount);
        }

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new deleterecord().execute(Cid);



            }
        });

    }

    @Override
    public int getItemCount() {
        return date.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView remark;
        TextView amount;
        ImageView remove;

        TextView cname;
        TextView cadd;
        TextView cno;


        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.shop_linear_layout);
            date = itemView.findViewById(R.id.rcdate);
            remark = itemView.findViewById(R.id.rcremark);
            amount = itemView.findViewById(R.id.riamount);
            cname = itemView.findViewById(R.id.rcname);
            cadd = itemView.findViewById(R.id.rcadd);
            cno = itemView.findViewById(R.id.rcno);
            remove=itemView.findViewById(R.id.remove);


        }
    }
}
