package com.eason.here.login_register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eason.here.BaseActivity;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.util.CommonUtil;

/**
 * Created by Eason on 8/28/15.
 */
public class RegisterActivity extends BaseActivity {

    private EditText emailPhoneEditText;
    private Button nextBtn;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        emailPhoneEditText = (EditText)findViewById(R.id.register_page_email_phone_edit_text);
        nextBtn = (Button)findViewById(R.id.register_page_email_phone_next_button);

        //检测用户名是否存在的回调函数
        final HttpResponseHandler checkUserIsExistHandler = new HttpResponseHandler(){
            @Override
            public void getResult() {
                if (this.resultVO==null){
                    Toast.makeText(RegisterActivity.this,"网络发生了一些问题",Toast.LENGTH_SHORT).show();
                    return;
                }else if (this.resultVO.getStatus()==0){
                    Toast.makeText(RegisterActivity.this,"该账户已经注册",Toast.LENGTH_SHORT).show();
                    return;
                }else{

                }

            }
        };

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = emailPhoneEditText.getText().toString();

                //检测用户输入格式是否正确
                if (!CommonUtil.isEmail(username)&&!CommonUtil.isMobileNO(username)){
                    Toast.makeText(RegisterActivity.this,
                            "请输入正确格式的手机号或者邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpRequest.checkUserIsExist(username,Object.class,checkUserIsExistHandler);
            }
        });
    }
}
