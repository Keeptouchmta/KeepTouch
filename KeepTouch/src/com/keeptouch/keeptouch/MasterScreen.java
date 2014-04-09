package com.keeptouch.keeptouch;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MasterScreen extends Activity{
	
	public Activity thisActivity = this;
	
	public void SetFaceList(ViewGroup layoutToSetInto, List<Integer> drawables)
	{
		LinearLayout.LayoutParams layoutParamsWithNoMargins = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(15, 0, 0, 0);
		int index = 0;
		for(Iterator<Integer> drawable = drawables.iterator(); drawable.hasNext(); ) 
		{
			ImageButton faceButton = new ImageButton(thisActivity);
			if(index == 0)
				faceButton.setLayoutParams(layoutParamsWithNoMargins);
			else
				faceButton.setLayoutParams(layoutParams);
			faceButton.setImageDrawable(thisActivity.getResources().getDrawable(drawable.next()));
			faceButton.setPadding(0, 0, 0, 0);
			faceButton.setOnClickListener(new View.OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
	 
				    Intent intent = new Intent(thisActivity, ProfileScreen.class);
	                startActivity(intent);   
				}
	 
			});
			layoutToSetInto.addView(faceButton);
			index++;
			
		}
	}

	//temp function:
	//in the future there wont be this function , all the pictures will have their links
	// event pics to event pages, profiles pics to profile screens, interests pics to interest pages..
	public void SetFaceListWithoutLinks(ViewGroup layoutToSetInto, List<Integer> drawables)
	{
		LinearLayout.LayoutParams layoutParamsWithNoMargins = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(15, 0, 0, 0);
		int index = 0;
		for(Iterator<Integer> drawable = drawables.iterator(); drawable.hasNext(); ) 
		{
			ImageButton faceButton = new ImageButton(thisActivity);
			if(index == 0)
				faceButton.setLayoutParams(layoutParamsWithNoMargins);
			else
				faceButton.setLayoutParams(layoutParams);
			faceButton.setImageDrawable(thisActivity.getResources().getDrawable(drawable.next()));
			faceButton.setPadding(0, 0, 0, 0);
			layoutToSetInto.addView(faceButton);
			index++;
			
		}
	}
}
