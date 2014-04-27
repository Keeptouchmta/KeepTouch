package com.keeptouch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keeptouch.location.Place;
import com.keeptouch.location.PlaceLocation;
import com.keeptouch.server.ServerConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by tgoldberg on 4/19/2014.
 */
public class LocationPickerAdapter extends ArrayAdapter<PlaceLocation> {
    ServerConnection m_ServerConnection;
    Context m_Context;
    private ArrayList<PlaceLocation> m_PlacesLocations = new ArrayList<PlaceLocation>();

    public LocationPickerAdapter(Context context, int textViewResourceId,
                                 ArrayList<PlaceLocation> placesLocations) {
        super(context, textViewResourceId, placesLocations);
        m_Context = context;
        m_PlacesLocations = placesLocations;
    }

    public void setPlaceLocations(ArrayList<PlaceLocation> placeLocations) {
        m_PlacesLocations = placeLocations;
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderLocation holder;
        final PlaceLocation placeLocation = m_PlacesLocations.get(position);

        LayoutInflater mInflater = (LayoutInflater) m_Context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.place_list_item, null);

            holder = new ViewHolderLocation();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.placePhoto = (ImageView) convertView.findViewById(R.id.photo);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderLocation) convertView.getTag();
        }

        holder.name.setText(placeLocation.getPlace().getPlaceName());
        holder.placePhoto.setImageResource(placeLocation.getPlace().getPlacePhoto());
        holder.address.setText(placeLocation.getPlace().getFormattedAddress());

        return convertView;
    }

    @Override
    public int getCount() {
        return m_PlacesLocations.size();
    }

    @Override
    public PlaceLocation getItem(int i) {
        return m_PlacesLocations.get(i);
    }

    /*private  Filter createFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint.toString() != null && constraint.length()!= 0) {

                    m_ServerConnection = ServerConnection.getConnection();
                    m_PlacesLocations = m_ServerConnection.getPlaceAccordingToFilter(constraint.toString().toLowerCase());
                    if (m_PlacesLocations == null) {
                        Place place =  new Place();
                        place.setPlacePhoto(R.drawable.ic_action_place);
                        String placeName = "Create new location " + constraint.toString();
                        place.setPlaceName(placeName);
                        PlaceLocation placeLocation = new PlaceLocation(place,null);
                        m_PlacesLocations = new HashMap<Integer, PlaceLocation>();
                        m_PlacesLocations.put(0,placeLocation);
                    }
                }
                else
                {
                    m_PlacesLocations = m_OrigPlaceLocations;
                }

                filterResults.values = m_PlacesLocations;
                filterResults.count = m_PlacesLocations.size();

                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                m_PlacesLocations = ( HashMap<Integer, PlaceLocation>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    public Filter getFilter() {
        return m_LocationNameFilter;
    }*/

}

class ViewHolderLocation {
    ImageView placePhoto;
    TextView name;
    TextView address;
    ImageView placeIcon;
}