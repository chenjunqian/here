package com.eason.marker.main_activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.eason.marker.R;
import com.eason.marker.about_us_activity.AboutUsActivity;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.emchat.applib.controller.HXSDKHelper;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.util.LogUtil;
import com.eason.marker.util.WidgetUtil.GreenToast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements EMEventListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolbar;

    private MainMapFragment mainMapFragment;
    private MainProfileFragment settingFragment;
    private NearUserListFragment nearUserListFragment;
    private CurrentMarkerListFragment currentPostFragment;

    private static final int CHANGE_TOOL_BAR_TITLE_MAIN = 0x1;
    private static final int CHANGE_TOOL_BAR_TITLE_SETTING = 0X2;
    /**
     * MainMapFragment 获取帖子为空
     */
    public static final int NONE_VALID_POST = 0x3;
    private static final int CHANGE_TOOL_BAR_TITLE_NEAR = 0X4;
    private static final int CHANGE_TOOL_BAR_TITLE_MINE = 0X5;
    public static final int NONE_VALID_MORE_POST = 0x6;

    /**
     * 判断toolbar是否要显示刷新按键
     */
    private boolean isShowRefreshView = true;

    /**
     * 标记当前显示的是哪个fragment
     */
    public static int FRAGMENT_TAG = 0;

    public static List<Post> postListItem;

    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_TOOL_BAR_TITLE_MAIN:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.main_map_page);

                    break;
                case CHANGE_TOOL_BAR_TITLE_SETTING:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.my_profile_page_title);
                    break;
                case CHANGE_TOOL_BAR_TITLE_NEAR:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.near_list_page_title);
                    break;
                case CHANGE_TOOL_BAR_TITLE_MINE:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.current_list_page_title);
                    break;
                case NONE_VALID_POST:
                    GreenToast.makeText(MainActivity.this, "附近还没人标记过哦，还不先码一个", Toast.LENGTH_LONG).show();
                    break;
                case NONE_VALID_MORE_POST:
                    GreenToast.makeText(MainActivity.this, "没有更多的标记啦", Toast.LENGTH_LONG).show();
                    break;
                case ErroCode.ERROR_CODE_REQUEST_FORM_INVALID:
                    GreenToast.makeText(MainActivity.this, "获取附近的标记似乎除了点问题，但是又不知道是为什么。。。", Toast.LENGTH_LONG).show();
                    break;
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
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.main_map_page);
        toolbar.setTitleTextColor(getResources().getColor(R.color.universal_white));
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_refresh:

                        mainMapFragment.getPost();
                        break;

                    case R.id.action_about_us:
                        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        //将toolbar与Drawerlayout绑定起来
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.main_map_page,
                R.string.action_settings) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (isShowRefreshView) {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_main_without_refresh, menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 初始化参数，及相应的布局设置
     */
    private void initParam() {
        setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
        EMChatUtil.autoReConnectEMChat();
        if (LoginStatus.getIsUserMode()){
            loginEMChat(LoginStatus.getUser().getUserid(),LoginStatus.getUser().getPassword());
        }
    }

    public Fragment getFragment(int type){
        switch (type){
            case IntentUtil.MAIN_MAP_FRAGMENT:
                return mainMapFragment;
            case IntentUtil.NEAR_USER_FRAGMENT:
                return nearUserListFragment;
            case IntentUtil.CURRENT_POST_FRAGMENT:
                return currentPostFragment;
            case IntentUtil.PROFLIE_FRAGMENT:
                return settingFragment;
        }

        return null;
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * 切换fragment,切换到fragmentIndex相应的fragment
     *
     * @param fragmentIndex
     */
    public void setFragmentTransaction(int fragmentIndex) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Message msg = new Message();
        if (fragmentIndex != IntentUtil.MAIN_TO_LOGIN_PAGE){//跳转到注册登录页面不需要切换fragment
            hideFragment(transaction);
        }
        switch (fragmentIndex) {

            //切换到主页
            case IntentUtil.MAIN_MAP_FRAGMENT:
                if (mainMapFragment == null) {
                    mainMapFragment = new MainMapFragment();
                    transaction.add(R.id.main_fragment_frame_layout, mainMapFragment);
                }else{
                    transaction.show(mainMapFragment);
                }

                msg.what = CHANGE_TOOL_BAR_TITLE_MAIN;
                isShowRefreshView = true;
                FRAGMENT_TAG = IntentUtil.MAIN_MAP_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                break;

            //切换到设置页面
            case IntentUtil.PROFLIE_FRAGMENT:

                if (settingFragment == null) {
                    settingFragment = new MainProfileFragment();
                    transaction.add(R.id.main_fragment_frame_layout, settingFragment);
                }else{
                    transaction.show(settingFragment);
                }

                msg.what = CHANGE_TOOL_BAR_TITLE_SETTING;
                isShowRefreshView = false;
                FRAGMENT_TAG = IntentUtil.PROFLIE_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                break;

            //切换到附近事件列表显示页面
            case IntentUtil.NEAR_USER_FRAGMENT:
                if (nearUserListFragment == null) {
                    nearUserListFragment = new NearUserListFragment();
                    transaction.add(R.id.main_fragment_frame_layout, nearUserListFragment);
                }else{
                    transaction.show(nearUserListFragment);
                }

                FRAGMENT_TAG = IntentUtil.NEAR_USER_FRAGMENT;
                msg.what = CHANGE_TOOL_BAR_TITLE_NEAR;
                handler.sendEmptyMessage(msg.what);
                isShowRefreshView = false;
                break;

            case IntentUtil.MAIN_TO_LOGIN_PAGE:
                /**
                 * 在这里什么都不需要做，因为只需要关闭Drawerlayout,具体的跳转操作在MainLeftFragment中
                 */
                break;

            case IntentUtil.CHAT_MAIN_PAGE:
                /**
                 * 在这里什么都不需要做，因为只需要关闭Drawerlayout,具体的跳转操作在MainLeftFragment中
                 */
                break;

            case IntentUtil.CURRENT_POST_FRAGMENT:
                if (currentPostFragment == null) {
                    currentPostFragment = new CurrentMarkerListFragment();
                    transaction.add(R.id.main_fragment_frame_layout, currentPostFragment);
                }else{
                    transaction.show(currentPostFragment);
                }

                FRAGMENT_TAG = IntentUtil.CURRENT_POST_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                isShowRefreshView = false;
                msg.what = CHANGE_TOOL_BAR_TITLE_MINE;
                handler.sendEmptyMessage(msg.what);
                break;
        }

        drawerLayout.closeDrawers();//点击Item后关闭Drawerlayout
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if (mainMapFragment!=null){
            transaction.hide(mainMapFragment);
        }
        if (settingFragment!=null){
            transaction.hide(settingFragment);
        }
        if (nearUserListFragment!=null){
            transaction.hide(nearUserListFragment);
        }
        if (currentPostFragment!=null){
            transaction.hide(currentPostFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case IntentUtil.TO_PUBLISH_PAGE:
                mainMapFragment.getPost();
                break;
            case IntentUtil.MAIN_TO_LOGIN_PAGE:
                if (LoginStatus.getIsUserMode()){
                    loginEMChat(LoginStatus.getUser().getUserid(), LoginStatus.getUser().getPassword());
                }
                break;
            case IntentUtil.CHAT_MAIN_PAGE:
                /**
                 * 这里是为了从聊天页面回来时显示主页
                 */
                setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
                MenuLeftFragment fragment = (MenuLeftFragment) getFragmentManager().
                        findFragmentById(R.id.main_left_menu_fragment);
                fragment.setItemBackground(R.id.main_page_tag_layout);
                break;
        }

    }

    private static long touchTime = 0;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (FRAGMENT_TAG != IntentUtil.MAIN_MAP_FRAGMENT) {//在不是地图fragment的时候按返回键回到地图fragment
            setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
            MenuLeftFragment fragment = (MenuLeftFragment) getFragmentManager().
                    findFragmentById(R.id.main_left_menu_fragment);
            fragment.setItemBackground(R.id.main_page_tag_layout);
            return;
        } else if (currentTime - touchTime > 2000) {
            GreenToast.makeText(MainActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            touchTime = System.currentTimeMillis();
            return;
        } else {
            System.exit(0);
        }

    }

    /**
     * 登录环信，因为改为使用用户id来登录，为了保证在登录本服务器后，获取到用户信息再登录环信服务器
     * @param id
     * @param password
     */
    private void loginEMChat(String id,String password){
        //登录环信SDK
        EMChatManager.getInstance().login(id, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                        EMChatUtil.isConnectedEMChatServer = true;
                        LogUtil.d("SplashActivity", "登陆聊天服务器成功！");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                EMChatUtil.isConnectedEMChatServer = false;
                LogUtil.d("SplashActivity", "登陆聊天服务器失败！"+" code "+code+" message : "+message);
            }
        });
    }

    @Override
    public void onEvent(EMNotifierEvent emNotifierEvent) {
        switch (emNotifierEvent.getEvent()){
            case EventNewMessage:
                EMMessage message = (EMMessage) emNotifierEvent.getData();

                // 提示新消息
                HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
                break;
        }
    }
}