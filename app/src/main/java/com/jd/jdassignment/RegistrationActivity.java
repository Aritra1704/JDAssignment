package com.jd.jdassignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ARPaul on 27-03-2017.
 */

public class RegistrationActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtUserName, edtPassword, edtConfirmPassword;
    private TextView tvDOB;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
