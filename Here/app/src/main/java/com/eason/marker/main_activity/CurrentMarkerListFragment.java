package com.eason.marker.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private TextView headerTitle;
    private TextView headerSubTitle;
    private RelativeLayout headerRelativeLayout;
    private static int LOAD_MORE_NUM = 20;
    private boolean isShowNoMorePostToast = true;
    private boolean isGetOneHourPostSuccess = true;

    /**
     * 只获取最近的帖子
     */
    private final int ONLY_GET_CURRENT_POST = 0X1;
    /**
     * 只获取最近一小时的帖子
     */
    private final int ONLY_ONE_HOUR_CURRENT_POST = 0X2;
    /**
     * 看是否有最近一小时的帖子，如果没有服务器就会返回最近的帖子
     */
    private final int GET_CURRENT_POST = 0X3;

    private int GET_POST_TYPE_TAG = 0;

    private int CLICK_GET_POST_TYPE_TAG = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        header = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_current_post_header_layout, null);
        headerTitle = (TextView) header.findViewById(R.id.current_post_list_header_tile);
        headerSubTitle = (TextView) header.findViewById(R.id.current_post_list_header_sub_tile);
        headerRelativeLayout = (RelativeLayout) header.findViewById(R.id.current_post_list_header_layout);
        listView.addHeaderView(header);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.post_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.universal_title_background_red);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setRefreshing(true);
        loadData(LOAD_MORE_NUM, GET_CURRENT_POST);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1) && isShowNoMorePostToast) {
                            LOAD_MORE_NUM = LOAD_MORE_NUM + 20;
                            loadData(LOAD_MORE_NUM, GET_POST_TYPE_TAG);
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        headerRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGetOneHourPostSuccess)return;
                postListGlobal = null;
                LOAD_MORE_NUM = 20;
                swipeRefreshLayout.setRefreshing(true);
                loadData(LOAD_MORE_NUM, CLICK_GET_POST_TYPE_TAG);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadData(int index, int type) {

        HttpResponseHandler getPostByTimeHandle = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    if (this.resultVO.getErrorMessage().equals("get_last_100_post_success")) {
                        /**
                         * 没有最近一小时的帖子，服务器传最新的帖子
                         */
                        GET_POST_TYPE_TAG = GET_CURRENT_POST;
                        isGetOneHourPostSuccess = false;//点击头部layout无效
                        headerTitle.setText(R.string.current_list_page_number_status_title);
                        headerSubTitle.setVisibility(View.GONE);
                    } else if (this.resultVO.getErrorMessage().equals("get_post_success")){
                        /**
                         * 成功获取最近一小时的帖子
                         */
                        GET_POST_TYPE_TAG = GET_CURRENT_POST;
                        CLICK_GET_POST_TYPE_TAG = ONLY_GET_CURRENT_POST;//让点击头部layout获取最新的帖子
                        isGetOneHourPostSuccess = true;
                        headerTitle.setText(R.string.current_list_page_time_status_title);
                        headerSubTitle.setVisibility(View.VISIBLE);
                        headerSubTitle.setText(R.string.current_list_page_time_status_sub_title);
                    }  else if (this.resultVO.getErrorMessage().equals("get_only_last_100_post_success")) {
                        /**
                         * 只获取最新的帖子
                         */
                        GET_POST_TYPE_TAG = ONLY_GET_CURRENT_POST;
                        CLICK_GET_POST_TYPE_TAG = ONLY_ONE_HOUR_CURRENT_POST;//让点击头部layout获取最近一小时的帖子
                        isGetOneHourPostSuccess = true;
                        headerTitle.setText(R.string.current_list_page_number_status_title_only_get_current);
                        headerSubTitle.setVisibility(View.VISIBLE);
                        headerSubTitle.setText(R.string.current_list_page_time_status_sub_title_now);
                    } else if (this.resultVO.getErrorMessage().equals("get_one_hour_post_success")) {
                        /**
                         * 只获取最近一小时的帖子
                         */
                        GET_POST_TYPE_TAG = ONLY_ONE_HOUR_CURRENT_POST;
                        CLICK_GET_POST_TYPE_TAG = ONLY_GET_CURRENT_POST;//让点击头部layout获取最新的帖子
                        isGetOneHourPostSuccess = true;
                        headerTitle.setText(R.string.current_list_page_time_status_title);
                        headerSubTitle.setVisibility(View.VISIBLE);
                        headerSubTitle.setText(R.string.current_list_page_time_status_sub_title);
                    }else{
                        GreenToast.makeText(getActivity(),getActivity().getResources().
                                getString(R.string.net_work_invalid),Toast.LENGTH_SHORT).show();
                    }

                    //设置分页效果
                    if (postListGlobal != null) {
                        postListGlobal.getPostList().addAll(postList.getPostList());
                        postListViewAdapter.notifyDataSetChanged();
                    } else {
                        postListGlobal = postList;
                        postListViewAdapter = new PostListViewAdapter(getActivity(), postListGlobal.getPostList());
                        listView.setAdapter(postListViewAdapter);
                    }

                } else if (isShowNoMorePostToast) {
                    GreenToast.makeText(getActivity(), getActivity().getResources().getString(R.string.current_list_page_no_more_post), Toast.LENGTH_SHORT).show();
                    isShowNoMorePostToast = false;
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        switch (type){
            case ONLY_GET_CURRENT_POST:
                HttpRequest.getCurrentPostOnly(index, getPostByTimeHandle);
                break;
            case ONLY_ONE_HOUR_CURRENT_POST:
                HttpRequest.getOneHourPostOnly(String.valueOf(System.currentTimeMillis()), index, getPostByTimeHandle);
                break;
            case GET_CURRENT_POST:
                HttpRequest.getpostByTime(String.valueOf(System.currentTimeMillis()), index, getPostByTimeHandle);
                break;
        }

    }

    @Override
    public void onRefresh() {
        LOAD_MORE_NUM = 20;
        postListGlobal = null;
        isShowNoMorePostToast = true;
        loadData(LOAD_MORE_NUM, GET_POST_TYPE_TAG);
        swipeRefreshLayout.setRefreshing(false);
    }
}
