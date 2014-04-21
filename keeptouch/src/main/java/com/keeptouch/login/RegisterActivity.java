package com.keeptouch.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.keeptouch.ProfileActivity;
import com.keeptouch.R;
import com.keeptouch.Utils.Progress;
import com.keeptouch.Utils.Storage;
import com.keeptouch.server.ServerConnection;
import com.keeptouch.zvalidations.Field;
import com.keeptouch.zvalidations.Form;
import com.keeptouch.zvalidations.TextViewValidationFailedRenderer;
import com.keeptouch.zvalidations.validations.IsEmail;
import com.keeptouch.zvalidations.validations.IsPasswordAndVerifyPasswordEquals;
import com.keeptouch.zvalidations.validations.IsUserRegister;
import com.keeptouch.zvalidations.validations.NotEmpty;

import org.json.JSONObject;

import static com.keeptouch.Utils.Storage.COMMIT;
import static com.keeptouch.Utils.Storage.USER_EMAIL;
import static com.keeptouch.Utils.Storage.USER_ID;
import static com.keeptouch.Utils.Storage.USER_PASSWORD;
import static com.keeptouch.Utils.Storage.saveInt;
import static com.keeptouch.Utils.Storage.saveString;

/**
 * Activity for registration
 */
public class RegisterActivity extends Activity 
{
    private EditText m_Email;
    private EditText m_Password;
    private EditText m_verifyPassword;
    private Intent m_Intent;
    // Form used for validation
    private Form m_Form;
    private int m_UserId;
    private ServerConnection m_ServerConnection;
    private String m_FacebookId;
    private Facebook m_Facebook;
    private String m_DbFacebookId;
    private Boolean m_HandledNewUserWithFacebook = false;
    private Boolean m_FacebookAuthorized;
    //private FriendPickerFragment friendPickerFragment;

    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        System.out.println("on create for Register activity");
        m_Intent = getIntent();
        m_FacebookAuthorized = m_Intent.hasExtra(Storage.FB_AUTH);
        if(m_FacebookAuthorized)
        {
            new RegisterUserViaFacbookTask().execute();
        }

        initFields();
        initValidationForm();
        final Button registerButton = (Button)findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View i_View)
            {
                if (m_Form.isValid()) {
                    new RegisterUserTask().execute(m_Email.getText().toString(),
                            m_Password.getText().toString(), m_verifyPassword.getText().toString());
                }

            }
        });

        final ImageButton registerViaFacebookButton = (ImageButton)findViewById(R.id.imageButtonRegisterWithFacebok);
        registerViaFacebookButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View i_View)
            {
                Intent intent = new Intent(RegisterActivity.this, FacebookAuth.class);
                intent.putExtra(Storage.REGISTER_FB, true);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });


	}

    public enum RegisterUserResult {
        Failed,
        Success
    }

    private class RegisterUserTask extends AsyncTask<String, Void, RegisterUserResult> {

        ProgressDialog m_ProgressDialog;
        String m_Email;
        String m_Passsword ;
        String m_verifyPassword;

        @Override
        protected void onPostExecute(RegisterUserResult registerUserResult) {

            super.onPostExecute(registerUserResult);

            if(registerUserResult.equals(RegisterUserResult.Failed)){
                Toast.makeText(RegisterActivity.this,
                        "Registration error occured",
                        Toast.LENGTH_LONG).show();
            }
            else if(registerUserResult.equals(RegisterUserResult.Success)){
                saveString(USER_EMAIL, m_Email, !COMMIT);
                saveString(USER_PASSWORD, m_Passsword, !COMMIT);
                saveInt(USER_ID, m_UserId, COMMIT);
                System.out.println("Register :" + m_Email);

            }
        }

        @Override
        protected RegisterUserResult doInBackground(String... strings) {

            RegisterUserResult registerUserResult = RegisterUserResult.Failed;
            m_Email = strings[0];
            m_Passsword = strings[1];
            m_ServerConnection = ServerConnection.getConnection();
            Integer userId = 0;
            m_ServerConnection.SetInitialDetails(m_Email, m_Passsword, RegisterActivity.this);
            int failCount = 0;
            while (failCount < 3) {
                try {
                    registerUserResult = m_ServerConnection.RegisterNewUser(m_Email, m_Passsword,userId);
                    break;
                } catch (Exception e) {
                    failCount++;
                    continue;
                } catch (AssertionError a) {
                    failCount++;
                    continue;
                }
            }

            if (failCount > 2)
            {
                Toast.makeText(RegisterActivity.this, "Cannot register, check internet connection", Toast.LENGTH_LONG).show();
                (RegisterActivity.this).finish();
            }

            return registerUserResult;
        }

        @Override
        protected void onPreExecute()
        {
            Progress.show(RegisterActivity.this);
        }
    }

    private void initValidationForm() {
        m_Form = new Form(this);
        m_Form.setValidationFailedRenderer( new TextViewValidationFailedRenderer(this));
        m_Form.addField(Field.using(m_Email).validate(NotEmpty.build(this)).validate(IsEmail.build(this)).validate(IsUserRegister.build(this)));
        m_Form.addField(Field.using(m_Password).validate(NotEmpty.build(this)));
        m_Form.addField(Field.using(m_verifyPassword).validate(NotEmpty.build(this)).validate(IsPasswordAndVerifyPasswordEquals.build(this,m_Password)));

    }

    private void initFields() {
        m_Email = (EditText) findViewById(R.id.textEmailAddress);
        m_Password = (EditText) findViewById(R.id.textPassword);
        m_verifyPassword = (EditText) findViewById(R.id.textReTypePassword);
    }

    private class RegisterUserViaFacbookTask extends AsyncTask<String, Void, RegisterUserResult> {

        @Override
        protected void onPostExecute(RegisterUserResult registerUserResult) {

            super.onPostExecute(registerUserResult);

            if(registerUserResult.equals(RegisterUserResult.Failed)){
                Toast.makeText(RegisterActivity.this,
                        "Registration with facebook error occured",
                        Toast.LENGTH_LONG).show();
            }
            else if(registerUserResult.equals(RegisterUserResult.Success)){
                saveInt(USER_ID, m_UserId, COMMIT);
                System.out.println("Register via facebook :" + m_Email);
                m_ServerConnection.GetFacebookDetails(m_UserId, m_FacebookId); // to decide what to return
                Boolean facebookAuthSuccess = m_ServerConnection.UpdateUserDetailsViaFb();// send to object to that method

                if (facebookAuthSuccess)
                {
                    m_Intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    startActivity(m_Intent);
                    RegisterActivity.this.finish();

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Could not login to facebook.", Toast.LENGTH_LONG).show();
                }

            }
        }


        @Override
        protected RegisterUserResult doInBackground(String... strings) {
            RegisterUserResult registerUserResult;
            m_ServerConnection = ServerConnection.getConnection();
            getFacebookId();
            return handleNewUserWithFacebook();
        }
    }

    private RegisterUserResult handleNewUserWithFacebook()
    {
        m_HandledNewUserWithFacebook = true;
        return m_ServerConnection.RegisterNewUserViaFb(m_FacebookId,m_UserId);
    }

    private void getFacebookId()
    {
        m_Facebook = ServerConnection.GetFacebookConnection();
        JSONObject userJson;
        try
        {
            userJson = Util.parseJson(m_Facebook.request("me"));
            m_FacebookId = (String)userJson.get("id");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
