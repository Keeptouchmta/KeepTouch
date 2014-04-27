package com.keeptouch.location;

import com.google.android.maps.GeoPoint;
import com.keeptouch.KeepTouchUser;

/**
 * Class holding an N2MContact and its location
 */
public class UserLocation {
    private KeepTouchUser m_User;
    private GeoPoint m_Location;

    public UserLocation(KeepTouchUser i_User, GeoPoint i_Location) {
        m_User = i_User;
        m_Location = i_Location;
    }

    public KeepTouchUser getUser() {
        return m_User;
    }

    public void setUser(KeepTouchUser i_User) {
        m_User = i_User;
    }

    public GeoPoint getLocation() {
        return m_Location;
    }

    public void setLocation(GeoPoint i_Location) {
        m_Location = i_Location;
    }

}

