package com.eason.here.main_activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eason.here.R;
import com.eason.here.login_register.LoginActivity;
import com.eason.here.model.IntentUtil;

/**
 * Created by Eason on 8/22/15.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mainTagLayout;
    private RelativeLayout userListLayout;
    private RelativeLayout settingLayout;
    private RelativeLayout loginLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_left_menu_layout, container, false);

        mainTagLayout = (RelativeLayout) root.findViewById(R.id.main_page_tag_layout);
        userListLayout = (RelativeLayout) root.findViewById(R.id.user_list_item_layout);
        settingLayout = (RelativeLayout) root.findViewById(R.id.setting_item_layout);
        loginLayout = (RelativeLayout) root.findViewById(R.id.login_item_layout);

        mainTagLayout.setOnClickListener(this);
        userListLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        loginLayout.setOnClickListener(this);

        return root;
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
            case R.id.setting_item_layout:
                mainActivity.setFragmentTransaction(IntentUtil.SETTING_FRAGMENT);
                break;
            case R.id.login_item_layout:
                mainActivity.setFragmentTransaction(IntentUtil.MAIN_TO_LOGIN_PAGE);
                Intent intent=new Intent(this.getActivity(),LoginActivity.class);
                getActivity().startActivityForResult(intent,IntentUtil.MAIN_TO_LOGIN_PAGE);
                break;
        }
    }

}