package com.keeptouch.keeptouch.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by tgoldberg on 4/11/2014.
 */
public class Progress {

    private static ProgressDialog m_Progress;

    public static void show(Context context) {
        m_Progress = ProgressDialog.show(context,
                "", "Loading data...");
     }

    public static void dismiss(Context context) {
        m_Progress.dismiss();
    }


}
