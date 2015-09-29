package com.eason.here.publish_location_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eason.here.BaseActivity;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.HttpUtil.ThreadPoolUtils;
import com.eason.here.R;
import com.eason.here.model.LoginStatus;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.LocationUtil;
import com.eason.here.util.LogUtil;

/**
 * 发帖页面
 * Created by Eason on 9/21/15.
 */
public class PublishActivity extends BaseActivity {

    private EditText inputEditText;
    private Button publishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
        getLocation();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        inputEditText = (EditText) findViewById(R.id.publish_page_share_content_edit_text);
        publishButton = (Button) findViewById(R.id.publish_page_public_button);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shareContent = inputEditText.getText().toString();
                if (CommonUtil.isEmptyString(shareContent)) {
                    Toast.makeText(PublishActivity.this, "请输入在这干嘛呢", Toast.LENGTH_SHORT).show();
                } else {
                    publish(shareContent, null);
                }

            }
        });
    }

    private void getLocation(){

        Runnable locationRun = new Runnable() {
            @Override
            public void run() {
                LocationUtil.startGetLocation(PublishActivity.this);
            }
        };

        ThreadPoolUtils.execute(locationRun);
    }

    /**
     * 发帖
     *
     * @param shareContent
     * @param httpResponseHandler
     */
    private void publish(String shareContent, HttpResponseHandler httpResponseHandler) {
        String longitude = String.valueOf(LocationUtil.getLongitude());
        String latitude = String.valueOf(LocationUtil.getLatitude());
        String city = LocationUtil.getCityName();
        String userid = LoginStatus.getUser().getUserId();
        LogUtil.d("PublishActivity","longitude : "+longitude+" latitude : "+latitude+" city : "+city+" userid : "+userid);
//        HttpRequest.uploadPost(longitude, latitude, city, userid, shareContent, httpResponseHandler);
    }
}
