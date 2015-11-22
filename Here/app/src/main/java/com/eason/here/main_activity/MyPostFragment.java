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
import com.eason.here.R;
import com.eason.here.model.LoginStatus;
import com.eason.here.model.Post;
import com.eason.here.util.WidgetUtil.CircleImageView;

import java.util.List;

/**
 * Created by Eason on 11/19/15.
 */
public class MyPostFragment extends BaseFragment {

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
        listView.setAdapter(new PostListViewAdapter(getActivity(),MainActivity.postListItem));
    }

    private class PostListViewAdapter extends BaseAdapter {

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
            Post post = postList.get(position);
            if (post==null)return null;

            if (convertView==null){
                viewsHolder = new ViewsHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.near_post_item_layout,null);
                viewsHolder.addressTextView = (TextView) convertView.findViewById(R.id.near_item_address_text_view);
                viewsHolder.avatarView = (CircleImageView) convertView.findViewById(R.id.near_item_avatar);
                viewsHolder.nicknameTextView = (TextView) convertView.findViewById(R.id.near_item_nickname);
                viewsHolder.postTagTextView = (TextView) convertView.findViewById(R.id.near_item_post_tag_text_view);
                convertView.setTag(viewsHolder);
            }else{
                viewsHolder = (ViewsHolder) convertView.getTag();
            }

            viewsHolder.addressTextView.setText(post.getAddress());
            viewsHolder.postTagTextView.setText(post.getTag());
            viewsHolder.nicknameTextView.setText(LoginStatus.getUser().getNickname());

            return convertView;
        }
    }

    private static class ViewsHolder{
        public TextView nicknameTextView;
        public CircleImageView avatarView;
        public TextView postTagTextView;
        public TextView addressTextView;
    }
}
