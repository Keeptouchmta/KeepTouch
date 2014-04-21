package com.keeptouch;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.keeptouch.Utils.AlertDialogManager;
import com.keeptouch.Utils.IObserver;
import com.keeptouch.Utils.Storage;
import com.keeptouch.location.GPSTrackerService;
import com.keeptouch.location.GeoPoint;
import com.keeptouch.location.Place;
import com.keeptouch.location.PlaceLocation;
import com.keeptouch.location.PlacesList;
import com.keeptouch.receivers.ConnectionReceiver;
import com.keeptouch.server.ServerConnection;
import com.keeptouch.zvalidations.validations.InRange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class LocationPickerActivity extends Activity{
    private GoogleMap m_Map;
    private static final int m_Radius = 25000;

    private ServerConnection m_ServerConnection;
    // flag for Internet connection status
    Boolean m_IsInternetPresent = false;
    LocationPickerAdapter m_LocationPickerAdapter;
    // Alert Dialog Manager
    AlertDialogManager m_Alert = new AlertDialogManager();
    // Connection detector class
    ConnectionReceiver m_ConnectionReceiver;
    // Places List
    ArrayList<PlaceLocation> m_NearPlaces;
    // GPS Location
    GPSTrackerService m_Gps;
    // Progress dialog
    ProgressDialog m_Dialog;
    private AlertDialog m_NoInternetDialog;
    // Places Listview
    ListView m_ListView;
    ArrayList<MarkerOptions> m_Markers = new ArrayList<MarkerOptions>();
    private Marker myMarker;
    Menu m_Menu;
    PlaceLocation m_ChosenPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_picker);
        m_Map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        m_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        m_Map.setMyLocationEnabled(true);

        // Getting listview
        m_ListView = (ListView) findViewById(R.id.list);
        m_ListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        m_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_ChosenPlace = (PlaceLocation)m_ListView.getItemAtPosition(position);
                showPlaceOnMap(m_ChosenPlace);
//                m_ChoosePlaceItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        Intent intent = new Intent(LocationPickerActivity.this, AddEditEventActivity.class);
//                        intent.putExtra(Storage.CHOSEN_LOCATION, m_ChosenPlace);
//                        setResult(Activity.RESULT_OK, intent);
//                        ((Activity) LocationPickerActivity.this).finish();
//                        return true;
//                    }
//                });
            }
        });

        new LoadNearByPlacesAndServicesTask().execute();
    }

    private void showPlaceOnMap(PlaceLocation itemAtPosition) {
        GeoPoint geoPoint = itemAtPosition.getLocation();
        double lat = (double)geoPoint.getLatitudeE6() / 1E6;
        double lng = (double)geoPoint.getLongitudeE6() / 1E6;
        LatLng latLng = new LatLng(lat, lng);
        m_Map.clear();
        MarkerOptions marker = new MarkerOptions().position(latLng);
        m_Map.addMarker(marker);
        m_Markers.add(marker);
    }

    private void setUpMyLocation() {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                m_Gps.getLatitude(), m_Gps.getLongitude()), 14);
        m_Map.moveCamera(cameraUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.places_picker_menu, menu);
        m_Menu = menu;
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_location).getActionView();
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);
        searchPlate.setHint("Search Place..");
        searchPlate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                new GetPlacesAccordingToCharSequenceTask().execute(cs);

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

    private class LoadNearByPlacesAndServicesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            m_ServerConnection = ServerConnection.getConnection();
            System.out.println("After setting mapview settings progDialog");
            m_Dialog = new ProgressDialog(LocationPickerActivity.this);
            m_Dialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            m_Dialog.setIndeterminate(false);
            m_Dialog.setCancelable(false);
            m_Dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // ConnectionReceiver.StartObserving(LocationPickerActivity.this);
            startLocationService();
            m_ServerConnection = ServerConnection.getConnection();
            m_NearPlaces = new ArrayList<PlaceLocation>();
//            m_NearPlaces = m_ServerConnection.getPlacesNearBy(m_Radius,m_Gps.getLocation());

            String names[] = {"Tzlil", "Natali","Sasha"};
            //GeoPoint point[] = {new GeoPoint(};
            GeoPoint[] point = {new GeoPoint((int)(32.069961 * 1E6),(int)(34.78208*1E6)),new GeoPoint((int)(32.069962 * 1E6),(int)(34.78208*1E6)),new GeoPoint((int)(32.169962 * 1E6),(int)(34.78308*1E6)) };
            Integer[] photos = {R.drawable.tzlil,R.drawable.natalie, R.drawable.sash};
            String[] address =  {"tel aviv","ramat gan", "givatayim"};
            Place placeLocation;
            int noOfPlayers = names.length;

            //now populate the ArrayList players
            for (int i = 0; i < noOfPlayers; i++) {
                placeLocation = new Place();
                placeLocation.setPlaceName(names[i]);
                placeLocation.setPlacePhoto(photos[i]);
                placeLocation.setFormattedAddress(address[i]);
                PlaceLocation placeLocation1 = new PlaceLocation(placeLocation,point[i]);

                //add the row to the ArrayList
                m_NearPlaces.add(placeLocation1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("DISMISSING progDialog");
            // dismiss the dialog after getting all products
            m_Dialog.dismiss();
            // updating UI from Background Thread

            // Successfully got places details
            if (m_NearPlaces != null) {

                m_LocationPickerAdapter = new LocationPickerAdapter(LocationPickerActivity.this,
                        R.layout.place_list_item, m_NearPlaces);

                m_ListView.setAdapter(m_LocationPickerAdapter);

                getActionBar().setTitle(R.string.select_location);
            }

            setUpMyLocation();
        }
    }

    /**
     * Start location service
     */
    private void startLocationService() {
        // creating GPS Class object
        m_Gps = new GPSTrackerService(this);

        // check if GPS location can get
        if (m_Gps.canGetLocation()) {
            //Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            m_Alert.showAlertDialog(LocationPickerActivity.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
            return;
        }
    }


    public void Update(int i_Updater)
    {
        switch (i_Updater)
        {
            case IObserver.NETWORK_RECEIVER:
                System.out.println("Net: " + ConnectionReceiver.GetNetworkState());
                if (!ConnectionReceiver.GetNetworkState())
                {
                    showNoConnectionDialog();
                }
                else
                {
                    if (m_NoInternetDialog != null)
                    {
                        m_NoInternetDialog.cancel();
                    }
                }

        }
    }

    private void showNoConnectionDialog()
    {
        //AlertDialogManager.Builder builder = new AlertDialog.Builder(EnvirActivity.this);
        //m_NoInternetDialog = builder.setMessage("Internet is down. Waiting for connection...").setPositiveButton("Quit", new InternetDialogListener()).show();
    }

    private class GetPlacesAccordingToCharSequenceTask extends AsyncTask<CharSequence, Void,  CharSeqAndPlaceLocations> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected CharSeqAndPlaceLocations doInBackground(CharSequence... cs) {
            m_ServerConnection = ServerConnection.getConnection();
            CharSequence charSequence = cs[0];
            ArrayList<PlaceLocation> newPlaceLocations = new ArrayList<PlaceLocation>();
            if (charSequence.length() > 0) {
                newPlaceLocations =
                        m_ServerConnection.getPlaceAccordingToCharSequence(charSequence.toString().toLowerCase());
            }

            return new CharSeqAndPlaceLocations(newPlaceLocations, charSequence);
        }

        @Override
        protected void onPostExecute(CharSeqAndPlaceLocations charSeqAndPlaceLocations) {
            ArrayList<PlaceLocation> placesLocation = new ArrayList<PlaceLocation>();

            if (charSeqAndPlaceLocations.charSequence.length() > 0) {
                if (charSeqAndPlaceLocations.placeLocations == null) {
                    Place place = new Place();
                    place.setPlacePhoto(R.drawable.ic_action_place);
                    String placeName = "Create new location " + charSeqAndPlaceLocations.charSequence.toString();
                    place.setPlaceName(placeName);
                    PlaceLocation placeLocation = new PlaceLocation(place, null);
                    placesLocation = new ArrayList<PlaceLocation>();
                    placesLocation.add(placeLocation);
                } else {
                    placesLocation = charSeqAndPlaceLocations.placeLocations;
                }

                m_LocationPickerAdapter.setPlaceLocations(placesLocation);
            } else {
                m_LocationPickerAdapter.setPlaceLocations(m_NearPlaces);
            }
        }
    }

    class CharSeqAndPlaceLocations {
        CharSeqAndPlaceLocations(ArrayList<PlaceLocation> placeLocations, CharSequence charSequence) {
            this.placeLocations = placeLocations;
            this.charSequence = charSequence;
        }

        ArrayList<PlaceLocation> placeLocations;
        CharSequence charSequence;
    }
}
