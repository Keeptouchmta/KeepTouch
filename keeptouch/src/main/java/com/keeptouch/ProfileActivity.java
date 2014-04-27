package com.keeptouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keeptouch.server.ServerConnection;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends MasterActivity {

    Activity thisActivity = this;
    private int m_profileId;
    private ServerConnection m_ServerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Intent m_Intent = getIntent();
        m_profileId = m_Intent.getIntExtra("profileUserId", -1);
        if (m_profileId != -1) {
            m_ServerConnection = ServerConnection.getConnection();
            m_ServerConnection.GetProfileDetails(m_profileId);

        }

        TextView profileUserName = (TextView) findViewById(R.id.txtUserName);
        //profileUserName.setText("Sasha Poliacov");

        TextView profileLocation = (TextView) findViewById(R.id.txtLocation);
        //profileLocation.setText("Tel Aviv");


        ViewGroup linearLayoutGroups = (ViewGroup) findViewById(R.id.linearLayoutGroups);
        List<Integer> drawablesGroups = new ArrayList<Integer>();
        //drawablesGroups.add(R.drawable.bikes);
        //drawablesGroups.add(R.drawable.domino);
        //drawablesGroups.add(R.drawable.maccabi);

        //SetFaceListWithoutLinks(linearLayoutGroups, drawablesGroups);

        ViewGroup linearLayoutLatestEvents = (ViewGroup) findViewById(R.id.linearLayoutLatestEvents);
        List<Integer> drawablesEvents = new ArrayList<Integer>();
        //drawablesEvents.add(R.drawable.party);
        //drawablesEvents.add(R.drawable.picnic);
        //drawablesEvents.add(R.drawable.beach);
        //SetFaceListWithoutLinks(linearLayoutLatestEvents, drawablesEvents);

        ViewGroup linearLayoutInterests = (ViewGroup) findViewById(R.id.linearLayoutInterests);
        List<Integer> drawablesInterests = new ArrayList<Integer>();
        //drawablesInterests.add(R.drawable.cook);
        //drawablesInterests.add(R.drawable.bikes);
        //drawablesInterests.add(R.drawable.cats);
        //drawablesInterests.add(R.drawable.dogs);
        //SetFaceListWithoutLinks(linearLayoutInterests, drawablesInterests);
    }


}

