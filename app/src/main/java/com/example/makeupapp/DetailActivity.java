package com.example.makeupapp;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makeupapp.DAO.DataBase;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView id, brand, name, price, price_sign, product_link, website_link, description, colour_name;
    String id1, brand1, name1, price1, price_sign1, product_link1, website_link1, description1,
            colour_name1, image_link1;
    ImageView image_link;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DataBase dataBase;
    List<Favourite_class> arrayList;
    FavModelClass favouriteModel;
    MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favouriteModel = ViewModelProviders.of(DetailActivity.this).get(FavModelClass.class);

        image_link = findViewById(R.id.image_v);
        name = findViewById(R.id.name_tv);
        id = findViewById(R.id.id_tv);
        brand = findViewById(R.id.brand_tv);
        price_sign = findViewById(R.id.price_sign);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        product_link = findViewById(R.id.product_link);
        website_link = findViewById(R.id.website_link);
        colour_name = findViewById(R.id.colorInfo);
        materialFavoriteButton = findViewById(R.id.favButton);

        arrayList = new ArrayList<>();

        dataBase = Room.databaseBuilder(this, DataBase.class, "makeup")
                .allowMainThreadQueries()
                .build();

        name1 = getIntent().getStringExtra("name");
        brand1 = getIntent().getStringExtra("brand");
        id1 = getIntent().getStringExtra("id");
        price_sign1 = getIntent().getStringExtra("priceSign");
        price1 = getIntent().getStringExtra("price");
        description1 = getIntent().getStringExtra("description");
        product_link1 = getIntent().getStringExtra("productLink");
        website_link1 = getIntent().getStringExtra("webLink");
        image_link1 = getIntent().getStringExtra("imageLink");
        colour_name1 = getIntent().getStringExtra("colorName");

        dataBase = Room.databaseBuilder(this, DataBase.class, "makeup")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        checkmovie();

        materialFavoriteButton.setOnFavoriteChangeListener
                (new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {

                            Favourite_class fav_items = new Favourite_class();
                            fav_items.setName(name1);
                            fav_items.setBrand(brand1);
                            fav_items.setPrice(price1);
                            fav_items.setPrice_sign(price_sign1);
                            fav_items.setProduct_link(product_link1);
                            fav_items.setWebsite_link(website_link1);
                            fav_items.setDescription(description1);
                            fav_items.setColour_name(colour_name1);
                            fav_items.setImage_link(image_link1);
                            fav_items.setId(Integer.parseInt(id1));
                            favouriteModel.insert(fav_items);
                        } else {

                            Favourite_class fav_items = new Favourite_class();
                            fav_items.setName(name1);
                            fav_items.setBrand(brand1);
                            fav_items.setPrice(price1);
                            fav_items.setPrice_sign(price_sign1);
                            fav_items.setProduct_link(product_link1);
                            fav_items.setWebsite_link(website_link1);
                            fav_items.setDescription(description1);
                            fav_items.setColour_name(colour_name1);
                            fav_items.setImage_link(image_link1);
                            fav_items.setId(Integer.parseInt(id1));
                            favouriteModel.delete(fav_items);
                        }

                    }
                });

        name.setText(name1);
        brand.setText(brand1);
        price.setText(price1);
        price_sign.setText(price_sign1);
        product_link.setText(product_link1);
        website_link.setText(website_link1);
        description.setText(description1);
        colour_name.setText(colour_name1);
        id.setText(id1);
        Picasso.with(this).load(image_link1).into(image_link);

        product_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = product_link.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        website_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = website_link.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    private void checkmovie() {

        Favourite_class selectedID = favouriteModel.searchForItem(id1);
        if (selectedID != null) {
            materialFavoriteButton.setFavorite(true);
        } else {
            materialFavoriteButton.setFavorite(false);
            }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        switch (id)
        {
            case R.id.action_widget:
                StringBuilder builder = new StringBuilder();
                    builder.append("Name:"+name1+"\n"+"Brand:"+brand1+"\n");

                sharedPreferences=getSharedPreferences("sharedPreference",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.clear();
                editor.putString("name",builder.toString());
                editor.putString("image",image_link1);

                editor.commit();

                Intent i = new Intent(DetailActivity.this,AppWidget.class);
                i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                int identity[]= AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),AppWidget.class));
                i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,identity);
                sendBroadcast(i);

        }
        return super.onOptionsItemSelected(item);

    }
}
