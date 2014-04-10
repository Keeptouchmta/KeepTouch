package com.keeptouch.keeptouch.login;

import com.keeptouch.keeptouch.R;
import com.keeptouch.keeptouch.R.id;
import com.keeptouch.keeptouch.R.layout;
import com.keeptouch.keeptouch.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Field;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Form;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.FormUtils;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.ToastValidationFailedRenderer;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.ValidationFailedRenderer;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.InRange;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.IsEmail;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.NotEmpty;

import java.util.Map;

public class LoginActivity extends Activity {

    SharedPreferences m_Prefences;
    private EditText m_Email;
    private EditText m_Password;
    private Button m_Submit;

    // Form used for validation
    private Form m_Form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        System.out.println("on create for login activity");

        initFields();
        initValidationForm();

        ImageButton buttonFacebookLogin = (ImageButton) findViewById(id.imageButtonLoginWithFacebok);
        buttonFacebookLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), FacebookAuth.class);
                startActivity(intent);
                ((Activity)v.getContext()).finish();

            }
        });

        ImageButton buttonLogin = (ImageButton) findViewById(id.imageButtonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (m_Form.isValid())
                {
                    Intent intent = new Intent(v.getContext(), UserConfirmationActivity.class);
                    startActivity(intent);
                    ((Activity)v.getContext()).finish();
                }
            }
        });

        TextView textViewSignUp =  (TextView) findViewById(R.id.textSignUp);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initValidationForm() {
        m_Form = new Form(this);
        m_Form.setValidationFailedRenderer( new TextViewValidationFailedRenderer(this));
        m_Form.addField(Field.using(m_Email).validate(NotEmpty.build(this)).validate(IsEmail.build(this)));
        m_Form.addField(Field.using(m_Password).validate(NotEmpty.build(this)));
    }

    private void initFields() {
        m_Email = (EditText) findViewById(R.id.textEmailAddress);
        m_Password = (EditText) findViewById(R.id.textPassword);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
