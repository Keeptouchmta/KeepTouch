package com.keeptouch.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.keeptouch.Utils.IObserver;

import java.util.ArrayList;

/**
 * Receiver class for network updates
 * updates the application for network changes via the observer pattern
 */
public class ConnectionReceiver extends BroadcastReceiver
{
	private static Boolean m_NetworkIsOn = false;
	private static Boolean m_Update = false;
	private static ArrayList<IObserver> m_Observers = new ArrayList<IObserver>();
	
	public static void StopObserving(IObserver i_Observer)
	{
		m_Observers.remove(i_Observer);
	}
	
	public static void StartObserving(IObserver i_Observer)
	{
		m_Observers.add(i_Observer);
	}
	
	public static Boolean GetNetworkState()
	{
		return m_NetworkIsOn;
	}
	
	public static Boolean GetUpdateState()
	{
		return m_Update;
	}
	
	public static void SetNetworkState(Boolean i_State)
	{
		m_NetworkIsOn = i_State;
	}
	
	@Override
	public void onReceive(Context arg0, Intent arg1)
	{					
		m_Update = m_NetworkIsOn != !arg1.getBooleanExtra(
		        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		
		m_NetworkIsOn = !arg1.getBooleanExtra(
		        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		System.out.println("Receiver " + m_NetworkIsOn + ", sending to " + m_Observers.size() + " recipients");
		
		for (IObserver observer : m_Observers)
		{
			observer.Update(IObserver.NETWORK_RECEIVER);
		}
	}
}
