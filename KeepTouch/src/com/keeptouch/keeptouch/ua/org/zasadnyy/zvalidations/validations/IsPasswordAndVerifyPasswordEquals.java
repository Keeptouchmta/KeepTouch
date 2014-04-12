package com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.keeptouch.keeptouch.R;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Field;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.ValidationResult;

/**
 * Created by tgoldberg on 4/12/2014.
 */
public class IsPasswordAndVerifyPasswordEquals extends BaseValidation {
    private EditText m_OtherText;
    protected IsPasswordAndVerifyPasswordEquals(Context context, EditText i_OtherText) {
       super(context);
        m_OtherText = i_OtherText;
    }

    public static Validation build(Context context, EditText i_OtherText) {
        return new IsPasswordAndVerifyPasswordEquals(context, i_OtherText);
    }

   @Override
   public ValidationResult validate(Field field) {
       EditText textView = field.getTextView();

       boolean isValid = textView.getText().toString().matches(m_OtherText.getText().toString());
       return isValid ?
               ValidationResult.buildSuccess(textView)
               : ValidationResult.buildFailed(textView, mContext.getString(R.string.email_not_match));
    }
}
