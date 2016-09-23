package com.eason.marker.controller.main_activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.eason.marker.R;
import com.eason.marker.controller.about_us_activity.AboutUsActivity;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.emchat.chatuidemo.activity.EMChatMainActivity;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.controller.login_register.LoginActivity;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.util.LogUtil;
import com.eason.marker.util.SharePreferencesUtil;
import com.eason.marker.view.CircleImageView;
import com.eason.marker.view.GreenToast;
import com.eason.marker.view.ModelDialog;
import com.igexin.sdk.PushManager;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EMEventListener {

    private static Context instance;

    private Toolbar toolbar;
    private Menu toolBarMenu;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private MainMapFragment mainMapFragment;
    private MainProfileFragment settingFragment;
    private NearUserListFragment nearUserListFragment;
    private CurrentMarkerListFragment currentPostFragment;
    private NotificationFragment notificationFragment;

    private static final int CHANGE_TOOL_BAR_TITLE_MAIN = 0x1;
    private static final int CHANGE_TOOL_BAR_TITLE_SETTING = 0X2;
    /**
     * MainMapFragment 获取帖子为空
     */
    public static final int NONE_VALID_POST = 0x3;
    private static final int CHANGE_TOOL_BAR_TITLE_NEAR = 0X4;
    private static final int CHANGE_TOOL_BAR_TITLE_MINE = 0X5;
    public static final int NONE_VALID_MORE_POST = 0x6;
    private static final int CHANGE_TOOL_BAR_TITLE_NOTIFICATION = 0x7;
    private static final int INIT_DRAWER_LOGIN_STATUS = 0x8;
    private static final int HAS_CHAT_MESSAGE = 0x9;

    /**
     * 判断toolbar是否要显示刷新按键
     */
    private boolean isShowRefreshView = true;

    /**
     * 标记当前显示的是哪个fragment
     */
    public static int FRAGMENT_TAG = 0;

    public static List<Post> postListItem;

    private Handler handler = new Handler() {
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
                case CHANGE_TOOL_BAR_TITLE_NOTIFICATION:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.notification_page_title);

                    break;

                case CHANGE_TOOL_BAR_TITLE_MINE:
                    //跳转时改变Toobar相应的标题
                    toolbar.setTitle(R.string.current_list_page_title);
                    break;


                case NONE_VALID_POST:
                    GreenToast.makeText(MainActivity.this, getResources().getString(R.string.main_map_no_post_toast), Toast.LENGTH_LONG).show();
                    break;
                case NONE_VALID_MORE_POST:
                    GreenToast.makeText(MainActivity.this, getResources().getString(R.string.current_list_page_no_more_post), Toast.LENGTH_LONG).show();
                    break;
                case ErroCode.ERROR_CODE_REQUEST_FORM_INVALID:
                    GreenToast.makeText(MainActivity.this, getResources().getString(R.string.main_map_some_problem_happen), Toast.LENGTH_LONG).show();
                    break;

                case INIT_DRAWER_LOGIN_STATUS:
                    CircleImageView circleImageView = (CircleImageView) findViewById(R.id.left_menu_avatar_image_view);
                    circleImageView.setImageResource(R.drawable.default_avatar_ori);
                    MenuItem loginItem = navigationView.getMenu().findItem(R.id.login_item_layout);
                    if (LoginStatus.getIsUserMode()) {
                        loginItem.setTitle(R.string.logout);
                        HttpRequest.loadImage(circleImageView, HttpConfig.String_Url_Media + LoginStatus.getUser().getAvatar(), 150, 150);
                    } else {
                        loginItem.setTitle(R.string.login);
                    }
                    break;

                case HAS_CHAT_MESSAGE:
                    MenuItem enterChatItem = navigationView.getMenu().findItem(R.id.enter_chat_main_page_item_layout);
                    enterChatItem.getIcon().setColorFilter(getResources().
                            getColor(R.color.universal_title_background_red), PorterDuff.Mode.MULTIPLY);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initParam();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.main_map_page, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        handler.sendEmptyMessage(new Message().what = INIT_DRAWER_LOGIN_STATUS);
        RelativeLayout navHeaderLayout = (RelativeLayout) findViewById(R.id.nav_header_view_layout);
        navHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentTransaction(IntentUtil.PROFLIE_FRAGMENT);
                revertMenuIconColor();
            }
        });
    }

    /**
     * 初始化参数，及相应的布局设置
     */
    private void initParam() {
        instance = this;
        mainMapFragment = new MainMapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content_main_fragment_layout, mainMapFragment).commit();
        EMChatUtil.autoReConnectEMChat();
        if (LoginStatus.getIsUserMode()) {
            loginEMChat(LoginStatus.getUser().getUserid(), LoginStatus.getUser().getPassword());
        }

        /**
         * 初始化个推SDK
         */
        PushManager.getInstance().initialize(this.getApplication());
    }

    public static Context getInstance() {
        return instance;
    }

    public Handler getHandler() {
        return handler;
    }

    public Fragment getFragment(int type) {
        switch (type) {
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

    /**
     * 登录环信，因为改为使用用户id来登录，为了保证在登录本服务器后，获取到用户信息再登录环信服务器
     *
     * @param id
     * @param password
     */
    private void loginEMChat(String id, String password) {
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
                LogUtil.d("SplashActivity", "登陆聊天服务器失败！" + " code " + code + " message : " + message);
            }
        });
    }

    @Override
    public void onEvent(EMNotifierEvent emNotifierEvent) {
        switch (emNotifierEvent.getEvent()) {
            case EventNewMessage: // 普通消息
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(new Message().what = HAS_CHAT_MESSAGE);
                        SharePreferencesUtil.saveEMChatNewMessegeStatus(true);
                    }
                });

                LogUtil.e("MainActivity", "EventNewMessage !!!!!!!!!!!!!!!!!");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(new Message().what = INIT_DRAWER_LOGIN_STATUS);
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
                if (LoginStatus.getIsUserMode()) {
                    loginEMChat(LoginStatus.getUser().getUserid(), LoginStatus.getUser().getPassword());
                }
                break;
            case IntentUtil.CHAT_MAIN_PAGE:
                /**
                 * 这里是为了从聊天页面回来时显示主页
                 */
//                setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
//                menuLeftFragment.setItemBackground(IntentUtil.MAIN_MAP_FRAGMENT);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        toolBarMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void revertMenuIconColor() {
        MenuItem mapItem = navigationView.getMenu().findItem(R.id.main_page_tag_layout);
        mapItem.getIcon().setColorFilter(getResources().
                getColor(android.support.design.R.color.material_blue_grey_800), PorterDuff.Mode.MULTIPLY);

        MenuItem nearItem = navigationView.getMenu().findItem(R.id.user_list_item_layout);
        nearItem.getIcon().setColorFilter(getResources().
                getColor(android.support.design.R.color.material_blue_grey_800), PorterDuff.Mode.MULTIPLY);

        MenuItem currentPostItem = navigationView.getMenu().findItem(R.id.current_post_list_item_layout);
        currentPostItem.getIcon().setColorFilter(getResources().
                getColor(android.support.design.R.color.material_blue_grey_800), PorterDuff.Mode.MULTIPLY);

        if (!SharePreferencesUtil.getEMChatNewMessegeStatus()){
            MenuItem enterChatItem = navigationView.getMenu().findItem(R.id.enter_chat_main_page_item_layout);
            enterChatItem.getIcon().setColorFilter(getResources().
                    getColor(android.support.design.R.color.material_blue_grey_800), PorterDuff.Mode.MULTIPLY);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        revertMenuIconColor();
        if (id != R.id.login_item_layout) {
            menuItem.getIcon().setColorFilter(getResources().getColor(R.color.universal_title_background_red), PorterDuff.Mode.MULTIPLY);
        }
        switch (id) {

            case R.id.main_page_tag_layout:
                setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
                break;
            case R.id.user_list_item_layout:
                setFragmentTransaction(IntentUtil.NEAR_USER_FRAGMENT);
                break;
            case R.id.current_post_list_item_layout:
                setFragmentTransaction(IntentUtil.CURRENT_POST_FRAGMENT);
                break;
            case R.id.enter_chat_main_page_item_layout:
                setFragmentTransaction(IntentUtil.CHAT_MAIN_PAGE);
                break;

            case R.id.login_item_layout:

                setFragmentTransaction(IntentUtil.MAIN_TO_LOGIN_PAGE);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换fragment,切换到fragmentIndex相应的fragment
     *
     * @param fragmentIndex
     */
    public void setFragmentTransaction(int fragmentIndex) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Message msg = new Message();
        if (fragmentIndex != IntentUtil.MAIN_TO_LOGIN_PAGE) {//跳转到注册登录页面不需要切换fragment
            hideFragment(transaction);
        }

        switch (fragmentIndex) {

            //切换到主页
            case IntentUtil.MAIN_MAP_FRAGMENT:
                if (mainMapFragment == null) {
                    mainMapFragment = new MainMapFragment();
                    transaction.add(R.id.content_main_fragment_layout, mainMapFragment);
                } else {
                    transaction.show(mainMapFragment);
                }

                msg.what = CHANGE_TOOL_BAR_TITLE_MAIN;
                isShowRefreshView = true;
                FRAGMENT_TAG = IntentUtil.MAIN_MAP_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                break;

            //切换到设置页面
            case IntentUtil.PROFLIE_FRAGMENT:
                if (!LoginStatus.getIsUserMode()) {
                    Toast.makeText(this, getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (settingFragment == null) {
                    settingFragment = new MainProfileFragment();
                    transaction.add(R.id.content_main_fragment_layout, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }

                msg.what = CHANGE_TOOL_BAR_TITLE_SETTING;
                isShowRefreshView = false;
                FRAGMENT_TAG = IntentUtil.PROFLIE_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;

            //切换到附近事件列表显示页面
            case IntentUtil.NEAR_USER_FRAGMENT:
                if (nearUserListFragment == null) {
                    nearUserListFragment = new NearUserListFragment();
                    transaction.add(R.id.content_main_fragment_layout, nearUserListFragment);
                } else {
                    transaction.show(nearUserListFragment);
                }

                FRAGMENT_TAG = IntentUtil.NEAR_USER_FRAGMENT;
                msg.what = CHANGE_TOOL_BAR_TITLE_NEAR;
                handler.sendEmptyMessage(msg.what);
                isShowRefreshView = false;
                break;

            case IntentUtil.MAIN_TO_LOGIN_PAGE:
                if (LoginStatus.getIsUserMode()) {

                    final ModelDialog mDialog = new ModelDialog(this, R.layout.dialog_back, R.style.Theme_dialog);
                    final Button btnOK, btnCancel;
                    final TextView title;
                    btnOK = (Button) mDialog.findViewById(R.id.ok_button);
                    btnCancel = (Button) mDialog.findViewById(R.id.cancel_button);
                    title = (TextView) mDialog.findViewById(R.id.alert_dialog_note_text);
                    title.setText(getResources().getString(R.string.login_register_is_sure_logout));
                    btnOK.setText(getResources().getString(R.string.my_profile_sure_to_delete_my_post));
                    btnCancel.setText(getResources().getString(R.string.my_profile_cancel_delete_my_post));

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            handler.sendEmptyMessage(new Message().what = INIT_DRAWER_LOGIN_STATUS);
                            LoginStatus.logout();
                            EMChatUtil.logoutEMChat();
                            mDialog.dismiss();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();

                    return;
                }
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, IntentUtil.MAIN_TO_LOGIN_PAGE);
                break;

            case IntentUtil.CHAT_MAIN_PAGE:
                if (!LoginStatus.getIsUserMode()) {
                    Toast.makeText(this, getResources().getString(R.string.please_login_first), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent toChatPageIntent = new Intent(this, EMChatMainActivity.class);
                startActivityForResult(toChatPageIntent, IntentUtil.CHAT_MAIN_PAGE);
                break;

            case IntentUtil.CURRENT_POST_FRAGMENT:
                if (currentPostFragment == null) {
                    currentPostFragment = new CurrentMarkerListFragment();
                    transaction.add(R.id.content_main_fragment_layout, currentPostFragment);
                } else {
                    transaction.show(currentPostFragment);
                }

                FRAGMENT_TAG = IntentUtil.CURRENT_POST_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                isShowRefreshView = false;
                msg.what = CHANGE_TOOL_BAR_TITLE_MINE;
                handler.sendEmptyMessage(msg.what);
                break;

            case IntentUtil.NOTIFICATION_FRAGMENT:
                if (notificationFragment == null) {
                    notificationFragment = new NotificationFragment();
                    transaction.add(R.id.content_main_fragment_layout, notificationFragment);
                } else {
                    transaction.show(notificationFragment);
                }

                FRAGMENT_TAG = IntentUtil.NOTIFICATION_FRAGMENT;
                handler.sendEmptyMessage(msg.what);
                isShowRefreshView = false;
                msg.what = CHANGE_TOOL_BAR_TITLE_NOTIFICATION;
                handler.sendEmptyMessage(msg.what);
                onPrepareOptionsMenu(toolBarMenu);
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mainMapFragment != null) {
            transaction.hide(mainMapFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if (nearUserListFragment != null) {
            transaction.hide(nearUserListFragment);
        }
        if (currentPostFragment != null) {
            transaction.hide(currentPostFragment);
        }

        if (notificationFragment != null) {
            transaction.hide(notificationFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                mainMapFragment.getPost();
                break;
            case R.id.action_notification:
                setFragmentTransaction(IntentUtil.NOTIFICATION_FRAGMENT);
                break;
            case R.id.action_about_us:
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private static long touchTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long currentTime = System.currentTimeMillis();

            if (FRAGMENT_TAG != IntentUtil.MAIN_MAP_FRAGMENT) {//在不是地图fragment的时候按返回键回到地图fragment
                setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
                return;
            } else if (currentTime - touchTime > 2000) {
                GreenToast.makeText(MainActivity.this, getResources().getString(R.string.click_twice_finish), Toast.LENGTH_SHORT).show();
                touchTime = System.currentTimeMillis();
                return;
            } else {
                System.exit(0);
            }
        }

    }
}