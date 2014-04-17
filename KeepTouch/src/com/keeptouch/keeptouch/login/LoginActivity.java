package com.keeptouch.keeptouch.login;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;
import com.keeptouch.keeptouch.ProfileScreen;
import com.keeptouch.keeptouch.R;
import com.keeptouch.keeptouch.R.id;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.keeptouch.keeptouch.Utils.Progress;
import com.keeptouch.keeptouch.Utils.Storage;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Field;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.Form;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.IsEmail;
import com.keeptouch.keeptouch.ua.org.zasadnyy.zvalidations.validations.NotEmpty;
import com.keeptouch.keeptouch.server.ServerConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import static com.keeptouch.keeptouch.Utils.Storage.*;

public class LoginActivity extends Activity {

    SharedPreferences m_Prefences;
    private EditText m_Email;
    private EditText m_Password;
    private Button m_Submit;
    private ServerConnection m_Connection;
    private int m_UserId;
    private Bundle m_Extras;
    private Intent m_Intent;
    private AsyncFacebookRunner m_AsyncFb;

    // Form used for validation
    private Form m_Form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        System.out.println("on create for login activity");
        Storage.init(LoginActivity.this);
        initFields();
        initValidationForm();

        final ImageButton buttonLogin = (ImageButton) findViewById(id.imageButtonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (m_Form.isValid()){
                    new ConfirmUserTask().execute(m_Email.getText().toString(),
                            m_Password.getText().toString());
                }
            }
        });

        ImageButton buttonFacebookLogin = (ImageButton) findViewById(id.imageButtonLoginWithFacebok);
        buttonFacebookLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new LoginViaFbTask().execute();

            }
        });

        TextView textViewSignUp =  (TextView) findViewById(R.id.textSignUp);
        String htmlString="<u>Sign up</u>";
        textViewSignUp.setText(Html.fromHtml(htmlString));
        textViewSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
                ((Activity)v.getContext()).finish();
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

    public enum ConfirmUserResult {
        Failed,
        Success
    }

    private class ConfirmUserTask extends AsyncTask<String, Void, ConfirmUserResult>{

        ProgressDialog m_ProgressDialog;
        String m_Email;
        String m_Passsword ;

        @Override
        protected void onPostExecute(ConfirmUserResult confirmUserResult) {

            super.onPostExecute(confirmUserResult);

            if(confirmUserResult.equals(ConfirmUserResult.Failed)){
                Progress.dismiss(LoginActivity.this);
                Toast.makeText(LoginActivity.this,
                        "Faild to login." +
                                "Please check your email and password.",
                        Toast.LENGTH_LONG).show();
            }
            else if(confirmUserResult.equals(ConfirmUserResult.Success)){
                saveString(USER_EMAIL, m_Email, !COMMIT);
                saveString(USER_PASSWORD, m_Passsword, !COMMIT);
                saveInt(USER_ID, m_UserId, COMMIT);
                System.out.println("login :" + m_Email);

                if (m_Extras != null){
                    Progress.dismiss(LoginActivity.this);
                    m_Intent = new Intent(LoginActivity.this, ProfileScreen.class);
                    startActivity(m_Intent);
                    ((Activity)LoginActivity.this).finish();
                }
            }
        }

        @Override
        protected ConfirmUserResult doInBackground(String... strings) {

            m_Email = strings[0];
            m_Passsword = strings[1];
            m_Connection = ServerConnection.getConnection();
            ConfirmUserResult confirmUserResult =  m_Connection.Login(m_Email,m_Passsword);
            m_UserId = m_Connection.getLocalUserId();
            m_Connection.UpdateUserInitially();
            return confirmUserResult;
        }

        @Override
        protected void onPreExecute()
        {
            Progress.show(LoginActivity.this);
        }
    }

    class LoginViaFbTask extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog m_ProgDialog;

        @Override
        protected void onPreExecute()
        {
            Progress.show(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            m_Connection = ServerConnection.getConnection();
            Integer userId = m_Connection.LoginViaFb();
            Intent intent;
            if (!(userId == -1))
            {
                Storage.saveInt(Storage.USER_ID, userId, true);
                intent = new Intent(LoginActivity.this, FacebookAuth.class);
                intent.putExtra(Storage.FB_LOGIN, true);
                startActivity(intent);
                LoginActivity.this.finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Cannot login, try again later.", Toast.LENGTH_LONG).show();
                m_AsyncFb = ServerConnection.GetFacebookAsyncRunner();
                if (m_AsyncFb != null)
                {
                    logoutFacebook();
                }
                Progress.dismiss(LoginActivity.this);

            }

            return  null;
        }
    }

    public void logoutFacebook()
    {
        Storage.saveString(Storage.ACCESS_TOKEN, null, false);
        Storage.saveLong("access_expires", 0, true);
        m_AsyncFb.logout(LoginActivity.this, new AuthRequestListener());
    }

    class AuthRequestListener implements AsyncFacebookRunner.RequestListener
    {

        @Override
        public void onComplete(String response, Object state) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onIOException(IOException e, Object state) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFileNotFoundException(FileNotFoundException e,
                                            Object state) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onMalformedURLException(MalformedURLException e,
                                            Object state) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFacebookError(FacebookError e, Object state) {
            // TODO Auto-generated method stub

        }

    }
}
