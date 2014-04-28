package com.keeptouch;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.keeptouch.Utils.DatePickerFragment;
import com.keeptouch.Utils.Storage;
import com.keeptouch.Utils.TimePickerFragment;
import com.keeptouch.zvalidations.Field;
import com.keeptouch.zvalidations.Form;
import com.keeptouch.zvalidations.TextViewValidationFailedRenderer;
import com.keeptouch.zvalidations.validations.NotEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddEditEventActivity extends FragmentActivity {
	Activity thisActivity = this;
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;/** This integer will uniquely define the dialog to be used for displaying date picker.*/
    private int m_Year;
    private int m_Month;
    private int m_Day;

    private TextView  txtAddEditEventTitle;
    private EditText m_EventDesc;
    private EditText m_EventTitle;
    // Form used for validation
    private Form m_Form;
    private List<Integer> m_FriendDrawables;
    private ViewGroup m_LinearLayoutAttendies;
    private ArrayList<FriendBean> m_KeepTouchAttendies = new ArrayList<FriendBean>();
    private int REQUEST_OK = 0;
    Button m_DatePickerShowDialogButton = null;
    private int m_Hour;
    private int m_Minute;
    private Date m_Date=new Date();
    private boolean isPrivate = false;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_event);
        initFields();
        initValidationForm();
        txtAddEditEventTitle = (TextView) findViewById(R.id.txtAddEditEventTitle);

        ImageButton btnTime = (ImageButton) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                 showTimePicker();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        m_Year=calendar.get(Calendar.YEAR);
        m_Month=calendar.get(Calendar.MONTH);
        m_Day=calendar.get(Calendar.DAY_OF_MONTH);
        ImageButton btnCalendar = (ImageButton) findViewById(R.id.btnCalendar);

        btnCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        ImageButton buttonSave = (ImageButton) findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_Form.isValid()) {

                }
            }
        });

        ImageButton buttonPlusInterests = (ImageButton) findViewById(R.id.btnAddAttendees);
        buttonPlusInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m_Intent = new Intent(AddEditEventActivity.this, KeepTouchFriendPickerActivity.class);
                startActivityForResult(m_Intent, 0);
            }

        });

        ImageButton buttonLocationPin = (ImageButton) findViewById(R.id.btnAddLocation);
        buttonLocationPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m_Intent = new Intent(AddEditEventActivity.this, LocationPickerActivity.class);
                startActivityForResult(m_Intent, 0);
            }

        });

        ImageButton buttonPrivacy = (ImageButton) findViewById(R.id.btnPrivacy);
        buttonPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrivate = true;
            }

        });

       m_LinearLayoutAttendies = (ViewGroup) findViewById(R.id.linearLayoutInvites);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(Storage.ADD_EVENT))
        {
            setEventTitle(R.string.add_new_event);
        }
        else if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Storage.EDIT_AVENT))
        {
            setEventTitle(R.string.edit_event);
        }
    }

    private void initValidationForm() {
        m_Form = new Form(this);
        m_Form.setValidationFailedRenderer( new TextViewValidationFailedRenderer(this));
        m_Form.addField(Field.using(m_EventTitle).validate(NotEmpty.build(this)));
    }

    private void initFields() {
        m_EventDesc = (EditText) findViewById(R.id.etxtEventDescription);
        m_EventTitle = (EditText) findViewById(R.id.etxtEventTitle);
    }

    private void setEventTitle(int eventTitle) {
        txtAddEditEventTitle.setText(eventTitle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_OK && data.hasExtra(Storage.KEEPTOUCH_FRIENDS))
        {
            ArrayList<FriendBean> m_ChosenKeepTouchAttendies = (ArrayList<FriendBean>) data.getSerializableExtra(Storage.KEEPTOUCH_FRIENDS);

            if(m_FriendDrawables == null)
            {
                m_FriendDrawables = new ArrayList<Integer>();
            }

            for (FriendBean keepTouchFriend : m_ChosenKeepTouchAttendies)
            {
                if(!m_KeepTouchAttendies.contains(keepTouchFriend))
                {
                    m_KeepTouchAttendies.add(keepTouchFriend);
                }
            }

            SetFaceList(m_LinearLayoutAttendies, m_KeepTouchAttendies);
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_OK && data.hasExtra(Storage.CHOSEN_LOCATION))
        {

        }
        else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_OK && data.hasExtra(Storage.CHOSEN_LOCATION))
        {

        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
          int m_Year = year;
          int m_monthOfYear =monthOfYear;
          int m_dayOfMonth = dayOfMonth;
        }
    };

    public void SetFaceList(ViewGroup layoutToSetInto, List<FriendBean> keepTouchChosenFriend)
    {
        LinearLayout.LayoutParams layoutParamsWithNoMargins = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 0, 0, 0);
        int index = 0;
        layoutToSetInto.removeAllViews();

        for(final FriendBean friendBean : keepTouchChosenFriend )
        {
            ImageButton faceButton = new ImageButton(thisActivity);
            if(index == 0)
                faceButton.setLayoutParams(layoutParamsWithNoMargins);
            else
                faceButton.setLayoutParams(layoutParams);
            faceButton.setImageDrawable(thisActivity.getResources().getDrawable(friendBean.getPhoto()));
            faceButton.setPadding(0, 0, 0, 0);
            faceButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(thisActivity, ProfileActivity.class);
                    intent.putExtra(Storage.USER_ID,friendBean.getUserId());
                    startActivity(intent);
                }

            });

            layoutToSetInto.addView(faceButton);
            index++;

        }
    }

    public void showTimePicker()
    {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setCallBack(ontime);
        timePickerFragment.show(getSupportFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            m_Hour = hourOfDay;
            m_Minute = minute;
        }
    };
}
