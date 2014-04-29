package com.keeptouch.server;

import com.keeptouch.Utils.Storage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by tgoldberg on 4/28/2014.
 */
public class RequestBuilder {
    private ArrayList<NameValuePair> m_NameValuePairs;

    public RequestBuilder(String i_TypeOfRequest)
    {
        m_NameValuePairs = new ArrayList<NameValuePair>();
        m_NameValuePairs.add(new BasicNameValuePair(Storage.TYPE, i_TypeOfRequest));
    }

    public void addParameter(String i_Key, Object i_Value)
    {
        m_NameValuePairs.add(new BasicNameValuePair(i_Key, i_Value.toString()));
    }

    public ArrayList<NameValuePair> getNameValuePairs()
    {
        return m_NameValuePairs;
    }
}
