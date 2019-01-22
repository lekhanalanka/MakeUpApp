package com.example.makeupapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MakeUpHolder>
{
   Context context;
   ArrayList<ModelClass> modelClassArrayList;

    public Adapter(MainActivity mainActivity, ArrayList<ModelClass> modelClasses) {
        this.context=mainActivity;
        this.modelClassArrayList=modelClasses;
    }

    @NonNull
    @Override
    public Adapter.MakeUpHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,viewGroup,false);
        return new MakeUpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MakeUpHolder makeUpHolder, int i) {

        Picasso.with(context).load(modelClassArrayList.get(i).getImage_link()).placeholder(R.drawable.loading).into(makeUpHolder.imageView);
        makeUpHolder.textView.setText(modelClassArrayList.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class MakeUpHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;


        public MakeUpHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    if (position!=-1)
                    {
                        Intent intent = new Intent(context,DetailActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("id",modelClassArrayList.get(position).getId());
                        intent.putExtra("brand",modelClassArrayList.get(position).getBrand());
                        intent.putExtra("name",modelClassArrayList.get(position).getName());
                        intent.putExtra("price",modelClassArrayList.get(position).getPrice());
                        intent.putExtra("priceSign",modelClassArrayList.get(position).getPrice_sign());
                        intent.putExtra("imageLink",modelClassArrayList.get(position).getImage_link());
                        intent.putExtra("webLink",modelClassArrayList.get(position).getWebsite_link());
                        intent.putExtra("productLink",modelClassArrayList.get(position).getProduct_link());
                        intent.putExtra("description",modelClassArrayList.get(position).getDescription());
                        intent.putExtra("category",modelClassArrayList.get(position).getCategory());
                        intent.putExtra("colorName",modelClassArrayList.get(position).getColour_name());


                        context.startActivity(intent);

                    }
                }
            });
        }
    }
}
