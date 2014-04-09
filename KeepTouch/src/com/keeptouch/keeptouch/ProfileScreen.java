package com.keeptouch.keeptouch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.view.animation.Transformation;


	public class ProfileScreen extends MasterScreen {

		Activity thisActivity = this;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.profile);
			
			TextView profileUserName = (TextView) findViewById(R.id.txtUserName);
			profileUserName.setText("Sasha Poliacov");
			
			TextView profileLocation = (TextView) findViewById(R.id.txtLocation);
			profileLocation.setText("Tel Aviv");
			
			
			ViewGroup linearLayoutGroups = (ViewGroup) findViewById(R.id.linearLayoutGroups);
			List<Integer> drawablesGroups = new ArrayList<Integer>();
			drawablesGroups.add(R.drawable.bikes);
			drawablesGroups.add(R.drawable.domino);
			drawablesGroups.add(R.drawable.maccabi);
	        
			SetFaceListWithoutLinks(linearLayoutGroups, drawablesGroups);
	        
	        ViewGroup linearLayoutLatestEvents = (ViewGroup) findViewById(R.id.linearLayoutLatestEvents);
	        List<Integer> drawablesEvents = new ArrayList<Integer>();
	        drawablesEvents.add(R.drawable.party);
	        drawablesEvents.add(R.drawable.picnic);
	        drawablesEvents.add(R.drawable.beach);
	        SetFaceListWithoutLinks(linearLayoutLatestEvents, drawablesEvents);
	        
	        ViewGroup linearLayoutInterests = (ViewGroup) findViewById(R.id.linearLayoutInterests);
	        List<Integer> drawablesInterests = new ArrayList<Integer>();
	        drawablesInterests.add(R.drawable.cook);
	        drawablesInterests.add(R.drawable.bikes);
	        drawablesInterests.add(R.drawable.cats);
	        drawablesInterests.add(R.drawable.dogs);
	        SetFaceListWithoutLinks(linearLayoutInterests, drawablesInterests);
		}

		
		
		
		

	}

