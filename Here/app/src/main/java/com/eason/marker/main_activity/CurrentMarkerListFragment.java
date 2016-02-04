package com.eason.marker.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.PostList;
import com.eason.marker.util.PostListViewAdapter;
import com.eason.marker.util.WidgetUtil.GreenToast;


/**
 * Created by Eason on 1/13/16.
 */
public class CurrentMarkerListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private PostListViewAdapter postListViewAdapter;
    private View header;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostList postListGlobal;
    private static int LOAD_MORE_NUM = 20;
    private boolean isShowNoMorePostToast = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        header = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_current_post_header_layout, null);
        listView.addHeaderView(header);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.post_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.universal_title_background_red);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(LOAD_MORE_NUM);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    if (listView.getLastVisiblePosition() == (listView.getCount()-1)&&isShowNoMorePostToast) {
                        LOAD_MORE_NUM = LOAD_MORE_NUM + 20;
                        loadData(LOAD_MORE_NUM);
                    }

                    break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadData(int index) {

        HttpResponseHandler getPostByTimeHandle = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    if (this.resultVO.getErrorMessage().equals("get_last_100_post_success")) {
                        TextView title = (TextView) header.findViewById(R.id.current_post_list_header_tile);
                        title.setText(R.string.current_list_page_number_status_title);
                    }

                    //设置分页效果
                    if (postListGlobal!=null){
                        postListGlobal.getPostList().addAll(postList.getPostList());
                        postListViewAdapter.notifyDataSetChanged();
                    }else{
                        postListGlobal = postList;
                        postListViewAdapter = new PostListViewAdapter(getActivity(), postListGlobal.getPostList());
                        listView.setAdapter(postListViewAdapter);
                    }


                } else if (isShowNoMorePostToast){
                    GreenToast.makeText(getActivity(), "没有帖子啦", Toast.LENGTH_SHORT).show();
                    isShowNoMorePostToast = false;
                }
            }
        };

        HttpRequest.getpostByTime(String.valueOf(System.currentTimeMillis()),index, getPostByTimeHandle);
    }

    @Override
    public void onRefresh() {
        LOAD_MORE_NUM = 20;
        postListGlobal = null;
        isShowNoMorePostToast = true;
        loadData(LOAD_MORE_NUM);
        swipeRefreshLayout.setRefreshing(false);
    }
}
