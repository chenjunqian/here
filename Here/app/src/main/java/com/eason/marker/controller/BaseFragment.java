package com.eason.marker.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Eason on 9/14/15.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
