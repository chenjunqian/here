package com.eason.marker.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eason.marker.BaseActivity;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.LoginHandler;
import com.eason.marker.R;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.SharePreferencesUtil;
import com.eason.marker.util.WidgetUtil.GreenToast;
import com.eason.marker.util.WidgetUtil.ProgressDialog;

/**
 * Created by Eason on 8/21/15.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button loginBtn;
    private Button registeBtn;
    private Button forgotPasswordBtn;
    private EditText userAccountEditText;
    private EditText passwordEditText;
    private ProgressDialog progressDialog;

    private String userAccount;//用户登录账号
    private String userPassword;//用户登录密码

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ErroCode.ERROR_CODE_CLIENT_DATA_ERROR:
                    Toast.makeText(LoginActivity.this, "网络状态出了点问题", Toast.LENGTH_SHORT).show();
                    break;
                case ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID:
                    Toast.makeText(LoginActivity.this, "账户名或者密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        userAccountEditText = (EditText) findViewById(R.id.login_username_input_edit_text);
        passwordEditText = (EditText) findViewById(R.id.login_password_input_edit_text);
        loginBtn = (Button) findViewById(R.id.login_page_login_btn);
        loginBtn.setOnClickListener(this);
        registeBtn = (Button) findViewById(R.id.login_page_register_btn);
        registeBtn.setOnClickListener(this);
        forgotPasswordBtn = (Button) findViewById(R.id.forgot_password_btn);
        forgotPasswordBtn.setOnClickListener(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_page_login_btn:

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500))return;

                userAccount = userAccountEditText.getText().toString();
                userPassword = passwordEditText.getText().toString();

                if (CommonUtil.isEmptyString(userAccount)) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (CommonUtil.isEmptyString(userPassword)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();
                //登录逻辑
                login(userAccount, userPassword, "");

                break;
            case R.id.login_page_register_btn:

                //跳转至注册页面，注册逻辑结束后在 onActivityResult 方法中处理相关逻辑
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, IntentUtil.LOGIN_TO_REGISTER_REQUEST_CODE);
                break;

            case R.id.forgot_password_btn:
                GreenToast.makeText(LoginActivity.this,getResources().getString(R.string.forgot_password_text),Toast.LENGTH_LONG).show();

                break;
        }
    }

    /**
     * 登录请求
     *
     * @param userAccount
     * @param userPassword
     * @param pushKey
     */
    private void login(final String userAccount, final String userPassword, String pushKey) {

        LoginHandler loginHandler = new LoginHandler() {
            @Override
            public void getResult() {
                if (this.resultVO == null) {
                    handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID) {
                    handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    SharePreferencesUtil.saveUserLoginInfo(userAccount,userPassword);
                    setResult(RESULT_OK);
                    finish();
                }

                progressDialog.dismiss();
            }
        };

        HttpRequest.login(userAccount, userPassword, pushKey, loginHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IntentUtil.LOGIN_TO_REGISTER_REQUEST_CODE://如果是注册逻辑结束的requestCode则在这里处理
                    userAccount = data.getStringExtra("username");
                    userPassword = data.getStringExtra("password");
                    login(userAccount, userPassword, "");
                    break;
            }
        }
    }
}
