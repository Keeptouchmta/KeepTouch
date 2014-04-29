package com.keeptouch.server;

import android.content.Context;
import android.location.Location;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.keeptouch.Utils.Storage;
import com.keeptouch.location.PlaceLocation;
import com.keeptouch.login.LoginActivity;
import com.keeptouch.login.RegisterActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.google.android.maps.GeoPoint;

/**
 * Created by tgoldberg on 4/11/2014.
 */
public class ServerConnection {
    private static final String SERVER_IP="1.1.1.1";
    private static final String PORT=":80";
    private static final String PATH="/KeepTouch/Server";
    private static final String PREFIX="http://";
    private static final String URL=PREFIX+SERVER_IP+PORT+PATH;

    public static final int NO_SUCH_USER = -2;
    public static final int USER_EXITS = -1;
    public static final String NO_FB_ID = "noFbId";
    public static boolean facebookAuth;
    private static Facebook m_Facebook;
    // Single instance for this class
    private static ServerConnection m_Instance = new ServerConnection();
    private static AsyncFacebookRunner m_AsyncFacebook;
    private Context m_Context;
    private static int m_UserId;

    /**
     * Private constructor to disallow creation of instances from outside
     */
    private ServerConnection() {
    }

    public LoginActivity.ConfirmUserResult Login(String email, String passsword) {

        LoginActivity.ConfirmUserResult isLoginSuccessful = LoginActivity.ConfirmUserResult.Success;
        RequestBuilder requestBuilder = new RequestBuilder(Storage.LOGIN);
        requestBuilder.addParameter(Storage.USER_EMAIL,email);
        requestBuilder.addParameter(Storage.USER_PASSWORD,passsword);

        try
        {
            JSONObject jsonObject = new JSONObject(postData(requestBuilder.getNameValuePairs()));
            int userId = jsonObject.getInt(Storage.USER_ID);
            m_UserId = userId;

            if(m_UserId != -1)
            {
                isLoginSuccessful = LoginActivity.ConfirmUserResult.Failed;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            isLoginSuccessful = LoginActivity.ConfirmUserResult.Failed;
        }

        return isLoginSuccessful;
    }

    public int getLocalUserId() {
        return 0;
    }

    /**
     * @return a valid facebook Connection object
     * and also create an asyncFacebook object to keep
     */
    public static Facebook GetFacebookConnection() {
        if (m_Facebook == null) {
            synchronized (m_Instance) {
                if (m_Facebook == null) {
                    m_Facebook = new Facebook(Storage.FB_APP_ID);
                    String access_token = Storage.fetchString("access_token");
                    long expires = Storage.fetchLong("access_expires");

                    if (access_token != null) {
                        m_Facebook.setAccessToken(access_token);
                    }

                    if (expires != 0) {
                        m_Facebook.setAccessExpires(expires);
                    }

                    m_AsyncFacebook = new AsyncFacebookRunner(m_Facebook);
                }
            }
        }

        return m_Facebook;
    }

    public static AsyncFacebookRunner GetFacebookAsyncRunner() {
        return m_AsyncFacebook;
    }

    /**
     * @return the single instance of the server connection
     */
    public static ServerConnection getConnection() {
        return m_Instance;
    }

    public static int GetUserId() {
        return 1;
    }

    public static void GetUserDetails(int i_UserId) {
    }

    /**
     * Set a context. Will be MainActivity or PollService, depending on the scenario of startup
     */
    public void SetContext(Context i_Context) {
        m_Context = i_Context;
    }

    public void SetInitialDetails(String email, String password, Context i_Context) {

    }

    /**
     * Method for updating the user details for the first time.
     *
     * @return true if successfully received user details.
     * if not, services will shutdown until user tries to login again
     */
    public Boolean UpdateUserInitially() {
        return true;
    }

    public Object GetUser() {
        return null;
    }


    /**
     * Stop all services in App
     */
    public void StopServices() {

    }

    public int CheckUserExists(String m_email) {
        return NO_SUCH_USER;
    }

    public RegisterActivity.RegisterUserResult RegisterNewUser(String m_email, String m_passsword, Integer o_UserId) {
        o_UserId = 0;
        return RegisterActivity.RegisterUserResult.Success;
    }

    public Integer LoginViaFb() {
        return 0;
    }

    /**
     * Get facebook Id of the specified user
     *
     * @return facebook Id for user
     */
    public String GetFbId(int i_UserId) {
        return ServerConnection.NO_FB_ID;
    }

    /**
     * Register a new user via FB
     *
     * @return userId generated
     */
    public RegisterActivity.RegisterUserResult RegisterNewUserViaFb(String i_FacebookId, Integer o_UserId) {
        o_UserId = 0;
        return RegisterActivity.RegisterUserResult.Success;
    }

    public static void GetFacebookDetails(int i_UserId, String i_FbId) {

    }

    public Boolean UpdateUserDetailsViaFb() {
        return true;
    }

    public void GetProfileDetails(int m_profileId) {
    }

    public boolean AddNewEvent() {
        return true;
    }

    public boolean UpdateEvent() {
        return true;
    }

    public void GetEventDetails() {

    }

    public void GetPlaceDetails() {

    }

    public static void GetkeepTouchFriend() {

    }

    public void UpdateUserLocation(Location i_location) {

    }


    /**
     * get the places nearby by the radius requested around i_Location parameter
     * return the places in the output parameter o_Places which hold users\location
     */
    public ArrayList<PlaceLocation> getPlacesNearBy(int i_RadiusInMeters, Location i_Location) {
        return null;
    }

    public ArrayList<PlaceLocation> getPlaceAccordingToCharSequence(CharSequence constraint) {
        return null;
    }

    private String postData(ArrayList<NameValuePair> data)
    {
        String line = "";
        StringBuilder builder = new StringBuilder();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);

        try
        {
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(data,"UTF-8");
            form.setContentEncoding(HTTP.UTF_8);
            httpPost.setEntity(form);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch(Exception e)
        {
            System.out.println("Cought exception in read Json");
            e.printStackTrace();
        }

        return builder.toString();
    }
}
