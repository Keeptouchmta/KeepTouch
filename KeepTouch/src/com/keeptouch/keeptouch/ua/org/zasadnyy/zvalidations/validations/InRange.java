
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Vitaliy Zasadnyy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations;

import android.content.Context;
import android.widget.EditText;
import android.widget.EditText;
import com.keeptouch.keeptouch.R;
import com.keeptouch.keeptouch.R.id;
import com.keeptouch.keeptouch.R.layout;
import com.keeptouch.keeptouch.R.menu;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Field;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.ValidationResult;

/**
 * Created by vitaliyzasadnyy on 04.08.13.
 */
public class InRange extends BaseValidation {

    private int mMin;
    private int mMax;

    private InRange(Context context, int min, int max) {
        super(context);
        mMin = min;
        mMax = max;
    }

    public static Validation build(Context context, int min, int max) {
        return new InRange(context, min, max);
    }

    @Override
    public ValidationResult validate(Field field) {
        EditText textView = field.getTextView();
        boolean isValid = false;
        try {
            int value = Integer.parseInt(textView.getText().toString());
            isValid = (value > mMin) && (value < mMax);
        } catch (NumberFormatException ignored) {
        }
        return isValid ?
            ValidationResult.buildSuccess(textView)
            : ValidationResult.buildFailed(textView, mContext.getString(R.string.zvalidations_not_in_range, mMin, mMax));
    }
}
