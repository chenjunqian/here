package com.eason.here.main_activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.eason.here.R;


public class MainActivity extends Activity {

    private DrawerLayout drawerLayout;
    private ToggleButton topBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        topBtn = (ToggleButton)findViewById(R.id.main_top_menu_button);

        topBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }else{
                    drawerLayout.closeDrawers();
                }
            }
        });
    }



}
