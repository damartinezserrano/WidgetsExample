package com.example.marti.widgetsexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static CharSequence widgetText;
    static RemoteViews views;
    static int[] idArray;
    static String[] appsArray;
    static int thisWidgetId;
    public static String WIDGET_BUTTON = "MY_WIDGET_BUTTON";
    public static String WIFI_BUTTON = "MY_WIFI_BUTTON";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        thisWidgetId = appWidgetId; //id de este widget

        //intent para el receiver del boton
        Intent intentUpdate = new Intent(context,NewAppWidget.class);
        intentUpdate.setAction(WIDGET_BUTTON);
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,appWidgetId,intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentWifi = new Intent(context,NewAppWidget.class);
        intentWifi.setAction(WIFI_BUTTON);
        intentWifi.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,idArray);
        PendingIntent pendingWifi = PendingIntent.getBroadcast(context,appWidgetId,intentWifi,PendingIntent.FLAG_UPDATE_CURRENT);


        idArray = new int[]{appWidgetId};
        appsArray = new String[]{"Whatsapp","Facebook","Twitter","Clash of Clans","Feedly"};
        //widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.tv1, appsArray[0]);
        views.setTextViewText(R.id.tv2, appsArray[1]);
        views.setTextViewText(R.id.tv3, appsArray[2]);
        views.setTextViewText(R.id.tv4, appsArray[3]);
        views.setTextViewText(R.id.tv5, appsArray[4]);

        // reconocimiento de el click boton y lanzamiento de el pending intent
        views.setOnClickPendingIntent(R.id.updatebtn,pendingUpdate);
        views.setOnClickPendingIntent(R.id.wifibtn,pendingWifi);
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

        widgetText = context.getString(R.string.appwidget_text);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        if(WIDGET_BUTTON.equals(intent.getAction())){
            /*widgetText = "Hola Mundo Update";
            views.setTextViewText(R.id.appwidget_text, widgetText);
            appWidgetManager.updateAppWidget(thisWidgetId,views);*/

            Intent i = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
            context.startActivity(i);
        }else{

            if(WIFI_BUTTON.equals(intent.getAction())){
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if(wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    views.setTextViewText(R.id.wifidesc, "OFF");
                    views.setTextColor(R.id.wifidesc,context.getResources().getColor(R.color.black));
                }else{
                    wifiManager.setWifiEnabled(true);
                    views.setTextViewText(R.id.wifidesc, "ON");
                    views.setTextColor(R.id.wifidesc,context.getResources().getColor(R.color.darkGreenColor));
                }
                appWidgetManager.updateAppWidget(thisWidgetId,views);
            }
        }

    }
}

