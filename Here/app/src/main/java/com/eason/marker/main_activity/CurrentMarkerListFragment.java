package com.eason.marker.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class CurrentMarkerListFragment extends BaseFragment {

    private ListView listView;
    private View header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_list_layout, container, false);
        listView = (ListView) root.findViewById(R.id.post_list_view);
        header = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_current_post_header_layout,null);
        listView.addHeaderView(header);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HttpResponseHandler getPostByTimeHandle = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    if (this.resultVO.getErrorMessage().equals("get_last_100_post_success")){
                        TextView title = (TextView) header.findViewById(R.id.current_post_list_header_tile);
                        title.setText(R.string.current_list_page_number_status_title);
                    }
                    listView.setAdapter(new PostListViewAdapter(getActivity(), postList.getPostList()));
                } else {
                    GreenToast.makeText(getActivity(), "获取帖子失败啦", Toast.LENGTH_SHORT).show();
                }
            }
        };

        HttpRequest.getpostByTime(String.valueOf(System.currentTimeMillis()), getPostByTimeHandle);
    }
}
