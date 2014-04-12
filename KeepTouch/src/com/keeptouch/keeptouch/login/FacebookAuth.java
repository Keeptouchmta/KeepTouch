package com.keeptouch.keeptouch.login;

import android.app.Activity;
import android.os.Bundle;

import com.keeptouch.keeptouch.ProfileScreen;
import com.facebook.android.*;
import com.facebook.android.Facebook.*;
import android.content.Intent;
import android.widget.Toast;

import com.keeptouch.keeptouch.Utils.Storage;
import com.keeptouch.keeptouch.server.ServerConnection;

/**
 * Created by tgoldberg on 4/10/2014.
 */
public class FacebookAuth extends Activity {

    private static Facebook m_Facebook;
    private static Intent m_Intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_Facebook = ServerConnection.GetFacebookConnection();
        String access_token = Storage.fetchString(Storage.ACCESS_TOKEN);
        long expires = Storage.fetchLong(Storage.ACCESS_EXPIRES);

        if(access_token != null)
        {
            m_Facebook.setAccessToken(access_token);
        }

        if(expires != 0)
        {
            m_Facebook.setAccessExpires(expires);
        }

        if(!m_Facebook.isSessionValid())
        {
            connectToFacebook();
        }
        else
        {

        }
    }

    private void connectToFacebook() {

        try
        {
            m_Facebook.authorize(this,new String[]{"user_birthday",
                    "user_photos", "user_status", "email"}, new DialogListener()
            {
                @Override
                public void onComplete(Bundle values)
                {
                    Storage.saveString("access_token", m_Facebook.getAccessToken(),false);
                    Storage.saveLong("access_expires",m_Facebook.getAccessExpires(),true);
                    System.out.println("FB COMPLETE");
                    ServerConnection.facebookAuth = true;
                    ServerConnection.GetUserDetails(ServerConnection.GetUserId());
                    goToNextActivity();
                }

                @Override
                public void onFacebookError(FacebookError error)
                {
                    System.out.println("FB AUTH ERROR: " + error.getMessage());
                    goToNextActivity();
                }

                @Override
                public void onError(DialogError e) {
                    System.out.println("ERROR: " + e.getMessage());
                    goToNextActivity();
                }

                @Override
                public void onCancel() {
                    goToNextActivity();
                }
            });
        }
        catch(Exception e)
        {
            System.out.println(e);
            Toast.makeText(this, "Problem connecting to server with FB registration. please try again..", Toast.LENGTH_LONG).show();
            goToNextActivity();
        }
        catch(AssertionError a)
        {
            System.out.println(a);
            Toast.makeText(this, "Problem connecting to server with FB registration. please try again..", Toast.LENGTH_LONG).show();
            goToNextActivity();
        }
    }

    private void goToNextActivity(){

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(Storage.FB_LOGIN))
        {
            m_Intent = new Intent(FacebookAuth.this, ProfileScreen.class);
        }
        else if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(Storage.REGISTER_FB))
        {
            m_Intent = new Intent(FacebookAuth.this, RegisterActivity.class);
        }

        startActivity(m_Intent);
        (FacebookAuth.this).finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        m_Facebook.authorizeCallback(requestCode, resultCode, data);
        this.finish();
    }


}
