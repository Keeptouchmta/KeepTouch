package com.keeptouch.keeptouch;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.*;
import android.widget.DatePicker;

/**
 * Class for presenting a date picker
 */
public class DatePickerFragment extends DialogFragment {
    OnDateSetListener m_OndateSet;
    private int m_Year, m_Month, m_Day;

    public DatePickerFragment() {
    }

    public void setCallBack(OnDateSetListener ondate) {
        m_OndateSet = ondate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        m_Year = args.getInt("year");
        m_Month = args.getInt("month");
        m_Day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), m_OndateSet, m_Year, m_Month, m_Day);
    }
}