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
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.keeptouch.Utils.Storage;
import com.keeptouch.location.PlaceLocation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tgoldberg on 4/15/2014.
 */
public class KeepTouchFriendPickerActivity extends Activity  implements Serializable{
    //ArrayList that will hold the original Data
    ArrayList<FriendBean> m_KeepTouchFriend;
    ArrayList<FriendBean> m_keepTouchChosenFriend;
    LayoutInflater inflater;
    EditText m_InputSearch;
    FriendPickerAdapter m_FriendPickerAdapter;
    private int RESULT_OK = 0;
    // friend Listview
    ListView m_ListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keeptouch_friend_picker);
        //get the LayoutInflater for inflating the customomView
        //this will be used in the custom adapter
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        m_ListView = (ListView) findViewById(R.id.friend_list);
        m_ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        m_keepTouchChosenFriend = new  ArrayList<FriendBean>();
        m_ListView.setItemsCanFocus(false);
        m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!(m_keepTouchChosenFriend.contains((FriendBean)m_ListView.getItemAtPosition(position)))){
                    m_keepTouchChosenFriend.add ((FriendBean)m_ListView.getItemAtPosition(position));
                }
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
        m_ListView.setAdapter(m_FriendPickerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.friend_picker, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_friend).getActionView();
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);
        searchPlate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_friend:

                if(m_keepTouchChosenFriend != null)
                {
                    Intent intent = new Intent(KeepTouchFriendPickerActivity.this, AddEditEventActivity.class);
                    intent.putExtra(Storage.KEEPTOUCH_FRIENDS, m_keepTouchChosenFriend);
                    setResult(Activity.RESULT_OK, intent);
                    ((Activity) KeepTouchFriendPickerActivity.this).finish();
                }
                else
                {
                    Toast.makeText(this, R.string.no_friends_chosen, Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(KeepTouchFriendPickerActivity.this, AddEditEventActivity.class);
                    setResult(Activity.RESULT_OK, intent);
                    ((Activity) KeepTouchFriendPickerActivity.this).finish();
                }

                break;
            default:
                break;
        }

        return true;
    }
}