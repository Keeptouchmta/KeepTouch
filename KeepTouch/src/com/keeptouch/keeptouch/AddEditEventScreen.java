package com.keeptouch.keeptouch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.keeptouch.keeptouch.Utils.Storage;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Field;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Form;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.NotEmpty;
import com.keeptouch.keeptouch.MasterScreen;

public class AddEditEventScreen extends FragmentActivity {
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
    private ArrayList<FriendBean> m_ChosenKeepTouchAttendies;
    private int REQUEST_OK = 0;
    Button m_DatePickerShowDialogButton = null;
    private int m_Hour;
    private int m_Minute;
    private Date m_Date=new Date();

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

        ImageButton buttonPlusInterests = (ImageButton) findViewById(R.id.btnPlusInterests);
        buttonPlusInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m_Intent = new Intent(AddEditEventScreen.this, KeepTouchFriendPickerActivity.class);
                startActivityForResult(m_Intent, 0);
            }

        });

        ImageButton buttonLocationPin = (ImageButton) findViewById(R.id.btnLocationPin);
        buttonLocationPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
           m_ChosenKeepTouchAttendies = (ArrayList<FriendBean>) data.getSerializableExtra(Storage.KEEPTOUCH_FRIENDS);

            if(m_FriendDrawables == null)
            {
                m_FriendDrawables = new ArrayList<Integer>();
            }

            for (FriendBean keepTouchFriend : m_ChosenKeepTouchAttendies)
            {
                if(!m_FriendDrawables.contains(keepTouchFriend.getPhoto()))
                {
                    m_FriendDrawables.add(keepTouchFriend.getPhoto());
                }
            }

            SetFaceList(m_LinearLayoutAttendies, m_FriendDrawables);
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

    public void SetFaceList(ViewGroup layoutToSetInto, List<Integer> drawables)
    {
        LinearLayout.LayoutParams layoutParamsWithNoMargins = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 0, 0, 0);
        int index = 0;
        for(Iterator<Integer> drawable = drawables.iterator(); drawable.hasNext(); )
        {
            ImageButton faceButton = new ImageButton(thisActivity);
            if(index == 0)
                faceButton.setLayoutParams(layoutParamsWithNoMargins);
            else
                faceButton.setLayoutParams(layoutParams);
            faceButton.setImageDrawable(thisActivity.getResources().getDrawable(drawable.next()));
            faceButton.setPadding(0, 0, 0, 0);
            faceButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(thisActivity, ProfileScreen.class);
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
