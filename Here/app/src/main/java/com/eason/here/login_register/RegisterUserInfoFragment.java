package com.eason.here.login_register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eason.here.BaseFragment;
import com.eason.here.R;

/**
 * Created by Eason on 9/14/15.
 */
public class RegisterUserInfoFragment extends BaseFragment {

    private TextView test_text_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_user_info,container,false);
        test_text_view = (TextView)rootView.findViewById(R.id.test_text_view);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        test_text_view.setText(RegisterActivity.userAccount);
    }
}
