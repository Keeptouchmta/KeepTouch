package com.keeptouch.messaging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


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

