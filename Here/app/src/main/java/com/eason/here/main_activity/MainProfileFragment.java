package com.eason.here.main_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.here.BaseFragment;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.model.ErroCode;
import com.eason.here.model.IntentUtil;
import com.eason.here.model.LoginStatus;
import com.eason.here.model.User;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.ImageProcessParams;
import com.eason.here.util.ImageScan.ImageScanMainActivity;
import com.eason.here.util.LogUtil;
import com.eason.here.util.WidgetUtil.CircleImageView;
import com.eason.here.util.WidgetUtil.EditTextDialog;
import com.eason.here.util.WidgetUtil.OnFinishInputListener;

/**
 * Created by Eason on 9/6/15.
 */
public class MainProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MainProfileFragment";

    private RelativeLayout avatarLayout;
    private RelativeLayout nicknameLayout;
    private RelativeLayout genderLayout;
    private RelativeLayout birthdayLayout;
    private TextView nicknameTextView;
    private TextView genderTextView;
    private TextView birthdayTextView;
    private TextView accountTextView;
    private EditTextDialog editTextDialog;
    private CircleImageView avatarImageView;

    private Bitmap avatarBitmap;


    private static final int UPDATE_AVATAR_SUCCESS = 0x2;
    private static final int UPDATE_AVATAR_FAIL = 0x3;
    private static final int UPDATE_GENDER_SUCCESS = 0x4;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case UPDATE_AVATAR_SUCCESS:
                    avatarImageView.setImageBitmap(avatarBitmap);
                    break;

                case UPDATE_AVATAR_FAIL:

                    Toast.makeText(getActivity(),"上传头像失败",Toast.LENGTH_SHORT).show();

                    break;
                case ErroCode.ERROR_CODE_CLIENT_DATA_ERROR:

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_profile__layout, container, false);
        avatarLayout = (RelativeLayout) rootView.findViewById(R.id.profile_avatar_layout);
        avatarLayout.setOnClickListener(this);
        nicknameLayout = (RelativeLayout) rootView.findViewById(R.id.profile_nickname_layout);
        nicknameLayout.setOnClickListener(this);
        genderLayout = (RelativeLayout) rootView.findViewById(R.id.profile_gender_layout);
        genderLayout.setOnClickListener(this);
        birthdayLayout = (RelativeLayout) rootView.findViewById(R.id.profile_birthday_layout);
        birthdayLayout.setOnClickListener(this);

        nicknameTextView = (TextView) rootView.findViewById(R.id.profile_nickname_text_view);
        genderTextView = (TextView) rootView.findViewById(R.id.profile_gender_text_view);
        birthdayTextView = (TextView) rootView.findViewById(R.id.profile_birthday_text_view);
        accountTextView = (TextView) rootView.findViewById(R.id.profile_username_text_view);
        avatarImageView = (CircleImageView) rootView.findViewById(R.id.profile_avatar_image_view);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化用户个人信息
     */
    private void initData() {
        if (!LoginStatus.getIsUserMode() || LoginStatus.getUser() == null) return;

        User user = LoginStatus.getUser();
        nicknameTextView.setText(user.getNickname());
        if (user.getGender().equals("male")) {
            genderTextView.setText("男");
        } else {
            genderTextView.setText("女");
        }

        birthdayTextView.setText(user.getBirthday());
        accountTextView.setText(user.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_avatar_layout:

                /**
                 * 打开手机系统相册
                 */
                Intent albumIntent = new Intent(getActivity(), ImageScanMainActivity.class);
                startActivityForResult(albumIntent, IntentUtil.SYSTEM_ABLUM_REQUEST_CODE);

                break;
            case R.id.profile_nickname_layout:

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick())return;

                editTextDialog = new EditTextDialog(getActivity(),"修改昵称", nicknameTextView.getText().toString(), new OnFinishInputListener() {
                    @Override
                    public void onFinish(String s) {
                        editTextDialog.dismiss();
                    }
                });

                editTextDialog.show();

                break;
            case R.id.profile_gender_layout:

                break;
            case R.id.profile_birthday_layout:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!=getActivity().RESULT_OK){
            LogUtil.e(TAG,"resultCode : "+requestCode);
            return;
        }

        /**
         * 获取相册返回的相片信息
         */
        if (requestCode==IntentUtil.SYSTEM_ABLUM_REQUEST_CODE){

            String imagePath = data
                    .getStringExtra(ImageProcessParams.IMAGE_PATH_EXTRA_NAME);
            LogUtil.d(TAG, "imagePath : " + imagePath);

            avatarBitmap = CommonUtil.loadBitmap(imagePath);

            if (avatarBitmap==null)return;

            HttpResponseHandler avatarHandler = new HttpResponseHandler(){
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO == null) {
                        handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                    } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        handler.sendEmptyMessage(new Message().what=UPDATE_AVATAR_SUCCESS);
                    }else{
                        handler.sendEmptyMessage(new Message().what=UPDATE_AVATAR_FAIL);
                    }

                    LogUtil.d(TAG,"resultVO.getStatus() : "+resultVO.getStatus());
                }
            };

            HttpRequest.uploadAvatar(LoginStatus.getUser().getUsername(),CommonUtil.getFileNameFromFilePath(imagePath),imagePath,avatarHandler);
        }
    }
}
