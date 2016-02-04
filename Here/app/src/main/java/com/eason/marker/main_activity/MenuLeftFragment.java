package com.eason.marker.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.login_register.LoginActivity;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.util.WidgetUtil.CircleImageView;
import com.eason.marker.util.WidgetUtil.ModelDialog;

/**
 * Created by Eason on 8/22/15.
 */
public class MenuLeftFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MenuLeftFragment";
    private RelativeLayout userProfileLayout;
    private RelativeLayout mainTagLayout;
    private RelativeLayout userListLayout;
    private RelativeLayout loginLayout;
    private RelativeLayout currentPostLayout;
    private CircleImageView circleImageView;

    private TextView loginTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_left_menu_layout, container, false);

        mainTagLayout = (RelativeLayout) root.findViewById(R.id.main_page_tag_layout);
        userListLayout = (RelativeLayout) root.findViewById(R.id.user_list_item_layout);
        loginLayout = (RelativeLayout) root.findViewById(R.id.login_item_layout);
        userProfileLayout = (RelativeLayout) root.findViewById(R.id.left_menu_profile_layout);
        currentPostLayout = (RelativeLayout) root.findViewById(R.id.current_post_list_item_layout);

        loginTextView = (TextView) root.findViewById(R.id.login_text_view);
        circleImageView = (CircleImageView) userProfileLayout.findViewById(R.id.left_menu_avatar_image_view);

        mainTagLayout.setOnClickListener(this);
        userListLayout.setOnClickListener(this);
        loginLayout.setOnClickListener(this);
        userProfileLayout.setOnClickListener(this);
        currentPostLayout.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        if (LoginStatus.getIsUserMode()) {
            loginTextView.setText("注销");
        } else if (!LoginStatus.getIsUserMode()) {
            loginTextView.setText("登录");
        }

        if (LoginStatus.getUser() == null) return;
        HttpRequest.loadImage(circleImageView, HttpConfig.String_Url_Media + LoginStatus.getUser().getAvatar(),150,150);
    }

    @Override
    public void onClick(View v) {

        MainActivity mainActivity = (MainActivity) getActivity();

        switch (v.getId()) {
            case R.id.main_page_tag_layout:
                mainActivity.setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
                break;
            case R.id.user_list_item_layout:
                mainActivity.setFragmentTransaction(IntentUtil.NEAR_USER_FRAGMENT);
                break;
            case R.id.login_item_layout:
                if (LoginStatus.getIsUserMode()) {

                    final ModelDialog mDialog = new ModelDialog(getActivity(), R.layout.dialog_back, R.style.Theme_dialog);
                    final Button btnOK, btnCancel;
                    final TextView title;
                    btnOK = (Button) mDialog.findViewById(R.id.ok_button);
                    btnCancel = (Button) mDialog.findViewById(R.id.cancel_button);
                    title = (TextView) mDialog.findViewById(R.id.alert_dialog_note_text);
                    title.setText("确定要注销嘛？");
                    btnOK.setText("是的");
                    btnCancel.setText("手滑了");

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            LoginStatus.setUser(null);
                            EMChatUtil.logoutEMChat();
                            initData();
                            mDialog.dismiss();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();

                    return;
                }
                mainActivity.setFragmentTransaction(IntentUtil.MAIN_TO_LOGIN_PAGE);
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(intent, IntentUtil.MAIN_TO_LOGIN_PAGE);
                break;
            case R.id.current_post_list_item_layout:
                mainActivity.setFragmentTransaction(IntentUtil.CURRENT_POST_FRAGMENT);
                break;

            case R.id.left_menu_profile_layout:

                if (!LoginStatus.getIsUserMode()) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }

                mainActivity.setFragmentTransaction(IntentUtil.PROFLIE_FRAGMENT);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}