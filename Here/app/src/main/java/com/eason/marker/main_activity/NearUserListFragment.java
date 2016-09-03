package com.eason.marker.main_activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    private PostListViewAdapter postListViewAdapter;
    private static int LOAD_MORE_NUM = 20;
    private static boolean IS_LOAD_MORE = false;
    private MainMapFragment mainMapFragment;

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
        mainMapFragment = (MainMapFragment) ((MainActivity)getActivity()).getFragment(IntentUtil.MAIN_MAP_FRAGMENT);

        initData();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                            LOAD_MORE_NUM = LOAD_MORE_NUM + 20;
                            IS_LOAD_MORE = true;
                            getPost(mainMapFragment, LOAD_MORE_NUM);
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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

        postListViewAdapter = new PostListViewAdapter(getActivity(), MainActivity.postListItem);
        listView.setAdapter(postListViewAdapter);
    }

    @Override
    public void onRefresh() {
        if (mainMapFragment==null)return;
        LOAD_MORE_NUM = 20;
        IS_LOAD_MORE = false;
        getPost(mainMapFragment,LOAD_MORE_NUM);
    }

    private void getPost(final MainMapFragment mainMapFragment,int index) {

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (this.resultVO == null) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_REQUEST_FORM_INVALID);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CLIENT_DATA_ERROR) {
                    if (IS_LOAD_MORE){
                        mainActivity.getHandler().sendEmptyMessage(new Message().what = MainActivity.NONE_VALID_MORE_POST);
                    }else{
                        mainActivity.getHandler().sendEmptyMessage(new Message().what = MainActivity.NONE_VALID_POST);
                    }
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    PostList postList = (PostList) this.result;
                    if (postList == null) {
                        return;
                    }else if (IS_LOAD_MORE){
                        MainActivity.postListItem.addAll(postList.getPostList());
                        postListViewAdapter.notifyDataSetChanged();
                    }else{
                        MainActivity.postListItem = postList.getPostList();
                        initData();
                    }
                    mainMapFragment.setMarker();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        HttpRequest.getPost(LocationInfo.getLon(), LocationInfo.getLat(), LocationInfo.getCityName(),index, getPostHandler);
    }
}