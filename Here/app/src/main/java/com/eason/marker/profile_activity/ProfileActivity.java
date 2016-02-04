package com.eason.marker.profile_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseActivity;
import com.eason.marker.MainApplication;
import com.eason.marker.R;
import com.eason.marker.emchat.chatuidemo.activity.ChatActivity;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.Constellation;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.model.User;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.WidgetUtil.CircleImageView;
import com.eason.marker.util.WidgetUtil.GreenToast;
import com.eason.marker.util.WidgetUtil.ImageViewDialog;
import com.eason.marker.util.WidgetUtil.ModelDialog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Eason on 12/22/15.
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener{

    private CircleImageView avatar;
    private TextView nicknameTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private TextView constellationTextView;
    private TextView simpleProfileTextView;
    private TextView longProfileTextView;
    private ListView historyPostListView;
    private RelativeLayout userInfoLayout;
    private ImageButton enterChatBtn;

    private User user;
    /**
     * 主要用于判断该页面是显示用户自己的信息，还是显示别人的信息
     */
    private int page_status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initData();
    }

    private void initView(){
        nicknameTextView = (TextView) findViewById(R.id.profile_activity_title_nickname_text_view);
        genderTextView = (TextView) findViewById(R.id.profile_activity_gender_text_view);
        ageTextView = (TextView) findViewById(R.id.profile_activity_age_text_view);
        constellationTextView = (TextView) findViewById(R.id.profile_activity_constellation_text_view);
        simpleProfileTextView = (TextView) findViewById(R.id.simple_profile_text_view);
        longProfileTextView = (TextView) findViewById(R.id.profile_activity_long_profile_text_view);
        historyPostListView = (ListView) findViewById(R.id.profile_activity_list_view);
        avatar = (CircleImageView) findViewById(R.id.profile_activity_avatar_circle_view);
        avatar.setOnClickListener(this);
        userInfoLayout = (RelativeLayout) findViewById(R.id.profile_activity_user_info_layout);
        enterChatBtn = (ImageButton) findViewById(R.id.enter_chat_activity_image_button);
        enterChatBtn.setOnClickListener(this);

        page_status = getIntent().getIntExtra(IntentUtil.IS_SHOW_MY_USER_INFO_LAYOUT_STRING,0);
        if (page_status==IntentUtil.IS_SHOW_USER_INFO_LAYOUT_INT){
            userInfoLayout.setVisibility(View.GONE);
        }

    }
    private void initData(){
        Intent dataIntent = getIntent();
        String username = dataIntent.getStringExtra("username");

        HttpResponseHandler getUserHandler = new HttpResponseHandler(){
            @Override
            public void getResult() {
                if (resultVO.getStatus()== ErroCode.ERROR_CODE_CORRECT){
                    user = (User) this.result;
                    nicknameTextView.setText(user.getNickname());
                    if (user.getGender().equals("male")){
                        genderTextView.setText("男");
                    }else{
                        genderTextView.setText("女");
                    }

                    String[] birthday = user.getBirthday().split("-");
                    constellationTextView.setText(Constellation.getConstellation(Integer.valueOf(birthday[1]),
                            Integer.valueOf(birthday[2])));

                    if (!CommonUtil.isEmptyString(user.getSimpleProfile())){
                        simpleProfileTextView.setText(user.getSimpleProfile());
                    }

                    if (!CommonUtil.isEmptyString(user.getLongProfile())){
                        longProfileTextView.setText(user.getLongProfile());
                    }

                    if (!CommonUtil.isEmptyString(user.getAvatar())){
                        HttpRequest.loadImage(avatar, HttpConfig.String_Url_Media + user.getAvatar(),150,150);
                    }


                }else{
                    GreenToast.makeText(ProfileActivity.this,"获取用户信息失败啦", Toast.LENGTH_SHORT).show();
                    ProfileActivity.this.fileList();
                }
            }
        };

        HttpRequest.getUserByUsername(username,getUserHandler);

        HttpRequest.getPosyByUsername(username, new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    List<Post> postListItem = postList.getPostList();
                    Collections.sort(postListItem, new Comparator<Post>() {
                        @Override
                        public int compare(Post lhs, Post rhs) {
                            return rhs.getTime().compareTo(lhs.getTime());
                        }

                        @Override
                        public boolean equals(Object object) {
                            return false;
                        }
                    });

                    historyPostListView.setAdapter(new ListViewAdapter(ProfileActivity.this, postListItem));
                } else {
                    GreenToast.makeText(ProfileActivity.this, "获取用户信息失败啦", Toast.LENGTH_SHORT).show();
                    ProfileActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_activity_avatar_circle_view:
                if (user==null)return;
                ImageViewDialog imageViewDialog = new ImageViewDialog(ProfileActivity.this,user.getAvatar());
                imageViewDialog.show();
                break;
            case R.id.enter_chat_activity_image_button:
                // 进入聊天页面
                String userId = user.getUserid();
                if (user==null)return;
                if (userId.equals(MainApplication.getInstance().getUserName())){
                    GreenToast.makeText(ProfileActivity.this, getResources().getString(R.string.Cant_chat_with_yourself), 0).show();
                }else{
                    Intent intent = new Intent(ProfileActivity.this, ChatActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                    startActivity(intent);
                }

                break;
        }
    }


    private class ListViewAdapter extends BaseAdapter{

        private List<Post> postList;
        private Context context;

        public ListViewAdapter(Context context , List<Post> postList){
            this.context = context;
            this.postList = postList;
        }

        @Override
        public int getCount() {
            return postList==null?0:postList.size();
        }

        @Override
        public Object getItem(int position) {
            return postList==null?0:postList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewsHolder viewsHolder;
            final Post post = postList.get(position);
            if (post == null) return null;

            if (convertView == null) {
                viewsHolder = new ViewsHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.near_post_item_layout, null);
                viewsHolder.addressTextView = (TextView) convertView.findViewById(R.id.near_item_address_text_view);
                viewsHolder.avatarView = (CircleImageView) convertView.findViewById(R.id.near_item_avatar);
                viewsHolder.avatarView.setVisibility(View.GONE);
                viewsHolder.nicknameTextView = (TextView) convertView.findViewById(R.id.near_item_nickname);
                viewsHolder.nicknameTextView.setVisibility(View.INVISIBLE);
                viewsHolder.postTagTextView = (TextView) convertView.findViewById(R.id.near_item_post_tag_text_view);
                viewsHolder.simpleProfile = (TextView) convertView.findViewById(R.id.near_item_simple_profile_text_view);
                viewsHolder.simpleProfile.setVisibility(View.GONE);
                viewsHolder.postTimeTextView = (TextView) convertView.findViewById(R.id.near_post_item_post_time_text_view);
                viewsHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.post_item_delete_image_button);
                if (page_status==IntentUtil.IS_SHOW_USER_INFO_LAYOUT_INT){//如果是显示自己的页面，则显示删除键
                    viewsHolder.deleteBtn.setVisibility(View.VISIBLE);
                }
                convertView.setTag(viewsHolder);
            } else {
                viewsHolder = (ViewsHolder) convertView.getTag();
            }

            viewsHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出提示框，询问是否确认删除帖子
                    final ModelDialog mDialog = new ModelDialog(ProfileActivity.this,R.layout.dialog_back,R.style.Theme_dialog);
                    final Button btnOK, btnCancel;
                    final TextView title;
                    btnOK = (Button) mDialog.findViewById(R.id.ok_button);
                    btnCancel = (Button) mDialog.findViewById(R.id.cancel_button);
                    title = (TextView) mDialog.findViewById(R.id.alert_dialog_note_text);
                    title.setText("确定不要删除嘛");
                    btnOK.setText("是的");
                    btnCancel.setText("手滑了");

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            HttpResponseHandler deleteHandler = new HttpResponseHandler(){
                                @Override
                                public void getResult() {
                                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                                        postList.remove(position);
                                        ListViewAdapter.this.notifyDataSetChanged();
                                        GreenToast.makeText(ProfileActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                    }else{
                                        GreenToast.makeText(ProfileActivity.this,"因为一些原因，删除失败",Toast.LENGTH_SHORT).show();
                                    }
                                    mDialog.dismiss();
                                }

                            };

                            HttpRequest.deletePostById(LoginStatus.getUser().getUsername(), post.getPostId(),deleteHandler);
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
            });

            HttpResponseHandler getUserInfoHandler = new HttpResponseHandler() {
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        User user = (User) this.result;
                        //设置内容
                        viewsHolder.nicknameTextView.setText(user.getNickname());
                        viewsHolder.addressTextView.setText(post.getAddress());
                        viewsHolder.postTagTextView.setText(post.getTag());
                        HttpRequest.loadImage(viewsHolder.avatarView, HttpConfig.String_Url_Media + user.getAvatar(),150,150);
                        if (CommonUtil.isEmptyString(post.getTime()) || post.getTime().equals("null")) {
                            viewsHolder.postTimeTextView.setText(CommonUtil.formatTimeMillis(System.currentTimeMillis()));
                        } else {
                            viewsHolder.postTimeTextView.setText(CommonUtil.formatTimeMillis(Long.valueOf(post.getTime())));
                        }
                    }
                }
            };

            //由username获取用户信息
            HttpRequest.getUserByUsername(post.getUsername(), getUserInfoHandler);

            return convertView;
        }
    }

    private static class ViewsHolder{
        public TextView nicknameTextView;
        public TextView simpleProfile;
        public CircleImageView avatarView;
        public TextView postTagTextView;
        public TextView addressTextView;
        public TextView postTimeTextView;
        public ImageButton deleteBtn;
    }
}
