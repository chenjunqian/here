package com.eason.here.main_activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eason.here.BaseFragment;
import com.eason.here.R;
import com.eason.here.model.IntentUtil;
import com.eason.here.model.LoginStatus;
import com.eason.here.model.User;
import com.eason.here.util.LogUtil;
import com.eason.here.util.WidgetUtil.EditTextDialog;
import com.eason.here.util.WidgetUtil.OnFinishInputListener;

/**
 * Created by Eason on 9/6/15.
 */
public class MainProfileFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout avatarLayout;
    private RelativeLayout nicknameLayout;
    private RelativeLayout genderLayout;
    private RelativeLayout birthdayLayout;

    private TextView nicknameTextView;
    private TextView genderTextView;
    private TextView birthdayTextView;
    private TextView accountTextView;

    private EditTextDialog editTextDialog;

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

                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.setType("image/*");
                getActivity().startActivityForResult(albumIntent, IntentUtil.SYSTEM_ABLUM_REQUEST_CODE);

                break;
            case R.id.profile_nickname_layout:

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

        if (resultCode!=getActivity().RESULT_OK)return;

        /**
         * 获取相册返回的相片信息
         */
        if (requestCode==IntentUtil.SYSTEM_ABLUM_REQUEST_CODE){

            try{
                ContentResolver resolver = getActivity().getContentResolver();
                Uri originalUri = data.getData();
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                String[] proj = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);

                LogUtil.d("MainProfileFragment", "path : " + path + " bm : " + bm);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
