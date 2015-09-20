package com.eason.here.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eason.here.BaseFragment;
import com.eason.here.R;
import com.eason.here.model.LoginStatus;
import com.eason.here.model.User;

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

                break;
            case R.id.profile_nickname_layout:

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
}
