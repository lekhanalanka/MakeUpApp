package com.example.makeupapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AppWidget extends AppWidgetProvider {

    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String SHARED_PEFERENCE = "sharedPreference";
    public static final String GET = "GET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PEFERENCE,Context.MODE_PRIVATE);
        String image = sharedPreferences.getString(IMAGE,null);
        String name = sharedPreferences.getString(NAME,null);
        String widgetText = name;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        try {
            URL url=new URL(image);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(GET);
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            views.setImageViewBitmap(R.id.widget_image,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        views.setTextViewText(R.id.appwidget_text, widgetText);


        Intent intent = new Intent(context, MainActivity.class);
        sharedPreferences.edit();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

