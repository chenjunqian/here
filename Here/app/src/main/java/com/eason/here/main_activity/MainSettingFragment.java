package com.eason.here.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eason.here.BaseFragment;
import com.eason.here.R;

/**
 * Created by Eason on 9/6/15.
 */
public class MainSettingFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_setting_layout,container,false);

        return root;
    }
}
