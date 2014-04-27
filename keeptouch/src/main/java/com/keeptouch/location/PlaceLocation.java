package com.keeptouch.location;

import com.keeptouch.KeepTouchUser;

import java.io.Serializable;

/**
 * Created by tgoldberg on 4/19/2014.
 */
public class PlaceLocation implements Serializable {
    private Place m_Place;
    private GeoPoint m_Location;

    public PlaceLocation(Place i_Place, GeoPoint i_Location) {
        m_Place = i_Place;
        m_Location = i_Location;
    }

    public Place getPlace() {
        return m_Place;
    }

    public void setPlace(Place i_Place) {
        m_Place = i_Place;
    }

    public GeoPoint getLocation() {
        return m_Location;
    }

    public void setLocation(GeoPoint i_Location) {
        m_Location = i_Location;
    }
}
