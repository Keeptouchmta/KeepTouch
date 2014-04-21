package com.keeptouch.location;

import java.io.Serializable;

public class Place  {
    public String m_PlaceId;
    public String m_PlaceName;
    public String m_Icon;
    public String m_Formatted_address;
    public int m_Photo;

   public String getPlaceName()
   {
       return m_PlaceName;
   }

    public void setPlaceName(String i_PlaceName)
    {
        m_PlaceName = i_PlaceName;
    }

    public int getPlacePhoto()
    {
        return m_Photo;
    }

    public void setPlacePhoto(int i_Photo)
    {
        m_Photo = i_Photo;
    }

    public void setFormattedAddress(String i_FormattedAddress) {
        m_Formatted_address = i_FormattedAddress;
    }

    public String getFormattedAddress(){return m_Formatted_address;};


}
