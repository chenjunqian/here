package com.eason.marker.main_activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LocationInfo;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.util.PostListViewAdapter;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Eason on 9/6/15.
 */
public class NearUserListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.post_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.universal_title_background_red);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData(){
        if (MainActivity.postListItem == null) return;
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

        listView.setAdapter(new PostListViewAdapter(getActivity(), MainActivity.postListItem));
    }

    @Override
    public void onRefresh() {
        MainMapFragment mainMapFragment = (MainMapFragment) ((MainActivity)getActivity()).getFragment(IntentUtil.MAIN_MAP_FRAGMENT);
        if (mainMapFragment==null)return;
        getPost(mainMapFragment);
    }

    public void getPost(final MainMapFragment mainMapFragment) {

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (this.resultVO == null) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_REQUEST_FORM_INVALID);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CLIENT_DATA_ERROR) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = MainActivity.NONE_VALID_POST);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    PostList postList = (PostList) this.result;
                    if (postList == null) return;
                    MainActivity.postListItem = postList.getPostList();
                    mainMapFragment.setMarker();
                    initData();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        HttpRequest.getPost(LocationInfo.getLon(), LocationInfo.getLat(), LocationInfo.getCityName(), getPostHandler);
    }
}
