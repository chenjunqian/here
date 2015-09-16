package com.eason.here.login_register;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.eason.here.BaseActivity;
import com.eason.here.R;
import com.eason.here.model.IntentUtil;

/**
 * Created by Eason on 8/28/15.
 */
public class RegisterActivity extends BaseActivity {

    private RegisterVerifyFragment registerVerifyFragment;

    public static String userAccount;
    public static String nickname;
    public static String gender;

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
        transaction.replace(R.id.register_page_activity_contain_layout, registerVerifyFragment);
        transaction.commit();
    }

    public void setFragmentTransaction(int fragmentType){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (fragmentType){
            case IntentUtil.REGISTER_USER_INFO_PAGE:

                fragmentTransaction.hide(registerVerifyFragment);
                fragmentTransaction.add(R.id.register_page_activity_contain_layout,new RegisterUserInfoFragment());
                break;
            case IntentUtil.REGISTER_USER_BIRTHDAY_PAGE:

                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}