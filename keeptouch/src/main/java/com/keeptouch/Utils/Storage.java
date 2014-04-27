package com.keeptouch.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tgoldberg on 4/11/2014.
 */
public class Storage {
    public static final String CHOSEN_LOCATION = "chosenLocation";
    public static final String ADD_EVENT = "addEvent";
    public static final String EDIT_AVENT = "editEvent";
    public static boolean COMMIT = true;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String NEW_USER = "newUser";
    public static final String CANNOT_LOGIN = "cannotLogin";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ACCESS_EXPIRES = "access_expires";
    public static final String GOOGLE_MAP_ID = "233689536559";
    public static final String FB_APP_ID = "242284462626099";
    public static final String FB_AUTH = "fbAuth";
    public static final String SENDER_ID = "835042868787";
    public static final String FB_LOGIN = "fbLogin";
    public static final String REGISTER_FB = "registerFb";
    public static final String KEEPTOUCH_FRIENDS = "keepTouchFriends";
    public static final String GOOGLE_API_KEY = "AIzaSyCeNSDYa6_O0yVCnZgmR1TAHQtOnpHYoN4";
    public static final String EVENT_LATITUDE = "eventLatitude";
    public static final String EVENT_LONGITUDE = "eventLongitude";
    public static final String KEY_REFERENCE = "reference";
    public static final String KEY_NAME = "name";


    private static SharedPreferences m_Settings;

    public static void init(Context context) {
        m_Settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public static void saveString(String key, String value, boolean commit) {
        SharedPreferences.Editor edit = m_Settings.edit();
        edit.putString(key, value);
        if (commit) {
            edit.commit();
        }
    }

    public static void saveInt(String key, int value, boolean commit) {
        SharedPreferences.Editor edit = m_Settings.edit();
        edit.putInt(key, value);
        if (commit) {
            edit.commit();
        }
    }

    public static void saveLong(String key, long value, boolean commit) {
        SharedPreferences.Editor edit = m_Settings.edit();
        edit.putLong(key, value);
        if (commit) {
            edit.commit();
        }
    }

    public static int fetchInt(String key) {
        return m_Settings.getInt(key, -1);
    }

    public static String fetchString(String key) {
        return m_Settings.getString(key, null);
    }

    public static long fetchLong(String key) {
        return m_Settings.getLong(key, 0);
    }

}
