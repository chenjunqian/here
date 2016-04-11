package com.eason.marker.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eason.marker.R;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.Post;
import com.eason.marker.model.User;
import com.eason.marker.util.WidgetUtil.CircleImageView;
import com.eason.marker.util.WidgetUtil.ImageViewDialog;
import com.eason.marker.util.WidgetUtil.ListItemDialog;

import java.util.List;

/**
 * Created by Eason on 1/13/16.
 */
public class PostListViewAdapter extends BaseAdapter {

    private List<Post> postList;
    private Context context;

    public PostListViewAdapter(Context context,List<Post> postList){
        this.postList = postList;
        this.context = context;
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
        if (post==null)return null;

        if (convertView==null){
            viewsHolder = new ViewsHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.near_post_item_layout,null);
            viewsHolder.backgroundLayout = (RelativeLayout) convertView.findViewById(R.id.near_item_background_layout);
            viewsHolder.addressTextView = (TextView) convertView.findViewById(R.id.near_item_address_text_view);
            viewsHolder.avatarView = (CircleImageView) convertView.findViewById(R.id.near_item_avatar);
            viewsHolder.nicknameTextView = (TextView) convertView.findViewById(R.id.near_item_nickname);
            viewsHolder.simpleProfile = (TextView) convertView.findViewById(R.id.near_item_simple_profile_text_view);
            viewsHolder.postTagTextView = (TextView) convertView.findViewById(R.id.near_item_post_tag_text_view);
            viewsHolder.postTimeTextView = (TextView) convertView.findViewById(R.id.near_post_item_post_time_text_view);
            convertView.setTag(viewsHolder);
        }else{
            viewsHolder = (ViewsHolder) convertView.getTag();
        }

        viewsHolder.setData(context,post);

        return convertView;
    }

    private static class ViewsHolder{
        public TextView nicknameTextView;
        public TextView simpleProfile;
        public CircleImageView avatarView;
        public TextView postTagTextView;
        public TextView addressTextView;
        public TextView postTimeTextView;
        public RelativeLayout backgroundLayout;

        private User user;

        public void setData(final Context context,final Post post){

            HttpResponseHandler getUserInfoHandler = new HttpResponseHandler(){
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        user = (User) this.result;
                        //设置内容
                        nicknameTextView.setText(user.getNickname());
                        simpleProfile.setText(user.getSimpleProfile());
                        addressTextView.setText(post.getAddress());
                        postTagTextView.setText(post.getTag());
                        HttpRequest.loadImage(avatarView, HttpConfig.String_Url_Media + user.getAvatar(), 150, 150);
                        if (CommonUtil.isEmptyString(post.getTime())||post.getTime().equals("null")){
                            postTimeTextView.setText(CommonUtil.formatTimeMillis(System.currentTimeMillis()));
                        }else{
                            postTimeTextView.setText(CommonUtil.formatTimeMillis(Long.valueOf(post.getTime())));
                        }
                    }
                }
            };

            //由username获取用户信息
            HttpRequest.getUserByUsername(post.getUsername(), getUserInfoHandler);

            avatarView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user == null) return;
                    ImageViewDialog imageViewDialog = new ImageViewDialog(context, user.getAvatar());
                    imageViewDialog.show();
                }
            });

            backgroundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (post==null||user==null)return;
                    ListItemDialog listItemDialog = new ListItemDialog(context,post,user);
                    listItemDialog.show();
                }
            });
        }
    }
}
