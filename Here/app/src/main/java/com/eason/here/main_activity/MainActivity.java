package com.eason.here.main_activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.eason.here.BaseActivity;
import com.eason.here.R;
import com.eason.here.model.IntentUtil;


public class MainActivity extends BaseActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolbar;

    private MainMapFragment mainMapFragment;
    private MainSettingFragment settingFragment;
    private NearUserListFragment nearUserListFragment;

    private final int CHANGE_TOOL_BAR_TITLE_MAIN= 0x1;
    private final int CHANGE_TOOL_BAR_TITLE_SETTING = 0X2;


    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGE_TOOL_BAR_TITLE_MAIN:
                    toolbar.setTitle("HERE");
                    break;
                case CHANGE_TOOL_BAR_TITLE_SETTING:
                    toolbar.setTitle("设置");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        initParam();
    }

    /*
        初始化控件
     */
    private void initView(Bundle savedInstanceState) {
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        //将toolbar与Drawerlayout绑定起来
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.action_settings){

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    /**
     * 初始化参数，及相应的布局设置
     */
    private void initParam(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mainMapFragment = new MainMapFragment();
        settingFragment = new MainSettingFragment();
        nearUserListFragment = new NearUserListFragment();
        transaction.replace(R.id.main_fragment_frame_layout,mainMapFragment);
        transaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 切换fragment,切换到fragmentIndex相应的fragment
     * @param fragmentIndex
     */
    public void setFragmentTransaction(int fragmentIndex){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Message msg = new Message();

        switch (fragmentIndex){

            case IntentUtil.MAIN_MAP_FRAGMENT:
                if (mainMapFragment==null){
                    mainMapFragment = new MainMapFragment();
                }
                transaction.replace(R.id.main_fragment_frame_layout, mainMapFragment);
                msg.what = CHANGE_TOOL_BAR_TITLE_MAIN;
                break;

            case IntentUtil.SETTING_FRAGMENT:

                if (settingFragment==null){
                    settingFragment = new MainSettingFragment();
                }
                transaction.replace(R.id.main_fragment_frame_layout, settingFragment);
                break;

            case IntentUtil.NEAR_USER_FRAGMENT:
                if (nearUserListFragment==null){
                    nearUserListFragment = new NearUserListFragment();
                }
                transaction.replace(R.id.main_fragment_frame_layout,nearUserListFragment);
                msg.what = CHANGE_TOOL_BAR_TITLE_SETTING;
                break;
        }

        handler.sendMessage(msg);
        transaction.commit();
    }
}