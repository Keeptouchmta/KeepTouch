package com.keeptouch.zvalidations.validations;

import android.content.Context;
import android.widget.EditText;

import com.keeptouch.R;
import com.keeptouch.server.ServerConnection;
import com.keeptouch.zvalidations.Field;
import com.keeptouch.zvalidations.ValidationResult;
import com.keeptouch.zvalidations.ValidationResult;

/**
 * Created by tgoldberg on 4/12/2014.
 */
public class IsUserRegister extends BaseValidation {

    private IsUserRegister(Context context) {
        super(context);
    }

    public static Validation build(Context context) {
        return new IsUserRegister(context);
    }

    @Override
    public ValidationResult validate(Field field) {
        EditText textView = field.getTextView();
        ServerConnection m_ServerConnection = ServerConnection.getConnection();
        int m_UserId = (int) (m_ServerConnection.CheckUserExists(textView.getText().toString()));

        boolean isValid = (m_UserId == ServerConnection.USER_EXITS) ? false : true;
        return isValid ?
                ValidationResult.buildSuccess(textView)
                : ValidationResult.buildFailed(textView, mContext.getString(R.string.zvalidations_already_registered));
    }
}
