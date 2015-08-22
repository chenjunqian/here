package com.eason.here.main_activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eason.here.R;

/**
 * Created by Eason on 8/22/15.
 */
public class MenuLeftFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_left_menu_layout,container,false);

        return root;
    }
}
