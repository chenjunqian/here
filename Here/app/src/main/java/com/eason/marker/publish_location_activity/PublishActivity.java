package com.eason.marker.publish_location_activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseActivity;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.R;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.LocationInfo;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostTag;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.LogUtil;
import com.eason.marker.util.WidgetUtil.GreenToast;
import com.eason.marker.util.WidgetUtil.ModelDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 发帖页面
 * Created by Eason on 9/21/15.
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "PublishActivity";
    private EditText addTagText;
    private EditText addAddressText;
    private Button publishButton;
    private GridView gridView;
    private List<String> tagList;

    private final static int POST_SUCCESS = 0X1;
    private final static int POST_FAIL = 0X2;
    private final static int GET_TAG_FAIL = 0X3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ErroCode.ERROR_CODE_CLIENT_DATA_ERROR:
                    GreenToast.makeText(PublishActivity.this, "发帖失败咯，我也不知道为什么，想想可能发生些什么吧", Toast.LENGTH_SHORT).show();
                    break;
                case POST_SUCCESS:
                    GreenToast.makeText(PublishActivity.this, "标记成功", Toast.LENGTH_SHORT).show();
                    PublishActivity.this.setResult(RESULT_OK);
                    PublishActivity.this.finish();
                    break;
                case POST_FAIL:
                    GreenToast.makeText(PublishActivity.this, "发帖失败，可能是服务器的问题啦", Toast.LENGTH_SHORT).show();
                    break;
                case GET_TAG_FAIL:
                    GreenToast.makeText(PublishActivity.this, "网络似乎出了点问题，重新试试", Toast.LENGTH_SHORT).show();
                    PublishActivity.this.finish();
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
        addTagText = (EditText) findViewById(R.id.publish_page_share_content_edit_text);
        publishButton = (Button) findViewById(R.id.publish_page_public_button);
        gridView = (GridView) findViewById(R.id.tag_grid_view);
        addAddressText = (EditText) findViewById(R.id.publish_page_share_address_edit_text);
        publishButton.setOnClickListener(this);

        addAddressText.setText(LocationInfo.getAddress());

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                if (this.resultVO == null) {
                    handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    PostTag tag = (PostTag) this.result;
                    String[] tagString = tag.getTag().split("@@");
                    tagList = new ArrayList<String>();
                    for (int i = 0; i < tagString.length; i++) {
                        tagList.add(tagString[i]);
                    }

                    gridView.setAdapter(new GridViewAdapter(PublishActivity.this, tagList));

                } else {
                    handler.sendEmptyMessage(new Message().what = GET_TAG_FAIL);
                }
            }
        };

        HttpRequest.getPostTag(getPostHandler);
    }

    /**
     * 发帖
     *
     * @param tag
     * @param httpResponseHandler
     */
    private void publish(String tag, HttpResponseHandler httpResponseHandler) {
        String longitude = String.valueOf(LocationInfo.getLon());
        String latitude = String.valueOf(LocationInfo.getLat());
        String city = LocationInfo.getCityName();
        String cityCode = LocationInfo.getCityCode();
        String address;
        if (CommonUtil.isEmptyString(addAddressText.getText().toString())){
            address = LocationInfo.getAddress();
        }else{
            address = addAddressText.getText().toString();
        }

        String username = LoginStatus.getUser().getUsername();
        LogUtil.d("PublishActivity", "longitude : " + longitude + " latitude : " + latitude + " city : " + city + " username : " + username);

        HttpRequest.uploadPostWithoutImage(longitude, latitude, city, cityCode, address, username, tag, httpResponseHandler);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_page_public_button:

                HttpResponseHandler postHandler = new HttpResponseHandler() {
                    @Override
                    public void getResult() {
                        super.getResult();
                        if (this.resultVO == null) {
                            handler.sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                        } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                            handler.sendEmptyMessage(new Message().what = POST_SUCCESS);
                            Post post = (Post) this.result;
                            LogUtil.d(TAG, "tag :  " + post.getTag());
                        } else {
                            handler.sendEmptyMessage(new Message().what = POST_FAIL);
                        }
                    }
                };

                String shareContent = addTagText.getText().toString();
                if (CommonUtil.isEmptyString(shareContent)) {
                    GreenToast.makeText(PublishActivity.this, "请选择或者添加分享标签", Toast.LENGTH_SHORT).show();
                } else {
                    publish(shareContent, postHandler);
                }

                break;

        }

    }


    private class GridViewAdapter extends BaseAdapter {

        private List<String> tagList;
        private Context context;
        private TextView textView;

        public GridViewAdapter(Context context, List<String> tagList) {
            this.context = context;
            this.tagList = tagList;
        }

        @Override
        public int getCount() {
            return tagList == null ? 0 : tagList.size();
        }

        @Override
        public Object getItem(int position) {
            return tagList == null ? 0 : tagList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final String tag = tagList.get(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.post_tag_grid_view_layout, null);

                textView = (TextView) convertView.findViewById(R.id.post_tag_grid_view_text_view);

                convertView.setTag(textView);
            } else {
                textView = (TextView) convertView.getTag();
            }

            textView.setText(tag);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTagText.setText(tag);
                    addTagText.setSelection(tag.length());
                }
            });

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        final ModelDialog mDialog = new ModelDialog(PublishActivity.this,R.layout.dialog_back,R.style.Theme_dialog);
        final Button btnOK, btnCancel;
        final TextView title;
        btnOK = (Button) mDialog.findViewById(R.id.ok_button);
        btnCancel = (Button) mDialog.findViewById(R.id.cancel_button);
        title = (TextView) mDialog.findViewById(R.id.alert_dialog_note_text);
        title.setText("确定不要分享了嘛");
        btnOK.setText("是的");
        btnCancel.setText("手滑了");

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PublishActivity.this.finish();
                mDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
