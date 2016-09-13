package com.eason.marker.main_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.R;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.User;
import com.eason.marker.profile_activity.ProfileActivity;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.ImageProcessParams;
import com.eason.marker.util.ImageScan.ImageScanMainActivity;
import com.eason.marker.util.LogUtil;
import com.eason.marker.view.CircleImageView;
import com.eason.marker.view.GreenToast;
import com.eason.marker.view.ModelDialog;
import com.eason.marker.view.ProgressDialog;

/**
 * Created by Eason on 9/6/15.
 */
public class MainProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MainProfileFragment";

    private RelativeLayout avatarLayout;
    private RelativeLayout nicknameLayout;
    private RelativeLayout genderLayout;
    private RelativeLayout birthdayLayout;
    private RelativeLayout passwordLayout;
    private RelativeLayout myPostLayout;
    private LinearLayout simpleProfileLayout;
    private LinearLayout longProfileLayout;
    private TextView nicknameTextView;
    private TextView genderTextView;
    private TextView birthdayTextView;
    private TextView accountTextView;
    private TextView simpleProfileTextView;
    private TextView longProfileTextView;
    private CircleImageView avatarImageView;

    private User user;

    private Bitmap avatarBitmap;

    private int resultYear = 0;
    private int validMonth = 0;
    private int resultDayOfMonth = 0;

    private boolean isPasswordCheck = false;

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

                    GreenToast.makeText(getActivity(), getResources().getString(R.string.my_profile_failed_to_update_avatar), Toast.LENGTH_SHORT).show();

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
        passwordLayout = (RelativeLayout) rootView.findViewById(R.id.profile_password_layout);
        passwordLayout.setOnClickListener(this);
        simpleProfileLayout = (LinearLayout) rootView.findViewById(R.id.profile_simple_profile_layout);
        simpleProfileLayout.setOnClickListener(this);
        longProfileLayout = (LinearLayout) rootView.findViewById(R.id.profile_long_profile_layout);
        longProfileLayout.setOnClickListener(this);
        myPostLayout = (RelativeLayout) rootView.findViewById(R.id.profile_my_post_layout);
        myPostLayout.setOnClickListener(this);

        nicknameTextView = (TextView) rootView.findViewById(R.id.profile_nickname_text_view);
        genderTextView = (TextView) rootView.findViewById(R.id.profile_gender_text_view);
        birthdayTextView = (TextView) rootView.findViewById(R.id.profile_birthday_text_view);
        accountTextView = (TextView) rootView.findViewById(R.id.profile_username_text_view);
        avatarImageView = (CircleImageView) rootView.findViewById(R.id.profile_avatar_image_view);
        simpleProfileTextView = (TextView) rootView.findViewById(R.id.profile_simple_profile_text_view);
        longProfileTextView = (TextView) rootView.findViewById(R.id.profile_long_profile_text_view);
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

        user = LoginStatus.getUser();
        nicknameTextView.setText(user.getNickname());
        if (user.getGender().equals("male")) {
            genderTextView.setText(getResources().getString(R.string.profile_activity_gender_male));
        } else {
            genderTextView.setText(getResources().getString(R.string.profile_activity_gender_female));
        }

        birthdayTextView.setText(user.getBirthday());
        accountTextView.setText(user.getUsername());
        simpleProfileTextView.setText(user.getSimpleProfile());
        longProfileTextView.setText(user.getLongProfile());
        HttpRequest.loadImage(avatarImageView, HttpConfig.String_Url_Media + user.getAvatar(),150,150);
    }

    /**
     * @param password
     * @param gender
     * @param birthday
     * @param nickname
     * @param userId
     */
    private void modifyProfile(String password, String gender, String birthday, String nickname, String userId
                                ,String simpleProfile,String longProfile) {
        final HttpResponseHandler modifyUserInfoHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    if (this.result == null) return;
                    User user = (User) this.result;
                    LoginStatus.setUser(user);
                    initData();
                    GreenToast.makeText(getActivity(), getResources().getString(R.string.my_profile_edit_success), Toast.LENGTH_SHORT).show();
                } else {
                    GreenToast.makeText(getActivity(),getResources().getString(R.string.my_profile_edit_failed), Toast.LENGTH_SHORT).show();
                }
            }
        };

        //提交给服务器
        HttpRequest.modifyUserInfo(LoginStatus.getUser().getUsername(), password,
                gender, birthday, nickname, userId,simpleProfile,longProfile, modifyUserInfoHandler);
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
                /**
                 * 修改昵称
                 */

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500)) return;
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
                        if (CommonUtil.isFastDoubleClick(500)) return;
                        String nickname = nicknameEditTextView.getText().toString();
                        if (!CommonUtil.isEmptyString(nickname) || !nickname.equals(LoginStatus.getUser().getNickname())) {
                            modifyProfile(LoginStatus.getUser().getPassword(), LoginStatus.getUser().getGender(),
                                    LoginStatus.getUser().getBirthday(), nickname, LoginStatus.getUser().getUserid(),
                                        LoginStatus.getUser().getSimpleProfile(),LoginStatus.getUser().getLongProfile());
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
                if (CommonUtil.isFastDoubleClick(500)) return;

                final ModelDialog genderDialog = new ModelDialog(getActivity(), R.layout.fragment_profile_gender_dialog_layout, R.style.Theme_dialog);
                RadioButton maleBtn, femaleBtn;
                maleBtn = (RadioButton) genderDialog.findViewById(R.id.profile_dialog_gender_male_button);
                femaleBtn = (RadioButton) genderDialog.findViewById(R.id.profile_dialog_gender_female_button);
                RelativeLayout maleLayout, femalLayout;
                maleLayout = (RelativeLayout) genderDialog.findViewById(R.id.profile_dialog_gender_male_layout);
                femalLayout = (RelativeLayout) genderDialog.findViewById(R.id.profile_dialog_gender_female_layout);

                if (LoginStatus.getUser().getGender().equals("male")) {
                    maleBtn.setChecked(true);
                } else {
                    femaleBtn.setChecked(true);
                }

                maleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //提交给服务器
                        modifyProfile(LoginStatus.getUser().getPassword(), "male",
                                LoginStatus.getUser().getBirthday(), LoginStatus.getUser().getNickname(), LoginStatus.getUser().getUserid(),
                                LoginStatus.getUser().getSimpleProfile(),LoginStatus.getUser().getLongProfile());
                        genderDialog.dismiss();
                    }
                });

                femalLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //提交给服务器
                        modifyProfile(LoginStatus.getUser().getPassword(), "female",
                                LoginStatus.getUser().getBirthday(), LoginStatus.getUser().getNickname(), LoginStatus.getUser().getUserid(),
                                LoginStatus.getUser().getSimpleProfile(),LoginStatus.getUser().getLongProfile());
                        genderDialog.dismiss();

                    }
                });

                genderDialog.show();

                break;
            case R.id.profile_birthday_layout:
                /**
                 * 修改生日
                 */
                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500)) return;

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
                        if (CommonUtil.isFastDoubleClick(500)) return;
                        String birthday = resultYear + "-" + validMonth + "-" + resultDayOfMonth;
                        if (!birthday.equals("0-0-0") || !birthday.equals(LoginStatus.getUser().getBirthday())) {
                            //提交给服务器
                            modifyProfile(LoginStatus.getUser().getPassword(), LoginStatus.getUser().getGender(),
                                    birthday, LoginStatus.getUser().getNickname(), LoginStatus.getUser().getUserid(),
                                    LoginStatus.getUser().getSimpleProfile(),LoginStatus.getUser().getLongProfile());
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
            case R.id.profile_password_layout:
                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500)) return;
                final ModelDialog changePasswordDialog = new ModelDialog(getActivity(), R.layout.modify_username_dialog_layout, R.style.Theme_dialog);
                LinearLayout passwordParentLayout = (LinearLayout) changePasswordDialog.findViewById(R.id.modify_username_dialog_parent_layout);
                Button passwordOkBtn, passwordCancelBtn;
                EditText nicknameEdittextview = (EditText) changePasswordDialog.findViewById(R.id.modify_nickname_dialog_edit_text);
                nicknameEdittextview.setVisibility(View.GONE);
                final EditText passwordEditTextView = (EditText) changePasswordDialog.findViewById(R.id.modify_password_dialog_edit_text);
                final EditText passwordConfirmTextView = (EditText) changePasswordDialog.findViewById(R.id.modify_password_confirm_dialog_edit_text);
                passwordEditTextView.setHint(getResources().getString(R.string.my_profile_input_old_password));
                passwordEditTextView.setVisibility(View.VISIBLE);
                passwordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                passwordOkBtn = (Button) changePasswordDialog.findViewById(R.id.modify_username_ok_button);
                passwordCancelBtn = (Button) changePasswordDialog.findViewById(R.id.modify_username_cancel_button);

                isPasswordCheck = false;

                passwordOkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.isFastDoubleClick(500)) return;
                        String enterPassword = passwordEditTextView.getText().toString();
                        if (!CommonUtil.isEmptyString(enterPassword) && enterPassword.equals(LoginStatus.getUser().getPassword()) && !isPasswordCheck) {
                            passwordEditTextView.setText("");
                            passwordEditTextView.setHint(getActivity().getResources().getString(R.string.my_profile_input_old_password));
                            passwordConfirmTextView.setVisibility(View.VISIBLE);
                            isPasswordCheck = true;
                            return;
                        } else if (isPasswordCheck) {
                            if (!CommonUtil.isEmptyString(passwordEditTextView.getText().toString())
                                    && !CommonUtil.isEmptyString(passwordConfirmTextView.getText().toString())
                                    && passwordEditTextView.getText().toString().equals(passwordConfirmTextView.getText().toString())
                                    &&passwordConfirmTextView.getText().toString().length()>=6) {
                                modifyProfile(passwordConfirmTextView.getText().toString(),LoginStatus.getUser().getGender(),
                                        LoginStatus.getUser().getBirthday(),LoginStatus.getUser().getNickname(),LoginStatus.getUser().getUserid(),
                                        LoginStatus.getUser().getSimpleProfile(),LoginStatus.getUser().getLongProfile());
                            }
                        } else {
                            GreenToast.makeText(getActivity(), getActivity().getResources().getString(R.string.my_profile_input_valid_password), Toast.LENGTH_LONG).show();
                            return;
                        }

                        changePasswordDialog.dismiss();
                    }
                });

                passwordCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePasswordDialog.dismiss();
                    }
                });

                passwordParentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePasswordDialog.dismiss();
                    }
                });

                changePasswordDialog.show();
                break;

            case R.id.profile_simple_profile_layout:

                if (CommonUtil.isFastDoubleClick(500)) return;
                final ModelDialog simpleProfileDialog = new ModelDialog(getActivity(), R.layout.modify_username_dialog_layout, R.style.Theme_dialog);
                LinearLayout simpleProfileParentLayout = (LinearLayout) simpleProfileDialog.findViewById(R.id.modify_username_dialog_parent_layout);
                Button simpleProfileOkBtn, simpleProfileCancelBtn;
                final EditText simpleProfileEditTextView = (EditText) simpleProfileDialog.findViewById(R.id.modify_nickname_dialog_edit_text);
                simpleProfileEditTextView.setHint(getResources().getString(R.string.my_profile_edit_profile));
                simpleProfileEditTextView.setText(LoginStatus.getUser().getSimpleProfile());
                simpleProfileEditTextView.setSelection(LoginStatus.getUser().getSimpleProfile().length());//将光标设置在最后
                simpleProfileOkBtn = (Button) simpleProfileDialog.findViewById(R.id.modify_username_ok_button);
                simpleProfileCancelBtn = (Button) simpleProfileDialog.findViewById(R.id.modify_username_cancel_button);

                simpleProfileOkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.isFastDoubleClick(500)) return;
                        String simpleProfile = simpleProfileEditTextView.getText().toString();
                        if (!CommonUtil.isEmptyString(simpleProfile) || !simpleProfile.equals(LoginStatus.getUser().getSimpleProfile())) {
                            modifyProfile(LoginStatus.getUser().getPassword(), LoginStatus.getUser().getGender(),
                                    LoginStatus.getUser().getBirthday(), LoginStatus.getUser().getNickname(), LoginStatus.getUser().getUserid(),
                                    simpleProfile,LoginStatus.getUser().getLongProfile());
                        }
                        simpleProfileDialog.dismiss();
                    }
                });

                simpleProfileCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        simpleProfileDialog.dismiss();
                    }
                });

                simpleProfileParentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        simpleProfileDialog.dismiss();
                    }
                });

                simpleProfileDialog.show();

                break;

            case R.id.profile_long_profile_layout:

                if (CommonUtil.isFastDoubleClick(500)) return;
                final ModelDialog longProfileDialog = new ModelDialog(getActivity(), R.layout.modify_username_dialog_layout, R.style.Theme_dialog);
                LinearLayout longProfileParentLayout = (LinearLayout) longProfileDialog.findViewById(R.id.modify_username_dialog_parent_layout);
                Button longProfileOkBtn, longProfileCancelBtn;
                final EditText longProfileEditTextView = (EditText) longProfileDialog.findViewById(R.id.modify_nickname_dialog_edit_text);
                longProfileEditTextView.setHint(getResources().getString(R.string.my_profile_edit_profile));
                longProfileEditTextView.setText(LoginStatus.getUser().getLongProfile());
                longProfileEditTextView.setSelection(LoginStatus.getUser().getLongProfile().length());//将光标设置在最后
                longProfileOkBtn = (Button) longProfileDialog.findViewById(R.id.modify_username_ok_button);
                longProfileCancelBtn = (Button) longProfileDialog.findViewById(R.id.modify_username_cancel_button);

                longProfileOkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.isFastDoubleClick(500)) return;
                        String longProfile = longProfileEditTextView.getText().toString();
                        if (!CommonUtil.isEmptyString(longProfile) || !longProfile.equals(LoginStatus.getUser().getLongProfile())) {
                            modifyProfile(LoginStatus.getUser().getPassword(), LoginStatus.getUser().getGender(),
                                    LoginStatus.getUser().getBirthday(), LoginStatus.getUser().getNickname(), LoginStatus.getUser().getUserid(),
                                    LoginStatus.getUser().getSimpleProfile(), longProfile);
                        }
                        longProfileDialog.dismiss();
                    }
                });

                longProfileCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longProfileDialog.dismiss();
                    }
                });

                longProfileParentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longProfileDialog.dismiss();
                    }
                });

                longProfileDialog.show();
                break;

            case R.id.profile_my_post_layout:

                if (user==null)return;
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("username",user.getUsername());
                intent.putExtra(IntentUtil.IS_SHOW_MY_USER_INFO_LAYOUT_STRING,IntentUtil.IS_SHOW_USER_INFO_LAYOUT_INT);
                getActivity().startActivity(intent);

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

            avatarBitmap = CommonUtil.loadBitmapAndCompress(imagePath);

            if (avatarBitmap == null) return;

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.show();

            final HttpResponseHandler getUserInfoHandler = new HttpResponseHandler() {
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
