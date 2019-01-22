package com.example.makeupapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Fav_Holder> {

    List<Favourite_class> arrayList;
    Context context;


    public FavouriteAdapter(MainActivity mainActivity, List<Favourite_class> arrayList) {

        this.context = mainActivity;
        this.arrayList = arrayList;
    }


    @Override
    public FavouriteAdapter.Fav_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Fav_Holder(LayoutInflater.from(context).inflate(R.layout.items,parent,false));
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.Fav_Holder holder, int position) {

        Picasso.with(context).load(arrayList.get(position).getImage_link()).placeholder(R.drawable.loading).into(holder.imageView);
        holder.textView.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Fav_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Fav_Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != -1) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("id", Integer.toString(arrayList.get(position).getId()));
                        intent.putExtra("brand", arrayList.get(position).getBrand());
                        intent.putExtra("name", arrayList.get(position).getName());
                        intent.putExtra("price", arrayList.get(position).getPrice());
                        intent.putExtra("priceSign", arrayList.get(position).getPrice_sign());
                        intent.putExtra("imageLink", arrayList.get(position).getImage_link());
                        intent.putExtra("webLink", arrayList.get(position).getWebsite_link());
                        intent.putExtra("productLink", arrayList.get(position).getProduct_link());
                        intent.putExtra("description", arrayList.get(position).getDescription());
                        intent.putExtra("category", arrayList.get(position).getCategory());
                        intent.putExtra("colorName", arrayList.get(position).getColour_name());

                        context.startActivity(intent);

                    }
                }

            });
        }

    }
}
