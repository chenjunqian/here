package com.eason.marker.controller.profile_activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.eason.marker.controller.BaseActivity;
import com.eason.marker.MainApplication;
import com.eason.marker.R;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.emchat.chatuidemo.activity.ChatActivity;
import com.eason.marker.network.HttpConfig;
import com.eason.marker.network.HttpRequest;
import com.eason.marker.network.HttpResponseHandler;
import com.eason.marker.model.Constellation;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.model.User;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.view.CircleImageView;
import com.eason.marker.view.GreenToast;
import com.eason.marker.view.ImageViewDialog;
import com.eason.marker.view.ModelDialog;

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

    private final int SET_ENTER_CHAT_BUTTON_GONE = 0x1;

    private final int SET_ENTER_CHAT_BUTTON_VISIBLE = 0x2;
    /**
     * 主要用于判断该页面是显示用户自己的信息，还是显示别人的信息
     */
    private int page_status = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SET_ENTER_CHAT_BUTTON_GONE:
                    enterChatBtn.setVisibility(View.GONE);
                    break;
                case SET_ENTER_CHAT_BUTTON_VISIBLE:
                    enterChatBtn.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

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
        final String fromType = dataIntent.getStringExtra("come-from");
        if (IntentUtil.FROM_CHAT_ACTIVITY.equals(fromType)){
            handler.sendEmptyMessage(new Message().what=SET_ENTER_CHAT_BUTTON_GONE);
        }

        HttpResponseHandler getUserHandler = new HttpResponseHandler(){
            @Override
            public void getResult() {
                if (resultVO.getStatus()== ErroCode.ERROR_CODE_CORRECT){
                    user = (User) this.result;
                    nicknameTextView.setText(user.getNickname());
                    if (user.getGender().equals("male")){
                        genderTextView.setText(getResources().getString(R.string.profile_activity_gender_male));
                    }else{
                        genderTextView.setText(getResources().getString(R.string.profile_activity_gender_female));
                    }

                    if (LoginStatus.getUser()==null||LoginStatus.getUser().getUserid()==user.getUserid()){
                        handler.sendEmptyMessage(new Message().what=SET_ENTER_CHAT_BUTTON_GONE);
                    }else if (!IntentUtil.FROM_CHAT_ACTIVITY.equals(fromType)){
                        //由于网络可能慢的问题，所以先把进入私信界面的button设置为不可见，不然会因为没有获取到数据而崩溃
                        handler.sendEmptyMessage(new Message().what=SET_ENTER_CHAT_BUTTON_VISIBLE);
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
                    GreenToast.makeText(ProfileActivity.this,getResources().getString(R.string.profile_activity_failed_to_get_user_info), Toast.LENGTH_SHORT).show();
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
//                    GreenToast.makeText(ProfileActivity.this, "获取用户信息失败啦", Toast.LENGTH_SHORT).show();
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
                if (user==null|| !EMChatUtil.isConnectedEMChatServer){
                    GreenToast.makeText(ProfileActivity.this,getResources().getString(R.string.net_work_invalid),Toast.LENGTH_SHORT).show();
                    return;
                }
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

    @Override
    @TargetApi(21)
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
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
                viewsHolder.avatarView.setVisibility(View.INVISIBLE);
                viewsHolder.nicknameTextView = (TextView) convertView.findViewById(R.id.near_item_nickname);
                viewsHolder.nicknameTextView.setVisibility(View.GONE);
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
                    title.setText(getResources().getString(R.string.my_profile_is_sure_to_delete_my_post));
                    btnOK.setText(getResources().getString(R.string.my_profile_sure_to_delete_my_post));
                    btnCancel.setText(getResources().getString(R.string.my_profile_cancel_delete_my_post));

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            HttpResponseHandler deleteHandler = new HttpResponseHandler(){
                                @Override
                                public void getResult() {
                                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                                        postList.remove(position);
                                        ListViewAdapter.this.notifyDataSetChanged();
                                        GreenToast.makeText(ProfileActivity.this,getResources().getString(R.string.my_profile_success_to_delete_my_post),Toast.LENGTH_SHORT).show();
                                    }else{
                                        GreenToast.makeText(ProfileActivity.this,getResources().getString(R.string.my_profile_failed_to_delete_my_post),Toast.LENGTH_SHORT).show();
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
