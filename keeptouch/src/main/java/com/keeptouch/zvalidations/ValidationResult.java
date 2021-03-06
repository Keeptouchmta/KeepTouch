
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

package com.keeptouch.zvalidations;

import android.widget.EditText;

/**
 * Created by vitaliyzasadnyy on 01.08.13.
 */
public class ValidationResult {

    private final boolean mIsValid;
    private final String mMessage;
    private final EditText mTextView;

    public static ValidationResult buildSuccess(EditText textView) {
        return new ValidationResult(true, "", textView);
    }

    public static ValidationResult buildFailed(EditText textView, String message) {
        return new ValidationResult(false, message, textView);
    }

    private ValidationResult(boolean isValid, String message, EditText textView) {
        mIsValid = isValid;
        mMessage = message;
        mTextView = textView;
    }

    public boolean isValid() {
        return mIsValid;
    }

    public String getMessage() {
        return mMessage;
    }

    public EditText getTextView() {
        return mTextView;
    }
}
