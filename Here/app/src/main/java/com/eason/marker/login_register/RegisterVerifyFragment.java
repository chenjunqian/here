package com.eason.marker.login_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.R;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.util.CommonUtil;

/**
 * 验证用户注册账号是否正确
 * Created by Eason on 9/13/15.
 */
public class RegisterVerifyFragment extends BaseFragment {

    private EditText emailPhoneEditText;
    private EditText passwordEditText;
    private Button nextBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register_verify, container, false);
        initView(root);
        return root;
    }

    private void initView(View root){
        emailPhoneEditText = (EditText)root.findViewById(R.id.register_page_email_phone_edit_text);
        passwordEditText = (EditText)root.findViewById(R.id.register_page_password_edit_text);
        nextBtn = (Button)root.findViewById(R.id.register_page_email_phone_next_button);

        //检测用户名是否存在的回调函数
        final HttpResponseHandler checkUserIsExistHandler = new HttpResponseHandler(){
            @Override
            public void getResult() {
                if (this.resultVO==null){
                    Toast.makeText(getActivity(), R.string.net_work_invalid, Toast.LENGTH_SHORT).show();
                    return;
                }else if (this.resultVO.getStatus()==0){
                    Toast.makeText(getActivity(),R.string.login_register_username_is_exist,Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    if (RegisterActivity.password.length()<=6){//密码不得少于六位数
                        Toast.makeText(getActivity(),R.string.login_register_input_valid_password,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RegisterActivity registerActivity = (RegisterActivity)getActivity();
                    registerActivity.setFragmentTransaction(IntentUtil.REGISTER_USER_INFO_PAGE);
                }

            }
        };

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick())return;

                RegisterActivity.userAccount = emailPhoneEditText.getText().toString();
                RegisterActivity.password = passwordEditText.getText().toString();

                //检测用户输入格式是否正确
                if (!CommonUtil.isEmail(RegisterActivity.userAccount)&&!CommonUtil.isMobileNO(RegisterActivity.userAccount)){
                    Toast.makeText(getActivity(),
                            R.string.login_register_input_valid_username,Toast.LENGTH_SHORT).show();
                    return;
                }else if (CommonUtil.isEmptyString(RegisterActivity.password)){
                    Toast.makeText(getActivity(),
                            R.string.login_register_input_password,Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpRequest.checkUserIsExist(RegisterActivity.userAccount, Object.class, checkUserIsExistHandler);
            }
        });
    }
}
