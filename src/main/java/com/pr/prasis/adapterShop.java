package com.pr.prasis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterShop extends RecyclerView.Adapter<adapterShop.ViewHolder> {

    private ArrayList<String> name;
    private ArrayList<String> id;


    public adapterShop(ArrayList<String> name,ArrayList<String> id) {
        this.name = name;
        this.id = id;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shoplist_layout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        final String Name = name.get(position);
        final String Id = id.get(position);


        holder.name.setText(Name);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Dashboard.class);

                intent.putExtra("id", Id);

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;


        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.shop_linear_layout);
            name = itemView.findViewById(R.id.shopname);


        }
    }
}
