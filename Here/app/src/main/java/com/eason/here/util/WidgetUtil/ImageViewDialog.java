package com.eason.here.util.WidgetUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eason.here.HttpUtil.HttpConfig;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.R;
import com.eason.here.util.CommonUtil;

/**
 * Created by Eason on 12/22/15.
 */
public class ImageViewDialog extends Dialog {

    private ImageView imageView;
    private RelativeLayout backgroundLayout;
    private String url;

    public ImageViewDialog(Context context , String url) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_imageview);
        imageView = (ImageView) findViewById(R.id.dialog_image_view);
        backgroundLayout = (RelativeLayout) findViewById(R.id.dialog_image_view_background_layout);
        if (!CommonUtil.isEmptyString(url)){
            HttpRequest.loadImage(imageView, HttpConfig.String_Url_Media+url);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewDialog.this.dismiss();
            }
        });
    }
}
