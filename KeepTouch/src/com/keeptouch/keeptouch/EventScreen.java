package com.keeptouch.keeptouch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.view.animation.Transformation;


public class EventScreen extends MasterScreen {

	Activity thisActivity = this;
	boolean isDescriptionOpened = false;
	float actualDescriptionHeight = 100.0f;
	float initialDescriptionHeight = 60.0f;
	float actualDescriptionWidth = 0.0f;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView eventTitle = (TextView) findViewById(R.id.txtEventTitle);
		eventTitle.setText("Party!");
        
		TextView eventLocation = (TextView) findViewById(R.id.txtEventLocation);
		eventLocation.setText("Tel Aviv, Radio bar");
		
		TextView eventDescription = (TextView) findViewById(R.id.txtEventDescription);
		eventDescription.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
		eventDescription.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		//actualDescriptionHeight = eventDescription.getMeasuredHeight();
		actualDescriptionWidth= eventDescription.getMeasuredWidth();
		
		LayoutParams p = eventDescription.getLayoutParams();
        p.height = (int) initialDescriptionHeight;
        eventDescription.setVisibility(View.VISIBLE);
        
		
		TextView eventDate = (TextView) findViewById(R.id.txtEventDate);
		eventDate.setText("9.9.14");
		
		TextView eventAttendies = (TextView) findViewById(R.id.txtAttendies);
		eventAttendies.setText("Attendies:");
		
		TextView eventInvites = (TextView) findViewById(R.id.txtInvites);
		eventInvites.setText("Invites:");
		
		TextView eventCommonAttendiesInterests = (TextView) findViewById(R.id.txtCommonAttendiesInterests);
		eventCommonAttendiesInterests.setText("Common Attendies Interests:");
        
		
        ViewGroup linearLayoutAttendies = (ViewGroup) findViewById(R.id.linearLayoutAttendies);
        List<Integer> drawables = new ArrayList<Integer>();
        drawables.add(R.drawable.natalie);
        drawables.add(R.drawable.tzlil);
        drawables.add(R.drawable.sash);
        drawables.add(R.drawable.hila);
        
        SetFaceList(linearLayoutAttendies, drawables);
        
        ViewGroup linearLayoutInvites = (ViewGroup) findViewById(R.id.linearLayoutInvites);
        
        SetFaceList(linearLayoutInvites, drawables);
        
        
        ImageButton btnDescriptionExpand = (ImageButton)findViewById(R.id.btnDescriptionExpand);
        btnDescriptionExpand.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View descriptionToExpand = findViewById(R.id.txtEventDescription);
				if(!isDescriptionOpened)
				{
	                expand(descriptionToExpand, v);
	            } 
				else 
	            {
	            	collapse(descriptionToExpand, v);
	            }
				isDescriptionOpened = !isDescriptionOpened;
			}
		});
        
        ViewGroup linearLayoutCommonAttendiesInterests = (ViewGroup) findViewById(R.id.linearLayoutCommonAttendiesInterests);
        List<String> interests = new ArrayList<String>();
        interests.add("Metal Music");
        interests.add("Art");
        interests.add("Age: 24");
        LoadCommonInterests(linearLayoutCommonAttendiesInterests, interests);
        
        ImageButton btnEdit = (ImageButton)findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisActivity, AddEditEventScreen.class);
                startActivity(intent);
			}
		});
	}

    public void expand(final View v, final View button) {
		ResizeAnimation a = new ResizeAnimation(v, actualDescriptionWidth, initialDescriptionHeight, actualDescriptionWidth, 100.0f);
		AnimationListener animListener = new AnimationListener() {
	    	public void onAnimationStart(Animation anim)
	    	  {
	    	  }
	    	public  void onAnimationEnd(Animation anim)
	    	  {
	    		((ImageButton)button).setImageDrawable(thisActivity.getResources().getDrawable(R.drawable.btn_collapse));
	    	  }
	    	public void onAnimationRepeat(Animation anim)
	    	  {	    	    
	    	  }
	    	};
	    a.setAnimationListener(animListener);
		v.startAnimation(a);
	}
	public void collapse(final View v, final View button) {
		ResizeAnimation a = new ResizeAnimation(v, actualDescriptionWidth, actualDescriptionHeight, actualDescriptionWidth, initialDescriptionHeight);
		AnimationListener animListener = new AnimationListener() {
	    	public void onAnimationStart(Animation anim)
	    	  {
	    	  }
	    	public  void onAnimationEnd(Animation anim)
	    	  {
	    		((ImageButton)button).setImageDrawable(thisActivity.getResources().getDrawable(R.drawable.btn_expand));
	    	  }
	    	public void onAnimationRepeat(Animation anim)
	    	  {	    	    
	    	  }
	    	};
	    a.setAnimationListener(animListener);
		v.startAnimation(a);
	}
	
	

	private void LoadCommonInterests(ViewGroup layoutToSetInto, List<String> interests)
	{
		for(Iterator<String> drawable = interests.iterator(); drawable.hasNext(); ) 
		{
			TextView interestText = new TextView(thisActivity);
			interestText.setText(drawable.next());
			layoutToSetInto.addView(interestText);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}


