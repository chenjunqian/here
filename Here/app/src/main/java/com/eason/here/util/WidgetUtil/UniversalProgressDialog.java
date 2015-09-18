package com.eason.here.util.WidgetUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.eason.here.R;

/**
 * 全局的通用ProgressBar
 * Created by Eason on 9/18/15.
 */
public class UniversalProgressDialog extends Dialog {

    public UniversalProgressDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widge_universal_progress_bar_layout);

    }
}
