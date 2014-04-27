package com.keeptouch;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.keeptouch.Utils.Storage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tgoldberg on 4/15/2014.
 */
public class KeepTouchFriendPickerActivity extends ListActivity implements Serializable {
    //ArrayList that will hold the original Data
    ArrayList<FriendBean> m_KeepTouchFriend;
    ArrayList<FriendBean> m_keepTouchChosenFriend;
    LayoutInflater inflater;
    EditText m_InputSearch;
    FriendPickerAdapter m_FriendPickerAdapter;
    private int RESULT_OK = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keeptouch_friend_picker);
        //get the LayoutInflater for inflating the customomView
        //this will be used in the custom adapter
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Button buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChosenKeepTouchFriends();
                Intent intent = new Intent(KeepTouchFriendPickerActivity.this, AddEditEventActivity.class);
                intent.putExtra(Storage.KEEPTOUCH_FRIENDS, m_keepTouchChosenFriend);
                setResult(Activity.RESULT_OK, intent);
                ((Activity) KeepTouchFriendPickerActivity.this).finish();
            }

        });

        //these arrays are just the data that
        //I'll be using to populate the ArrayList
        //You can use our own methods to get the data
        String names[] = {"Tzlil", "Natalie", "Alex", "hila"};

        Integer[] photos = {R.drawable.tzlil, R.drawable.natalie,
                R.drawable.sash, R.drawable.hila};

        m_KeepTouchFriend = new ArrayList<FriendBean>();

        //temporary HashMap for populating the
        //Items in the ListView
        FriendBean temp;

        //total number of rows in the ListView

        int noOfPlayers = names.length;

        //now populate the ArrayList players
        for (int i = 0; i < noOfPlayers; i++) {
            temp = new FriendBean();
            temp.setName(names[i]);
            temp.setPhoto(photos[i]);

            //add the row to the ArrayList
            m_KeepTouchFriend.add(temp);
        }

        m_FriendPickerAdapter = new FriendPickerAdapter(this, R.layout.friend_list_item, m_KeepTouchFriend);
        m_InputSearch = (EditText) findViewById(R.id.inputSearch);
        m_InputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                m_FriendPickerAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        setListAdapter(m_FriendPickerAdapter);
    }

    private void setChosenKeepTouchFriends() {
        m_keepTouchChosenFriend = m_FriendPickerAdapter.getKeepTouchChosenFriends();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.friend_picker, menu);
        return true;
    }
}