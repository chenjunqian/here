package com.eason.marker.controller.login_register;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.eason.marker.controller.BaseActivity;
import com.eason.marker.network.HttpRequest;
import com.eason.marker.network.HttpResponseHandler;
import com.eason.marker.R;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.util.LogUtil;

/**
 * Created by Eason on 8/28/15.
 */
public class RegisterActivity extends BaseActivity {

    private RegisterVerifyFragment registerVerifyFragment;
    private RegisterUserInfoFragment registerUserInfoFragment;
    private RegisterBirthdayFragment registerBirthdayFragment;

    /**
     * 这里的静态变量用于在子Fragment中注册时存储的参数，统一在父Activity中定义
     */
    public static String userAccount;
    public static String password;
    public static String nickname;
    public static String gender;
    public static String birthday;
    public static String userConsellation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFragmentParam();
    }

    private void initFragmentParam(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        registerVerifyFragment = new RegisterVerifyFragment();
        registerUserInfoFragment = new RegisterUserInfoFragment();
        registerBirthdayFragment = new RegisterBirthdayFragment();
        transaction.replace(R.id.register_page_activity_contain_layout, registerVerifyFragment);
        transaction.commit();
    }

    public void setFragmentTransaction(int fragmentType){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (fragmentType){
            case IntentUtil.REGISTER_USER_INFO_PAGE:
                if (registerUserInfoFragment==null){
                    registerUserInfoFragment = new RegisterUserInfoFragment();
                }

                fragmentTransaction.add(R.id.register_page_activity_contain_layout,registerUserInfoFragment);
                break;
            case IntentUtil.REGISTER_USER_BIRTHDAY_PAGE:
                if (registerBirthdayFragment==null){
                    registerBirthdayFragment = new RegisterBirthdayFragment();
                }

                fragmentTransaction.add(R.id.register_page_activity_contain_layout, registerBirthdayFragment);
                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 注册用户
     */
    public void register(){

        //注册网络回调函数
        HttpResponseHandler registerHandler = new HttpResponseHandler(){
            @Override
            public void getResult() {
                if (this.resultVO==null){
                    Toast.makeText(RegisterActivity.this, R.string.net_work_invalid, Toast.LENGTH_SHORT).show();
                    return;
                }else if (this.resultVO.getStatus()==0){
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.register_success),Toast.LENGTH_SHORT).show();
                    Intent registerIntentData = new Intent();
                    registerIntentData.putExtra("username",userAccount);
                    registerIntentData.putExtra("password",password);
                    registerIntentData.putExtra("pushKey", "");
                    setResult(RESULT_OK,registerIntentData);
                    finish();
                    return;
                }
            }
        };

        LogUtil.d("RegisterActivity","userAccount : "+userAccount+" password : "+password+" nickname : "+nickname+" birthday : "+birthday+" gender : "+gender);
        HttpRequest.register(userAccount, password, "empty", nickname,birthday,gender, registerHandler);
    }
}