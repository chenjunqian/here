package com.eason.here.publish_location_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eason.here.BaseActivity;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.model.ErroCode;
import com.eason.here.model.IntentUtil;
import com.eason.here.model.LocationInfo;
import com.eason.here.model.LoginStatus;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.ImageProcessParams;
import com.eason.here.util.ImageScan.ImageScanMainActivity;
import com.eason.here.util.LogUtil;
import com.eason.here.util.WidgetUtil.GreenToast;

/**
 * 发帖页面
 * Created by Eason on 9/21/15.
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener{

    private static String TAG = "PublishActivity";
    private EditText inputEditText;
    private Button publishButton;
    private LinearLayout postImageViewLayout;
    private ImageButton addImageButton;
    private ImageView postImageViewOne;
    private ImageView postImageViewTwo;
    private ImageView postImageViewThree;
    private ImageView postImageViewFour;
    private boolean isImagePost = false;

    private static int POST_IMAGE_VIEW_NUM = 1;
    private static int CLICK_IMAGE_VIEW_NUM = 1;

    private final static int POST_SUCCESS  = 0X1;
    private final static int POST_FAIL  = 0X2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ErroCode.ERROR_CODE_CLIENT_DATA_ERROR:
                    GreenToast.makeText(PublishActivity.this,"发帖失败咯，我也不知道为什么，想想可能发生些什么吧",Toast.LENGTH_SHORT).show();
                    break;
                case POST_SUCCESS:
                    GreenToast.makeText(PublishActivity.this,"发帖成功",Toast.LENGTH_SHORT).show();
                    break;
                case POST_FAIL:
                    GreenToast.makeText(PublishActivity.this,"发帖失败，可能是服务器的问题啦",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
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
     * 发帖
     *
     * @param shareContent
     * @param httpResponseHandler
     */
    private void publish(String shareContent, HttpResponseHandler httpResponseHandler) {
        String longitude = String.valueOf(LocationInfo.getLon());
        String latitude = String.valueOf(LocationInfo.getLat());
        String city = LocationInfo.getCityName();
        String cityCode = LocationInfo.getCityCode();
        String address = LocationInfo.getAddress();
        String username  = LoginStatus.getUser().getUsername();
        LogUtil.d("PublishActivity", "longitude : " + longitude + " latitude : " + latitude + " city : " + city + " username : " + username);
        if (isImagePost){

        }else{
            HttpRequest.uploadPostWithoutImage(longitude, latitude, city,cityCode,address ,username, shareContent, httpResponseHandler);
        }
    }

    private void showPostImageView(){
        switch (POST_IMAGE_VIEW_NUM){
            case 1:
                postImageViewOne.setVisibility(View.VISIBLE);
                break;
            case 2:
                postImageViewTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                postImageViewThree.setVisibility(View.VISIBLE);
                break;
            case 4:
                postImageViewFour.setVisibility(View.VISIBLE);
                addImageButton.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_page_public_button:

                HttpResponseHandler postHandler = new HttpResponseHandler(){
                    @Override
                    public void getResult() {
                        super.getResult();
                        if (this.resultVO == null) {
                            handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                        } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                            handler.sendEmptyMessage(new Message().what = POST_SUCCESS);
                        }else{
                            handler.sendEmptyMessage(new Message().what = POST_FAIL);
                        }
                    }
                };

                String shareContent = inputEditText.getText().toString();
                if (CommonUtil.isEmptyString(shareContent)) {
                    GreenToast.makeText(PublishActivity.this, "请输入要分享的内容", Toast.LENGTH_SHORT).show();
                } else {
                    publish(shareContent, postHandler);
                }

                break;
            case R.id.add_post_image_button:
                showPostImageView();
                break;
            case R.id.post_image_view_one:
                /**
                 * 打开手机系统相册
                 */
                Intent albumIntent = new Intent(PublishActivity.this, ImageScanMainActivity.class);
                startActivityForResult(albumIntent, IntentUtil.SYSTEM_ABLUM_REQUEST_CODE);
                CLICK_IMAGE_VIEW_NUM = 1;
                break;
            case R.id.post_image_view_two:
                CLICK_IMAGE_VIEW_NUM = 2;

                break;
            case R.id.post_image_view_three:
                CLICK_IMAGE_VIEW_NUM = 3;

                break;
            case R.id.post_image_view_four:
                CLICK_IMAGE_VIEW_NUM = 4;

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK)return;

        if (requestCode== IntentUtil.SYSTEM_ABLUM_REQUEST_CODE){
            String imagePath = data
                    .getStringExtra(ImageProcessParams.IMAGE_PATH_EXTRA_NAME);
            LogUtil.d(TAG, "imagePath : " + imagePath);

            Bitmap avatarBitmap = CommonUtil.loadBitmap(imagePath);

            if (avatarBitmap==null)return;
            switch (CLICK_IMAGE_VIEW_NUM){
                case 1:
                    postImageViewOne.setImageBitmap(avatarBitmap);
                    break;
                case 2:
                    postImageViewTwo.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    postImageViewThree.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    postImageViewFour.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }
}
