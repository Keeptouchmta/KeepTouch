package com.keeptouch.keeptouch.messaging;

import java.util.ArrayList;
import java.util.HashMap;

import com.keeptouch.keeptouch.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import com.keeptouch.keeptouch.server.ServerConnection;


/** 
* PollService is a service that responds to requests from main process.
* Its purpose is to poll for messages and events from server following a request
*  at application startup.
* Following messages received, it produces notifications to the status bar.
*/
public class PollService extends Service
{
	
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

