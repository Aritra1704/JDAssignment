package com.jd.jdassignment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.ValidationUtils;
import com.jd.jdassignment.dataaccesslayer.UserDA;
import com.jd.jdassignment.dataobject.UserDO;

import java.util.Calendar;
import java.util.TimeZone;

import static com.jd.jdassignment.common.AppConstants.STATUS_SUCCESS;

/**
 * Created by ARPaul on 27-03-2017.
 */

public class RegistrationActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtUserName, edtPhone, edtPassword, edtConfirmPassword, edtEmail;
    private TextView tvDOB;
    private ProgressBar pbRegister;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialiseControls();

        bindControls();
    }

    void bindControls() {
        edtFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtLastName.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtUserName.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtPhone.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtEmail.requestFocus();
                    return true;
                }
                return false;
            }
        });

        edtEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    edtConfirmPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edtConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    registerUser();
                    return true;
                }
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    DatePickerDialog datePicker = new DatePickerDialog(RegistrationActivity.this,
                            datePickerListener,
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));

                    datePicker.setCancelable(false);
                    datePicker.setTitle("Select date");

                    datePicker.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {

                }
            }
        });
    }

    void registerUser() {
        boolean cancel = false;
        View focusView = null;

        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String userName = edtUserName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String dob = CalendarUtils.getDateinPattern(tvDOB.getText().toString(), CalendarUtils.DATE_FORMAT_WITH_COMMA, CalendarUtils.DATE_FORMAT1);
        String password = edtPassword.getText().toString();
        String confPassword = edtConfirmPassword.getText().toString();
        if(TextUtils.isEmpty(firstName)) {
            edtFirstName.setError(getString(R.string.error_field_required));
            focusView = edtFirstName;
            cancel = true;
        } else if(TextUtils.isEmpty(lastName)) {
            edtLastName.setError(getString(R.string.error_field_required));
            focusView = edtLastName;
            cancel = true;
        } else if(TextUtils.isEmpty(userName)) {
            edtUserName.setError(getString(R.string.error_field_required));
            focusView = edtUserName;
            cancel = true;
        } else if(TextUtils.isEmpty(phone)) {
            edtPhone.setError(getString(R.string.error_field_required));
            focusView = edtPhone;
            cancel = true;
        } else if(TextUtils.isEmpty(dob)) {
            tvDOB.setError(getString(R.string.error_field_required));
            focusView = tvDOB;
            cancel = true;
        } else if(TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.error_field_required));
            focusView = edtEmail;
            cancel = true;
        } else if(ValidationUtils.validateEmail(email)) {
            edtEmail.setError(getString(R.string.error_invalid_email));
            focusView = edtEmail;
            cancel = true;
        } else if(TextUtils.isEmpty(password)) {
            edtPassword.setError(getString(R.string.error_field_required));
            focusView = edtPassword;
            cancel = true;
        } else if(TextUtils.isEmpty(confPassword)) {
            edtConfirmPassword.setError(getString(R.string.error_field_required));
            focusView = edtConfirmPassword;
            cancel = true;
        } else if(!password.equalsIgnoreCase(confPassword)) {
            edtConfirmPassword.setError(getString(R.string.passwords_dont_match));
            focusView = edtConfirmPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            showProgress(false);
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            final UserDO objUserDO = new UserDO();
            objUserDO.UserId = userName + dob;
            objUserDO.FirstName = firstName;
            objUserDO.LastName = lastName;
            objUserDO.UserName = userName;
            objUserDO.Password = password;
            objUserDO.Phone = phone;
            objUserDO.Email = email;
            objUserDO.Dob = dob;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String isCreated = new UserDA().insertUser(RegistrationActivity.this, objUserDO);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isCreated.equalsIgnoreCase(STATUS_SUCCESS)) {
                                showProgress(false);
                                finish();
                            } else
                                showProgress(false);
                        }
                    });

                }
            }).start();
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

            pbRegister.setVisibility(show ? View.VISIBLE : View.GONE);
            pbRegister.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbRegister.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbRegister.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            String date = year1 + "-" + month1 + "-" + day1;
            tvDOB.setText(CalendarUtils.getDateinPattern(date, CalendarUtils.DATE_FORMAT1, CalendarUtils.DATE_FORMAT_WITH_COMMA));
        }
    };

    void initialiseControls() {
        edtFirstName        = (EditText) findViewById(R.id.edtFirstName);
        edtLastName         = (EditText) findViewById(R.id.edtLastName);
        edtUserName         = (EditText) findViewById(R.id.edtUserName);
        edtPhone            = (EditText) findViewById(R.id.edtPhone);
        edtPassword         = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword  = (EditText) findViewById(R.id.edtConfirmPassword);
        edtEmail            = (EditText) findViewById(R.id.edtEmail);

        tvDOB               = (TextView) findViewById(R.id.tvDOB);

        btnRegister         = (Button) findViewById(R.id.btnRegister);

        pbRegister          = (ProgressBar) findViewById(R.id.pbRegister);
    }
}
