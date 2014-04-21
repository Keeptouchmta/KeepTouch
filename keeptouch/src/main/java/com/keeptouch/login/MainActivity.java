package com.keeptouch.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.keeptouch.ProfileActivity;
import com.keeptouch.Utils.Storage;
import com.keeptouch.server.ServerConnection;

/**
 * The main activity of the app
 * Uses as a gateway. checks if user is logged in, and if so - starts the profile activity
 * otherwise, sends the user to the Authentication activity
 * Before it checks everything - it ensures Network connectivity
 */
public class MainActivity extends Activity {

    private ServerConnection m_ServerConnection;
    private int m_UserId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Storage.init(this);
        checkForNetworkConnectivity();
    }

    private void checkForNetworkConnectivity() {
        Boolean isNetEnabled = false;
        System.out.println("check for network Connectivity");
        System.out.println("ask for con");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Boolean networkActive = activeNetworkInfo != null;

        if (!networkActive) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Internet connection is down..")
                    .setCancelable(false)
                    .setPositiveButton("Enable Wifi", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            dialog.dismiss();
                            checkForNetworkConnectivity();
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            MainActivity.this.finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            System.out.println("return");
            new MainTask().execute();
        }
    }

    /**
     *
     * Main AsyncTask checks if user is logged in or not. send him to the appropriate activity based on authentication
     */
    private class MainTask extends AsyncTask<Void , Void, Boolean>
    {
        @Override
        protected void onPostExecute(Boolean i_Success)
        {
            if (!i_Success)
            {
                Toast.makeText(MainActivity.this,
                        "Cannot get user details. please try again later.", Toast.LENGTH_LONG).show();
                m_ServerConnection.StopServices();
                MainActivity.this.finish();
                return;
            }
            else
            {
                if (m_UserId == -1)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... arg0)
        {
            m_ServerConnection = ServerConnection.getConnection();
            m_ServerConnection.SetContext(MainActivity.this);
            String email = Storage.fetchString(Storage.USER_EMAIL);
            String password = Storage.fetchString(Storage.USER_PASSWORD);

            if (m_UserId == -1)
            {
                m_UserId = Storage.fetchInt(Storage.USER_ID);
                if (m_UserId == -1)
                {
                    return true; // User isn't logged in, userId is -1 -> move to AuthChoose
                }

                m_ServerConnection.SetInitialDetails(email, password, MainActivity.this);

                if (email != null && password != null)
                {
                    return m_ServerConnection.UpdateUserInitially();
                }
            }

            if (m_ServerConnection.GetUser() == null)
            {
                m_ServerConnection.SetInitialDetails(email, password, MainActivity.this);
                Boolean success = m_ServerConnection.UpdateUserInitially();
                return success;
            }
            else
            {
                return true;
            }
        }
    }
}

