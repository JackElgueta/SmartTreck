package com.example.jack.smarttreck;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.jack.smarttreck.R.id.url;

public class RegisterActivity extends AppCompatActivity {

    private RegisterLoginTask mAuthTask = null;
    private EditText mFullnameView, mNicknameView, mPasswordView, mRetypedPasswordView, mEmailView;
    private View mRegisterFormView, mProgressView;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullnameView = (EditText) findViewById(R.id.fullname);
        mNicknameView = (EditText) findViewById(R.id.nick);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRetypedPasswordView = (EditText) findViewById(R.id.retype_password);
        mEmailView = (EditText) findViewById(R.id.email);


        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        Button buttonRegister = (Button) findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mFullnameView.setError(null);
        mNicknameView.setError(null);
        mPasswordView.setError(null);
        mRetypedPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String fullname = mFullnameView.getText().toString();
        String nickname = mNicknameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String retypedPassword = mRetypedPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            mAuthTask = new RegisterLoginTask(email, fullname, nickname, password);
            mAuthTask.execute((Void) null);
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private class RegisterLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mFullName;
        private final String mNickname;
        private final String mPassword;

        private RegisterLoginTask(String email, String fullname, String nickname, String password) {
            mPassword = password;
            mEmail = email;
            mFullName = fullname;
            mNickname = nickname;
        }


        protected Boolean doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            String jsonString = "{\"name\": \"" + mNickname + "\" , \"pass\": \"" + mPassword + "\", \"mail\": \"" +mEmail+ "\", \"field_nombre_completo\": { \"und\": [{ \"value\": \"" +mFullName+ "\"}]}}";

            RequestBody body = RequestBody.create(JSON, jsonString);

            Request request = new Request.Builder()
                    .url("http://itfactory.cl/smartTrekking/api/user/register")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(response.isSuccessful()){

                    Intent goToMain = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToMain);

                }
                else{

                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError("Contraseña incorrecta");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
