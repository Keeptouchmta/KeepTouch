package com.keeptouch.server;

import android.content.Context;
import android.location.Location;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.keeptouch.Utils.Storage;
import com.keeptouch.location.PlaceLocation;
import com.keeptouch.login.LoginActivity;
import com.keeptouch.login.RegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by tgoldberg on 4/11/2014.
 */
public class ServerConnection {
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
    private ServerConnection()
    {
    }

    public LoginActivity.ConfirmUserResult Login(String email, String passsword) {
        return LoginActivity.ConfirmUserResult.Success;
    }

    public int getLocalUserId() {
        return 0;
    }

    /**
     * @return a valid facebook Connection object
     * and also create an asyncFacebook object to keep
     */
    public static Facebook GetFacebookConnection()
    {
        if (m_Facebook == null)
        {
            synchronized (m_Instance)
            {
                if (m_Facebook == null)
                {
                    m_Facebook = new Facebook(Storage.FB_APP_ID);
                    String access_token = Storage.fetchString("access_token");
                    long expires = Storage.fetchLong("access_expires");

                    if(access_token != null)
                    {
                        m_Facebook.setAccessToken(access_token);
                    }

                    if(expires != 0)
                    {
                        m_Facebook.setAccessExpires(expires);
                    }

                    m_AsyncFacebook = new AsyncFacebookRunner(m_Facebook);
                }
            }
        }

        return m_Facebook;
    }

    public static AsyncFacebookRunner GetFacebookAsyncRunner()
    {
        return m_AsyncFacebook;
    }

    /**
     * @return the single instance of the server connection
     */
    public static ServerConnection getConnection()
    {
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
    public void SetContext(Context i_Context)
    {
        m_Context = i_Context;
    }

    public void SetInitialDetails(String email, String password,Context i_Context) {

    }

    /**
     * Method for updating the user details for the first time.
     * @return true if successfully received user details.
     * if not, services will shutdown until user tries to login again
     */
    public Boolean UpdateUserInitially()
    {
        return true;
    }

    public Object GetUser() {
        return null;
    }


    /**
     * Stop all services in App
     */
    public void StopServices()
    {

    }

    public int CheckUserExists(String m_email) {
        return NO_SUCH_USER;
    }

    public RegisterActivity.RegisterUserResult RegisterNewUser(String m_email, String m_passsword, Integer o_UserId) {
        o_UserId =0;
        return RegisterActivity.RegisterUserResult.Success;
    }

    public Integer LoginViaFb() {
        return 0;
    }

    /**
     * Get facebook Id of the specified user
     * @return facebook Id for user
     */
    public String GetFbId(int i_UserId)
    {
        return ServerConnection.NO_FB_ID;
    }

    /**
     *  Register a new user via FB
     * @return userId generated
     */
    public RegisterActivity.RegisterUserResult RegisterNewUserViaFb(String i_FacebookId , Integer o_UserId)
    {
        o_UserId =0;
        return RegisterActivity.RegisterUserResult.Success;
    }

    public static void GetFacebookDetails(int i_UserId, String i_FbId) {

    }

    public Boolean UpdateUserDetailsViaFb() {
        return true;
    }

    public void GetProfileDetails(int m_profileId) {
    }

    public boolean AddNewEvent()
    {
        return true;
    }

    public boolean UpdateEvent()
    {
        return true;
    }

    public void GetEventDetails()
    {

    }

    public void GetPlaceDetails()
    {

    }

    public static void GetkeepTouchFriend() {

    }

    public void UpdateUserLocation(Location i_location) {

    }


    /**
     * get the places nearby by the radius requested around i_Location parameter
     * return the places in the output parameter o_Places which hold users\location
     */
    public ArrayList<PlaceLocation> getPlacesNearBy(int i_RadiusInMeters, Location i_Location)
    {
        return null;
    }

    public ArrayList<PlaceLocation> getPlaceAccordingToCharSequence(CharSequence constraint )
    {
        return null;
    }
}
