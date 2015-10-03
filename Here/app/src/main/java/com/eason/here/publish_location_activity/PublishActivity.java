package com.eason.here.publish_location_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eason.here.BaseActivity;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.HttpUtil.ThreadPoolUtils;
import com.eason.here.R;
import com.eason.here.model.IntentUtil;
import com.eason.here.model.LoginStatus;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.LocationUtil;
import com.eason.here.util.LogUtil;

/**
 * 发帖页面
 * Created by Eason on 9/21/15.
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener{

    private EditText inputEditText;
    private Button publishButton;
    private LinearLayout postImageViewLayout;
    private ImageButton addImageButton;
    private ImageView postImageViewOne;
    private ImageView postImageViewTwo;
    private ImageView postImageViewThree;
    private ImageView postImageViewFour;

    private static int POST_IMAGE_VIEW_NUM = 1;

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
        postImageViewLayout = (LinearLayout) findViewById(R.id.post_image_view_layout);
        addImageButton = (ImageButton) findViewById(R.id.add_post_image_button);
        postImageViewOne = (ImageView) findViewById(R.id.post_image_view_one);
        postImageViewTwo = (ImageView) findViewById(R.id.post_image_view_two);
        postImageViewThree = (ImageView) findViewById(R.id.post_image_view_three);
        postImageViewFour = (ImageView) findViewById(R.id.post_image_view_four);
        addImageButton.setOnClickListener(this);
        publishButton.setOnClickListener(this);
        postImageViewOne.setOnClickListener(this);
        postImageViewTwo.setOnClickListener(this);
        postImageViewThree.setOnClickListener(this);
        postImageViewFour.setOnClickListener(this);
    }

    /**
     *
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_page_public_button:

                String shareContent = inputEditText.getText().toString();
                if (CommonUtil.isEmptyString(shareContent)) {
                    Toast.makeText(PublishActivity.this, "请输入在这干嘛呢", Toast.LENGTH_SHORT).show();
                } else {
                    publish(shareContent, null);
                }
                break;
            case R.id.add_post_image_button:
//                if (CommonUtil.isFastDoubleClick())return;
//                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                albumIntent.setType("image/*");
//                startActivityForResult(albumIntent, IntentUtil.SYSTEM_ABLUM_REQUEST_CODE);

                break;
            case R.id.post_image_view_one:

                break;
            case R.id.post_image_view_two:

                break;
            case R.id.post_image_view_three:

                break;
            case R.id.post_image_view_four:

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK)return;

        if (requestCode== IntentUtil.SYSTEM_ABLUM_REQUEST_CODE){
            switch (POST_IMAGE_VIEW_NUM){
                case 1:
                    postImageViewOne.setVisibility(View.VISIBLE);
                    POST_IMAGE_VIEW_NUM++;
                    break;
                case 2:
                    postImageViewTwo.setVisibility(View.VISIBLE);
                    POST_IMAGE_VIEW_NUM++;
                    break;
                case 3:
                    postImageViewThree.setVisibility(View.VISIBLE);
                    POST_IMAGE_VIEW_NUM++;
                    break;
                case 4:
                    postImageViewFour.setVisibility(View.VISIBLE);
                    addImageButton.setVisibility(View.GONE);
                    break;
            }
        }

    }
}
