package no.ntnu.stud.erikfossum.quizapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText museridView;
    private EditText mPasswordView;
    protected static User user;
    private UserLoginTask mAuthTask = null;
    public static final String PREFERENCES_NAME = "dataStorage";
    public static final String USER_NAME_KEY ="username";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        museridView = (EditText)findViewById(R.id.username);
        mPasswordView = (EditText)findViewById(R.id.password);
        Button button= (Button) findViewById(R.id.logInButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Button button2= (Button) findViewById(R.id.createAccount);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

    }


    public User login() throws IOException {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://10.0.2.2:8080/QuizServer/api/auth/login");
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3000);
            JsonReader jr = new JsonReader(new InputStreamReader(new BufferedInputStream(con.getInputStream())));
            user = loadUser(jr);
            con.getInputStream().close();
        } finally {
            if(con != null) con.disconnect();
        }

        return user;
    }


    private User loadUser(JsonReader jr) throws IOException {
        User result = new User();

        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "userid":
                    result.setUserid(jr.nextString());
                    break;
                default:
                    jr.skipValue();
            }
        }
        jr.endObject();

        return result;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String mPassword;

        UserLoginTask(String userName, String password) {
            username = userName;
            mPassword = password;

            // Setup authentication
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, mPassword.toCharArray());
                }
            });
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return login() != null;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                setResult(RESULT_OK);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_NAME_KEY,museridView.getText().toString());
                editor.commit();
                finish();
            } else {
                mPasswordView.setError("This password is incorrect");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        museridView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = museridView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("This password is too short");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            museridView.setError("This field is required");
            focusView = museridView;
            cancel = true;
        } else if (!isUseridValid(username)) {
            museridView.setError("This username is invalid");
            focusView = museridView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isUseridValid(String userid) {
        //TODO: Replace this with your own logic
        return userid.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }


}

