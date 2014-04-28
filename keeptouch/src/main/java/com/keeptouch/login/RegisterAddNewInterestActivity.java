package com.keeptouch.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.keeptouch.R;

/**
 * Created by hilaku on 28/04/14.
 */
public class RegisterAddNewInterestActivity extends Activity {
    private Intent m_Intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_add_new_interests);
        m_Intent = getIntent();
        setListenerToButtonAdd();
        setListenerToButtonContinue();
    }

    private void setListenerToButtonAdd() {
    }
    private void setListenerToButtonContinue() {
    }
}
