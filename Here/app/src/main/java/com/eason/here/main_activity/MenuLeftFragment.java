package com.eason.here.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.here.BaseFragment;
import com.eason.here.R;
import com.eason.here.login_register.LoginActivity;
import com.eason.here.model.IntentUtil;
import com.eason.here.model.LoginStatus;

/**
 * Created by Eason on 8/22/15.
 */
public class MenuLeftFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout userProfileLayout;
    private RelativeLayout mainTagLayout;
    private RelativeLayout userListLayout;
    private RelativeLayout loginLayout;

    private TextView loginTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_left_menu_layout, container, false);

        mainTagLayout = (RelativeLayout) root.findViewById(R.id.main_page_tag_layout);
        userListLayout = (RelativeLayout) root.findViewById(R.id.user_list_item_layout);
        loginLayout = (RelativeLayout) root.findViewById(R.id.login_item_layout);
        userProfileLayout = (RelativeLayout)root.findViewById(R.id.left_menu_profile_layout);

        loginTextView = (TextView) root.findViewById(R.id.login_text_view);

        mainTagLayout.setOnClickListener(this);
        userListLayout.setOnClickListener(this);
        loginLayout.setOnClickListener(this);
        userProfileLayout.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (LoginStatus.getIsUserMode()){
            loginTextView.setText("注销");
        }else if (!LoginStatus.getIsUserMode()){
            loginTextView.setText("登录");
        }
    }

    @Override
    public void onClick(View v) {

        MainActivity mainActivity = (MainActivity)getActivity();

        switch (v.getId()) {
            case R.id.main_page_tag_layout:
                mainActivity.setFragmentTransaction(IntentUtil.MAIN_MAP_FRAGMENT);
                break;
            case R.id.user_list_item_layout:

                break;
            case R.id.login_item_layout:
                mainActivity.setFragmentTransaction(IntentUtil.MAIN_TO_LOGIN_PAGE);
                Intent intent=new Intent(this.getActivity(),LoginActivity.class);
                getActivity().startActivityForResult(intent, IntentUtil.MAIN_TO_LOGIN_PAGE);
                break;
            case R.id.left_menu_profile_layout:

                if (!LoginStatus.getIsUserMode()){
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                mainActivity.setFragmentTransaction(IntentUtil.PROFLIE_FRAGMENT);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (LoginStatus.getIsUserMode()){
            loginTextView.setText("注销");
        }else if (!LoginStatus.getIsUserMode()){
            loginTextView.setText("登录");
        }
    }
}