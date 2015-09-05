package com.eason.here.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eason.here.BaseActivity;
import com.eason.here.R;
import com.eason.here.model.IntentUtil;
import com.eason.here.util.CommonUtil;

/**
 * Created by Eason on 8/21/15.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button loginBtn;
    private Button registeBtn;
    private EditText userAccountEditText;
    private EditText passwordEditText;

    private String userAccount;//用户登录账号
    private String userPassword;//用户登录密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        userAccountEditText = (EditText)findViewById(R.id.login_username_input_edit_text);
        passwordEditText = (EditText)findViewById(R.id.login_password_input_edit_text);
        loginBtn = (Button)findViewById(R.id.login_page_login_btn);
        loginBtn.setOnClickListener(this);
        registeBtn = (Button)findViewById(R.id.login_page_register_btn);
        registeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_page_login_btn:

                userAccount = userAccountEditText.getText().toString();
                userPassword = passwordEditText.getText().toString();

                if (CommonUtil.isEmptyString(userAccount)||CommonUtil.isEmptyString(userPassword)){
                    return;
                }

                //登录逻辑

                break;
            case R.id.login_page_register_btn:

                //跳转至注册页面，注册逻辑结束后在 onActivityResult 方法中处理相关逻辑
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, IntentUtil.LOGIN_TO_REGISTER_REQUEST_CODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            switch (requestCode){
                case IntentUtil.LOGIN_TO_REGISTER_REQUEST_CODE://如果是注册逻辑结束的requestCode则在这里处理

                    break;
            }
        }
    }
}
