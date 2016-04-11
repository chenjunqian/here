package com.eason.marker.util.WidgetUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eason.marker.R;
import com.eason.marker.util.CommonUtil;

/**
 * 弹出文字输入对话框，修改资料用
 * Created by Eason on 9/23/15.
 */
public class EditTextDialog extends Dialog {

    private EditText editText;
    private TextView textViewTitle;
    private OnFinishInputListener onFinishInputListener;
    private RelativeLayout parentLayout;
    private String preContent;
    private String titleText;

    /**
     * @param context
     * @param content               用于初始化对话框中EditText内容
     * @param onFinishInputListener 当用户点击对话框背景时调用的接口
     */
    public EditTextDialog(Context context, String title, String content, OnFinishInputListener onFinishInputListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.onFinishInputListener = onFinishInputListener;
        preContent = content;
        titleText = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_universal_edit_text_dialog);
        editText = (EditText) findViewById(R.id.widget_universal_edit_text);
        parentLayout = (RelativeLayout) findViewById(R.id.widget_universal_edit_text_back_ground_parent_layout);
        textViewTitle = (TextView) findViewById(R.id.widget_universal_text_view_title);
        //设置Title
        textViewTitle.setText(titleText);
        //设置输入框文字
        editText.setText(preContent);
        editText.setSelection(preContent.length());
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.isEmptyString(editText.getText().toString())) return;
                onFinishInputListener.onFinish(editText.getText().toString());
            }
        });
    }
}
