package com.keeptouch.keeptouch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddEditEventScreen extends MasterScreen {
	Activity thisActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_event);
		
		ViewGroup linearLayoutAttendies = (ViewGroup) findViewById(R.id.linearLayoutInvites);
        List<Integer> drawables = new ArrayList<Integer>();
        drawables.add(R.drawable.natalie);
        drawables.add(R.drawable.tzlil);
        drawables.add(R.drawable.sash);
        drawables.add(R.drawable.hila);
        
        SetFaceList(linearLayoutAttendies, drawables);
        
        ImageButton btnCalendar = (ImageButton) findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
       	 
			@Override
			public void onClick(View arg0) {
 
				Intent i = new Intent();

				//Froyo or greater (mind you I just tested this on CM7 and the less than froyo one worked so it depends on the phone...)
				ComponentName cn = new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity");

				//less than Froyo
				cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");

				i.setComponent(cn);
				startActivity(i);  
			}
 
		});
	}
	
	

}
