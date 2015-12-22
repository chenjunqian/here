package com.eason.here.profile_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.here.BaseActivity;
import com.eason.here.HttpUtil.HttpConfig;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.model.Constellation;
import com.eason.here.model.ErroCode;
import com.eason.here.model.Post;
import com.eason.here.model.PostList;
import com.eason.here.model.User;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.WidgetUtil.CircleImageView;
import com.eason.here.util.WidgetUtil.GreenToast;
import com.eason.here.util.WidgetUtil.ImageViewDialog;

import java.util.List;

/**
 * Created by Eason on 12/22/15.
 */
public class ProfileActivity extends BaseActivity {

    private CircleImageView avatar;
    private TextView nicknameTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private TextView constellationTextView;
    private TextView simpleProfileTextView;
    private TextView longProfileTextView;
    private ListView historyPostListView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }

    private void initView(){
        nicknameTextView = (TextView) findViewById(R.id.profile_activity_title_nickname_text_view);
        genderTextView = (TextView) findViewById(R.id.profile_activity_gender_text_view);
        ageTextView = (TextView) findViewById(R.id.profile_activity_age_text_view);
        constellationTextView = (TextView) findViewById(R.id.profile_activity_constellation_text_view);
        simpleProfileTextView = (TextView) findViewById(R.id.simple_profile_text_view);
        longProfileTextView = (TextView) findViewById(R.id.profile_activity_profile_text_view);
        historyPostListView = (ListView) findViewById(R.id.profile_activity_list_view);
        avatar = (CircleImageView) findViewById(R.id.profile_activity_avatar_circle_view);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user==null)return;
                ImageViewDialog imageViewDialog = new ImageViewDialog(ProfileActivity.this,user.getAvatar());
                imageViewDialog.show();
            }
        });

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
                    }else if (!CommonUtil.isEmptyString(user.getLongProfile())){
                        longProfileTextView.setText(user.getLongProfile());
                    }

                    if (!CommonUtil.isEmptyString(user.getAvatar())){
                        HttpRequest.loadImage(avatar, HttpConfig.String_Url_Media + user.getAvatar());
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
                    historyPostListView.setAdapter(new ListViewAdapter(ProfileActivity.this, postList.getPostList()));
                } else {
                    GreenToast.makeText(ProfileActivity.this, "获取用户信息失败啦", Toast.LENGTH_SHORT).show();
                    ProfileActivity.this.fileList();
                }
            }
        });
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
        public View getView(int position, View convertView, ViewGroup parent) {
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
                viewsHolder.postTimeTextView = (TextView) convertView.findViewById(R.id.near_post_item_post_time_text_view);
                convertView.setTag(viewsHolder);
            } else {
                viewsHolder = (ViewsHolder) convertView.getTag();
            }


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
                        HttpRequest.loadImage(viewsHolder.avatarView, HttpConfig.String_Url_Media + user.getAvatar());
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
        public CircleImageView avatarView;
        public TextView postTagTextView;
        public TextView addressTextView;
        public TextView postTimeTextView;
    }
}
