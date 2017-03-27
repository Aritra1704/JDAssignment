package com.jd.jdassignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.utilitieslib.CalendarUtils;
import com.jd.jdassignment.dataobject.UserDO;

import static com.jd.jdassignment.common.AppConstants.BUNDLE_DASHBOARD;

/**
 * Created by Aritra on 27-03-2017.
 */

public class DashBoardActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvDOB;

    private UserDO objUserDO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(getIntent().hasExtra(BUNDLE_DASHBOARD)) {
            objUserDO = (UserDO) getIntent().getExtras().get(BUNDLE_DASHBOARD);
        }

        initialiseControls();

        bindControls();
    }

    void bindControls() {
        if(objUserDO != null) {
            tvName.setText(objUserDO.FirstName + " " + objUserDO.LastName);
            tvPhone.setText(objUserDO.Phone);
            tvDOB.setText(CalendarUtils.getDateinPattern(objUserDO.Dob, CalendarUtils.DATE_FORMAT1, CalendarUtils.DATE_FORMAT_WITH_COMMA));
        }
    }

    void initialiseControls() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
    }
}
