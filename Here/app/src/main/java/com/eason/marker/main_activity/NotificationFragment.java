package com.eason.marker.main_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.NotificationMessage;
import com.eason.marker.model.NotificationMessageList;
import com.eason.marker.model.User;
import com.eason.marker.view.CircleImageView;

import java.util.List;

/**
 * Created by Eason on 3/13/16.
 */
public class NotificationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private NotificationItemAdapter notificationItemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int LOAD_MORE_NUM = 20;
    public static NotificationMessageList notificationMessageList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = (ListView) root.findViewById(R.id.notification_fragment_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.notification_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.universal_title_background_red);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
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

        swipeRefreshLayout.setRefreshing(true);
        loadData(LOAD_MORE_NUM);
    }

    private void loadData(int index) {

        HttpResponseHandler getNotificationMessageHandle = new HttpResponseHandler() {
            @Override
            public void getResult() {
                if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    NotificationMessageList tempList = (NotificationMessageList) this.result;

                    if (notificationMessageList == null) {
                        notificationMessageList = tempList;
                        notificationItemAdapter = new NotificationItemAdapter(getActivity(),
                                notificationMessageList.getNotificationMessageList());
                        listView.setAdapter(notificationItemAdapter);
                    } else {
                        notificationMessageList.getNotificationMessageList().addAll(tempList.getNotificationMessageList());
                        notificationItemAdapter.notifyDataSetChanged();
                    }
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        //获取通知信息
        HttpRequest.getSystemNotificationMessage(index, getNotificationMessageHandle);
    }

    @Override
    public void onRefresh() {
        notificationMessageList = null;
        LOAD_MORE_NUM = 20;
        loadData(LOAD_MORE_NUM);
    }


    class NotificationItemAdapter extends BaseAdapter {

        private List<NotificationMessage> notificationMessageList;
        private Context context;

        public NotificationItemAdapter(Context context, List<NotificationMessage> notificationMessageList) {
            this.notificationMessageList = notificationMessageList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return notificationMessageList != null ? notificationMessageList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return notificationMessageList != null ? notificationMessageList.get(position) : 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.notification_fragment_list_view_item, null);
                viewHolder = new ViewHolder();
                viewHolder.notificationAvatar = (CircleImageView) convertView.findViewById(R.id.notification_message_avatar);
                viewHolder.notificationMessageTextView = (TextView) convertView.findViewById(R.id.notification_message_content);
                viewHolder.notificationTitleTextView = (TextView) convertView.findViewById(R.id.notification_message_title);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.setData(notificationMessageList.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        public TextView notificationTitleTextView;
        public TextView notificationMessageTextView;
        public CircleImageView notificationAvatar;
        private User user;

        public void setData(NotificationMessage notificationMessage) {
            notificationTitleTextView.setText(notificationMessage.getTitle());
            notificationMessageTextView.setText(notificationMessage.getMessage());

            HttpResponseHandler getUserInfoHandler = new HttpResponseHandler() {
                @Override
                public void getResult() {
                    super.getResult();
                    if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                        user = (User) this.result;
                        //设置内容
                        HttpRequest.loadImage(notificationAvatar, HttpConfig.String_Url_Media + user.getAvatar(), 150, 150);
                    }
                }
            };

            //由username获取用户信息
            HttpRequest.getUserByUsername(notificationMessage.getFromusername(), getUserInfoHandler);
        }
    }
}
