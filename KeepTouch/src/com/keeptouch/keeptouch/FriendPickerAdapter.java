package com.keeptouch.keeptouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tgoldberg on 4/15/2014.
 */
public class FriendPickerAdapter extends ArrayAdapter<FriendBean> {
    Filter friendNameFilter;
    Context m_Context;
    private Map<String, FriendBean> m_KeepTouchFriendsOriginal = new HashMap<String, FriendBean>();
    private ArrayList<String> m_KeepTouchFriendsCurrent ;
    private ArrayList<FriendBean> m_KeepTouchChosenFriend = new ArrayList<FriendBean>();
    public FriendPickerAdapter(Context context, int textViewResourceId,
                         ArrayList<FriendBean> keepTouchFriends) {
        super(context, textViewResourceId, keepTouchFriends);
        m_KeepTouchFriendsCurrent = new ArrayList<String>();
        initFriendMap(keepTouchFriends);
        initFriendArray(keepTouchFriends);
        this.m_Context =context;
        friendNameFilter = createFilter();
    }

    private void initFriendArray(ArrayList<FriendBean> keepTouchFriends) {
        for (FriendBean friendBean : keepTouchFriends) {
            m_KeepTouchFriendsCurrent.add(friendBean.getName());
        }
    }

    private void initFriendMap(ArrayList<FriendBean> keepTouchFriends) {
        for (FriendBean friendBean : keepTouchFriends) {
            m_KeepTouchFriendsOriginal.put(friendBean.getName(), friendBean);
        }
    }

    private Filter createFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<String> tempList = new ArrayList<String>();
                if(constraint != null && m_KeepTouchFriendsOriginal !=null) {
                    for (FriendBean friendBean : m_KeepTouchFriendsOriginal.values()) {
                        if (friendBean.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(friendBean.getName());
                        }
                    }

                    //following two lines is very important
                    //as publish result can only take FilterResults objects
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                m_KeepTouchFriendsCurrent = (ArrayList<String>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    @Override
    public int getCount() {
        return m_KeepTouchFriendsCurrent.size();
    }

    @Override
    public FriendBean getItem(int position) {
        return m_KeepTouchFriendsOriginal.get(m_KeepTouchFriendsCurrent.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final FriendBean friendBean = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) m_Context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.friendPhoto = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(friendBean.getName());
        holder.friendPhoto.setImageResource(friendBean.getPhoto());

        holder.checkBox.setChecked(Boolean.TRUE.equals(friendBean.getSelected()));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendBean.setSelected(holder.checkBox.isChecked());

                if(!holder.checkBox.isChecked() && m_KeepTouchChosenFriend.contains(friendBean))
                {
                    m_KeepTouchChosenFriend.remove(friendBean);
                }
                else if (holder.checkBox.isChecked())
                {
                    m_KeepTouchChosenFriend.add(friendBean);
                }
            }
        });

        return convertView;
    }

    public ArrayList<FriendBean> getKeepTouchChosenFriends()
    {
        return m_KeepTouchChosenFriend;
    }



    @Override
    public Filter getFilter() {
        return friendNameFilter;
    }
}

class ViewHolder {
    ImageView friendPhoto;
    TextView name;
    CheckBox checkBox;
}

class FriendBean implements Serializable {
    private int photo;
    private String name;
    private Boolean selected;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}