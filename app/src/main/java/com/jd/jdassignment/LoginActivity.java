package com.jd.jdassignment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.utilitieslib.LogUtils;
import com.jd.jdassignment.dataaccess.LSCPConstants;
import com.jd.jdassignment.dataobject.UserDO;

import static com.jd.jdassignment.common.AppConstants.ACTION_CHECK_LOGIN;
import static com.jd.jdassignment.common.AppConstants.BUNDLE_DASHBOARD;
import static com.jd.jdassignment.common.ApplicationInstance.LOADER_CHECK_LOGIN;
import static com.jd.jdassignment.dataaccess.LSCPConstants.CONTENT_URI_USER;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    // UI references.
    private EditText edtUsername, edtPassword;
    private TextView tvRegister;
    private Button btnLogin;
    private View pbLogin;
    private View mLoginFormView;
    String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseControls();

        bindControls();
    }

    void bindControls() {
        edtUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        tvRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        edtUsername.setError(null);
        edtPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            focusView = edtPassword;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError(getString(R.string.error_field_required));
            focusView = edtUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            UserDO objUserDO = new UserDO();
            objUserDO.UserName = username;
            objUserDO.Password = password;

            Bundle bundle = new Bundle();
            bundle.putSerializable(ACTION_CHECK_LOGIN, objUserDO);

            if(getSupportLoaderManager().getLoader(LOADER_CHECK_LOGIN) == null)
                getSupportLoaderManager().initLoader(LOADER_CHECK_LOGIN, bundle, this);
            else
                getSupportLoaderManager().restartLoader(LOADER_CHECK_LOGIN, bundle, this);
        }
    }

    boolean isPasswordValid(String password) {
        if(password.length() >= 8)
            return true;
        return false;
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            pbLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOADER_CHECK_LOGIN:
                UserDO objUserDO = (UserDO) bundle.getSerializable(ACTION_CHECK_LOGIN);
                LogUtils.infoLog(TAG, objUserDO.UserName + " " + objUserDO.Password);
                return new CursorLoader(this, CONTENT_URI_USER,
                        null,
                        UserDO.USERNAME + LSCPConstants.TABLE_QUES + LSCPConstants.TABLE_AND +
                        UserDO.PASSWORD + LSCPConstants.TABLE_QUES,
                        new String[]{objUserDO.UserName, objUserDO.Password},
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_CHECK_LOGIN:
                if(data instanceof Cursor) {
                    Cursor cursor = (Cursor) data;
                    LogUtils.infoLog(TAG, "inside data");
                    if(cursor != null && cursor.moveToFirst()) {
                        LogUtils.infoLog(TAG, "inside cursor");
                        UserDO objUserDO = new UserDO();
                        objUserDO.UserId = cursor.getString(cursor.getColumnIndex(UserDO.USERID));
                        objUserDO.Email = cursor.getString(cursor.getColumnIndex(UserDO.EMAIL));
                        objUserDO.FirstName = cursor.getString(cursor.getColumnIndex(UserDO.FIRSTNAME));
                        objUserDO.LastName = cursor.getString(cursor.getColumnIndex(UserDO.LASTNAME));
                        objUserDO.Email = cursor.getString(cursor.getColumnIndex(UserDO.PASSWORD));
                        objUserDO.Phone = cursor.getString(cursor.getColumnIndex(UserDO.PHONE));
                        objUserDO.Dob = cursor.getString(cursor.getColumnIndex(UserDO.DOB));

                        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        intent.putExtra(BUNDLE_DASHBOARD, objUserDO);
                        startActivity(intent);
                    } else {
                        showProgress(false);
                    }
                } else {
                    showProgress(false);
                }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    void initialiseControls() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        tvRegister = (TextView) findViewById(R.id.tvRegister);

        mLoginFormView = findViewById(R.id.login_form);
        pbLogin = (ProgressBar)findViewById(R.id.pbLogin);
    }
}

