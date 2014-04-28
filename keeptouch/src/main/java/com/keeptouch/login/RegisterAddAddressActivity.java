package com.keeptouch.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.keeptouch.LocationPickerActivity;
import com.keeptouch.R;

/**
 * Created by hilaku on 28/04/14.
 */
public class RegisterAddAddressActivity extends Activity {
    private Intent m_Intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_add_address);
        System.out.println("on create for Register activity");
        m_Intent = getIntent();
        setListenerToButtonSetCurrentLocation();
        setListenerToButtonDone();
        setListenerToButtonSearchAddress();
    }

    private void setListenerToButtonSearchAddress() {
        final Button buttonAddNewInterest;
        buttonAddNewInterest = (Button) findViewById(R.id.buttonAddNewInterest);
        buttonAddNewInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationPickerActivity();
            }
        });
    }

    private void setListenerToButtonDone() {
        final Button buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainScreen();
            }
        });
    }

    private void showMainScreen() {
    }

    private void setListenerToButtonSetCurrentLocation() {
        final Button buttonSetCurrentLocation = (Button) findViewById(R.id.buttonSetCurrentLocation);
        buttonSetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetCurrentLocationAsUserLocation();
            }
        });
    }

    private void SetCurrentLocationAsUserLocation() {
        openLocationPickerActivity();
    }

    private void openLocationPickerActivity() {
        Intent m_Intent = new Intent(RegisterAddAddressActivity.this, LocationPickerActivity.class);
        startActivityForResult(m_Intent, 0);
    }
}
