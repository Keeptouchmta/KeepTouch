package com.keeptouch.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.keeptouch.LocationPickerActivity;
import com.keeptouch.R;

/**
 * Created by hilaku on 28/04/14.
 */
public class RegisterAddInterestsActivity extends Activity {

    private Intent m_Intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_add_interests);
        System.out.println("on create for Register activity");
        m_Intent = getIntent();
        setArrayToAutoCompleteSearch();
        setListenerToButtonAddNewInterests();
        setListenerToButtonContinue();
    }

    private void setListenerToButtonContinue() {
        final Button buttonContinue;
        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent m_Intent = new Intent(RegisterAddInterestsActivity.this, RegisterAddAddressActivity.class);
        startActivityForResult(m_Intent, 0);
            }
        });
    }

    private void setListenerToButtonAddNewInterests() {
        final Button buttonAddNewInterest;
        buttonAddNewInterest = (Button) findViewById(R.id.buttonAddNewInterest);
        buttonAddNewInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m_Intent = new Intent(RegisterAddInterestsActivity.this, RegisterAddAddressActivity.class);
                startActivityForResult(m_Intent, 0);
            }
        });
    }

    private void setArrayToAutoCompleteSearch() {
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteSearch);
        textView.setAdapter(getAutoCompleteArray());
    }

    private ArrayAdapter<String> getAutoCompleteArray() {
        String[] list = getResources().getStringArray(R.array.interest_options);
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    }
}
