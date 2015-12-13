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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.here.BaseFragment;
import com.eason.here.HttpUtil.HttpConfig;
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
import com.eason.here.util.WidgetUtil.GreenToast;
import com.eason.here.util.WidgetUtil.ModelDialog;
import com.eason.here.util.WidgetUtil.ProgressDialog;

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
    private CircleImageView avatarImageView;

    private Bitmap avatarBitmap;

    private int resultYear = 0;
    private int validMonth = 0;
    private int resultDayOfMonth = 0;

    private static final int UPDATE_AVATAR_SUCCESS = 0x2;
    private static final int UPDATE_AVATAR_FAIL = 0x3;
    private static final int UPDATE_GENDER_SUCCESS = 0x4;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_AVATAR_SUCCESS:
                    avatarImageView.setImageBitmap(avatarBitmap);
                    break;

                case UPDATE_AVATAR_FAIL:

                    GreenToast.makeText(getActivity(), "上传头像失败", Toast.LENGTH_SHORT).show();

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
        HttpRequest.loadImage(avatarImageView, HttpConfig.String_Url_Media+user.getAvatar());
    }

    @Override
    public void onClick(View v) {

        final HttpResponseHandler modifyUserInfoHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    if (this.result == null) return;
                    User user = (User) this.result;
                    LoginStatus.setUser(user);
                    initData();
                    GreenToast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    GreenToast.makeText(getActivity(), "更改失败咯，重新试一下看看", Toast.LENGTH_SHORT).show();
                }
            }
        };

        switch (v.getId()) {
            case R.id.profile_avatar_layout:

                /**
                 * 打开手机系统相册
                 */
                Intent albumIntent = new Intent(getActivity(), ImageScanMainActivity.class);
                startActivityForResult(albumIntent, IntentUtil.SYSTEM_ABLUM_REQUEST_CODE);

                break;
            case R.id.profile_nickname_layout:
                /**
                 * 修改昵称
                 */

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick()) return;
                final ModelDialog nicknameDialog = new ModelDialog(getActivity(), R.layout.modify_username_dialog_layout, R.style.Theme_dialog);
                LinearLayout nicknameParentLayout = (LinearLayout) nicknameDialog.findViewById(R.id.modify_username_dialog_parent_layout);
                Button nicknameOkBtn, nicknameCancelBtn;
                final EditText nicknameEditTextView = (EditText) nicknameDialog.findViewById(R.id.modify_nickname_dialog_edit_text);
                nicknameEditTextView.setText(LoginStatus.getUser().getNickname());
                nicknameEditTextView.setSelection(LoginStatus.getUser().getNickname().length());//将光标设置在最后
                nicknameOkBtn = (Button) nicknameDialog.findViewById(R.id.modify_username_ok_button);
                nicknameCancelBtn = (Button) nicknameDialog.findViewById(R.id.modify_username_cancel_button);

                nicknameOkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.isFastDoubleClick()) return;
                        String nickname = nicknameEditTextView.getText().toString();
                        if (!CommonUtil.isEmptyString(nickname) || !nickname.equals(LoginStatus.getUser().getNickname())) {
                            //提交给服务器
                            HttpRequest.modifyUserInfo(LoginStatus.getUser().getUsername(), LoginStatus.getUser().getPassword(),
                                    LoginStatus.getUser().getGender(), LoginStatus.getUser().getBirthday(),nickname,
                                    LoginStatus.getUser().getUserid(), modifyUserInfoHandler);
                        }
                        nicknameDialog.dismiss();
                    }
                });

                nicknameCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nicknameDialog.dismiss();
                    }
                });

                nicknameParentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nicknameDialog.dismiss();
                    }
                });

                nicknameDialog.show();

                break;
            case R.id.profile_gender_layout:
                /**
                 * 修改性别
                 */
                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick()) return;

                final ModelDialog genderDialog = new ModelDialog(getActivity(), R.layout.date_picker_dialog_layout, R.style.Theme_dialog);
                LinearLayout genderParentLayout = (LinearLayout) genderDialog.findViewById(R.id.date_picker_modify_parent_layout);
                Button genderOkBtn, genderCancelBtn;
                genderOkBtn = (Button) genderDialog.findViewById(R.id.date_picker_modify_ok_button);
                genderCancelBtn = (Button) genderDialog.findViewById(R.id.date_picker_modify_cancel_button);
                break;
            case R.id.profile_birthday_layout:
                /**
                 * 修改生日
                 */
                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick()) return;

                final ModelDialog birthdayDialog = new ModelDialog(getActivity(), R.layout.date_picker_dialog_layout, R.style.Theme_dialog);
                DatePicker datePicker = (DatePicker) birthdayDialog.findViewById(R.id.date_picker_modify_username_date_picker);
                LinearLayout parentLayout = (LinearLayout) birthdayDialog.findViewById(R.id.date_picker_modify_parent_layout);
                Button okBtn, cancelBtn;
                okBtn = (Button) birthdayDialog.findViewById(R.id.date_picker_modify_ok_button);
                cancelBtn = (Button) birthdayDialog.findViewById(R.id.date_picker_modify_cancel_button);

                String[] temp = LoginStatus.getUser().getBirthday().split("-");//拆分生日
                datePicker.init(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]) - 1, Integer.valueOf(temp[2]), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        validMonth = monthOfYear + 1;
                        resultYear = year;
                        resultDayOfMonth = dayOfMonth;
                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.isFastDoubleClick()) return;
                        String birthday = resultYear + "-" + validMonth + "-" + resultDayOfMonth;
                        if (!birthday.equals("0-0-0") || !birthday.equals(LoginStatus.getUser().getBirthday())) {
                            //提交给服务器
                            HttpRequest.modifyUserInfo(LoginStatus.getUser().getUsername(), LoginStatus.getUser().getPassword(),
                                    LoginStatus.getUser().getGender(), birthday, LoginStatus.getUser().getNickname(),
                                    LoginStatus.getUser().getUserid(), modifyUserInfoHandler);
                        }
                        birthdayDialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        birthdayDialog.dismiss();
                    }
                });

                parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        birthdayDialog.dismiss();
                    }
                });

                birthdayDialog.show();
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

        if (resultCode != getActivity().RESULT_OK) {
            LogUtil.e(TAG, "resultCode : " + requestCode);
            return;
        }

        /**
         * 获取相册返回的相片信息
         */
        if (requestCode == IntentUtil.SYSTEM_ABLUM_REQUEST_CODE) {

            String imagePath = data
                    .getStringExtra(ImageProcessParams.IMAGE_PATH_EXTRA_NAME);
            LogUtil.d(TAG, "imagePath : " + imagePath);

            avatarBitmap = CommonUtil.loadBitmap(imagePath);

            if (avatarBitmap == null) return;

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.show();

            final HttpResponseHandler getUserInfoHandler = new HttpResponseHandler(){
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        User user = (User) this.result;
                        LoginStatus.setUser(user);
                        initData();
                    }
                }
            };

            HttpResponseHandler avatarHandler = new HttpResponseHandler() {
                @Override
                public void getResult() {
                    super.getResult();
                    progress.dismiss();
                    if (this.resultVO == null) {
                        handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                    } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
//                        handler.sendEmptyMessage(new Message().what = UPDATE_AVATAR_SUCCESS);
                        HttpRequest.getUserByUsername(LoginStatus.getUser().getUsername(), getUserInfoHandler);
                    } else {
                        handler.sendEmptyMessage(new Message().what = UPDATE_AVATAR_FAIL);
                    }

                    LogUtil.d(TAG, "resultVO.getStatus() : " + resultVO.getStatus() + " error message " + resultVO.getErrorMessage());
                }
            };

            HttpRequest.uploadAvatar(LoginStatus.getUser().getUsername(), CommonUtil.getFileNameFromFilePath(imagePath), imagePath, avatarHandler);

        }
    }
}
