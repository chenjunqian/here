package com.eason.here.about_us_activity;

import android.os.Bundle;
import android.widget.Button;

import com.eason.here.BaseActivity;
import com.eason.here.R;

/**
 * Created by Eason on 12/23/15.
 */
public class AboutUsActivity extends BaseActivity{

    private Button shareAppBtn;
    private Button reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_layout);
    }
}
