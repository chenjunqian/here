package com.eason.marker.login_register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.LogUtil;

/**
 * Created by Eason on 9/14/15.
 */
public class RegisterUserInfoFragment extends BaseFragment {

    private RadioGroup radioGroup;
    private EditText nicknameEditText;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_user_info, container, false);
        nicknameEditText = (EditText) rootView.findViewById(R.id.register_user_info_edit_text);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.register_user_info_gender_radio_group);
        nextButton = (Button) rootView.findViewById(R.id.register_user_info_next_button);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //根据checkId来判断用户选择的性别
                switch (checkedId) {
                    case R.id.register_user_info_gender_button_male:
                        RegisterActivity.gender = "male";
                        break;
                    case R.id.register_user_info_gender_button_female:
                        RegisterActivity.gender = "female";
                        break;
                }

                LogUtil.d("RegisterUserInfoFragment","checkedId : "+checkedId+" gender "+RegisterActivity.gender);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500))return;

                RegisterActivity.nickname = nicknameEditText.getText().toString();

                if (CommonUtil.isEmptyString(RegisterActivity.gender)){
                    Toast.makeText(getActivity(),getResources().getString(R.string.please_input_gender),Toast.LENGTH_SHORT).show();
                }else if (CommonUtil.isEmptyString(RegisterActivity.nickname)){
                    Toast.makeText(getActivity(),getResources().getString(R.string.please_input_gnickname),Toast.LENGTH_SHORT).show();
                }else{
                    RegisterActivity registerActivity = (RegisterActivity)getActivity();
                    registerActivity.setFragmentTransaction(IntentUtil.REGISTER_USER_BIRTHDAY_PAGE);
                }

            }
        });
    }
}
