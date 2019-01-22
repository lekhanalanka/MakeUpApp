package com.example.makeupapp;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.makeupapp.DAO.DataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String> {

    private FirebaseAuth auth;
    RecyclerView recyclerView;
    ArrayList<ModelClass> modelClasses;
    GridLayoutManager gridLayoutManager;
    ProgressDialog progressDialog;
    DataBase database;
    ViewModel viewModel;
    List<Favourite_class> arrayList;
    FavModelClass favouriteModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String ITEMS ="products";
    public static final String FAVOURITE ="favourite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        favouriteModel = ViewModelProviders.of(this).get(FavModelClass.class);

        viewModel=ViewModelProviders.of(this).get(com.example.makeupapp.ViewModel.class);

        database = Room.databaseBuilder(this, DataBase.class, "makeup").allowMainThreadQueries().build();
        arrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("makeup", MODE_PRIVATE);


        recyclerView = findViewById(R.id.recyclerview);
        //checkInternet();
        progressDialog = new ProgressDialog(this);

        if (getApplicationContext().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        String key = sharedPreferences.getString("key", null);
        if (key != null) {
            if (key.equalsIgnoreCase(ITEMS)) {
                checkInternet();
            } else if(key.equalsIgnoreCase(FAVOURITE))
                displayData();
        } else {
            checkInternet();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();

                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                String url = "https://makeup-api.herokuapp.com/api/v1/products.json";
                try {
                    URL url1 = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String s = "";
                    while ((s = bufferedReader.readLine()) != null) {
                        stringBuilder.append(s);
                    }

                    return stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

        modelClasses = new ArrayList<>();
        String colour_name = null;
        try {
            JSONArray root = new JSONArray(s);
            for (int i = 0; i < root.length(); i++) {
                JSONObject jsonObject = root.getJSONObject(i);
                String id = jsonObject.getString("id");
                String brand = jsonObject.getString("brand");
                String name = jsonObject.getString("name");
                String price = jsonObject.getString("price");
                String price_sign = jsonObject.getString("price_sign");
                String image_link = jsonObject.getString("image_link");
                String product_link = jsonObject.getString("product_link");
                String website_link = jsonObject.getString("website_link");
                String description = jsonObject.getString("description");
                String category = jsonObject.getString("category");
                JSONArray product_color = jsonObject.getJSONArray("product_colors");
                    for (int j = 0; j < product_color.length(); j++) {
                        JSONObject jsonObject1 = product_color.getJSONObject(j);
                        colour_name = jsonObject1.getString("colour_name");
                    }

                ModelClass modelClass = new ModelClass(id, brand, name, price, price_sign, image_link, product_link,
                        website_link, description, category, colour_name);
                modelClasses.add(modelClass);


                Adapter adapter = new Adapter(this, modelClasses);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                if (getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                } else {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(1, null, this);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("\n No internet Connection...");
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.action_products:
                editor = sharedPreferences.edit();
                editor.putString("key", ITEMS);
                editor.commit();
                checkInternet();

                break;
            case R.id.action_logout:
                auth.signOut();
                finish();
                startActivity(new Intent(this,SignInActivity.class));
                break;
            case R.id.action_Comment:
                finish();
                startActivity(new Intent(this,FeedbackActivity.class));
                break;
            case R.id.action_WishList:
                editor = sharedPreferences.edit();
                editor.putString("key", FAVOURITE);
                editor.commit();
                displayData();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayData() {

        database = Room.databaseBuilder(this, DataBase.class, "makeup").allowMainThreadQueries().build();

        favouriteModel.getmFavitems().observe(this, new Observer<List<Favourite_class>>() {
            @Override
            public void onChanged(@Nullable List<Favourite_class> fav_item) {
                arrayList = fav_item;
                FavouriteAdapter adapter = new FavouriteAdapter(MainActivity.this, arrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                if (getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                } else {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
