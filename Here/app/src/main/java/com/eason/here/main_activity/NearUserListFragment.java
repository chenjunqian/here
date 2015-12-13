package com.eason.here.main_activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eason.here.BaseFragment;
import com.eason.here.HttpUtil.HttpConfig;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.model.ErroCode;
import com.eason.here.model.Post;
import com.eason.here.model.User;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.WidgetUtil.CircleImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Eason on 9/6/15.
 */
public class NearUserListFragment extends BaseFragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Collections.sort(MainActivity.postListItem, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }

            @Override
            public boolean equals(Object object) {
                return false;
            }
        });

        listView.setAdapter(new PostListViewAdapter(getActivity(),MainActivity.postListItem));
    }

    private class PostListViewAdapter extends BaseAdapter{

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
                viewsHolder.addressTextView = (TextView) convertView.findViewById(R.id.near_item_address_text_view);
                viewsHolder.avatarView = (CircleImageView) convertView.findViewById(R.id.near_item_avatar);
                viewsHolder.nicknameTextView = (TextView) convertView.findViewById(R.id.near_item_nickname);
                viewsHolder.postTagTextView = (TextView) convertView.findViewById(R.id.near_item_post_tag_text_view);
                viewsHolder.postTimeTextView = (TextView) convertView.findViewById(R.id.near_post_item_post_time_text_view);
                convertView.setTag(viewsHolder);
            }else{
                viewsHolder = (ViewsHolder) convertView.getTag();
            }


            HttpResponseHandler getUserInfoHandler = new HttpResponseHandler(){
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        User user = (User) this.result;
                        //设置内容
                        viewsHolder.nicknameTextView.setText(user.getNickname());
                        viewsHolder.addressTextView.setText(post.getAddress());
                        viewsHolder.postTagTextView.setText(post.getTag());
                        HttpRequest.loadImage(viewsHolder.avatarView, HttpConfig.String_Url_Media+user.getAvatar());
                        if (CommonUtil.isEmptyString(post.getTime())||post.getTime().equals("null")){
                            viewsHolder.postTimeTextView.setText(CommonUtil.formatTimeMillis(System.currentTimeMillis()));
                        }else{
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
