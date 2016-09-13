package com.eason.marker.view;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eason.marker.R;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.User;
import com.eason.marker.util.CommonUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Eason on 2/11/16.
 */
public class ShareDialog extends Dialog {

    private ListView listView;
    private Context context;
    private ShareListAdapter shareListAdapter;
    private Post post;
    private User user;

    public ShareDialog(Context context,Post post ,User user) {
        super(context, R.style.Full_Screen_dialog);
        this.context = context;
        this.post = post;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_list_layout);
        listView = (ListView) findViewById(R.id.share_dialog_list_view);
        shareListAdapter = new ShareListAdapter(getShareApps(context));
        listView.setAdapter(shareListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (post == null || user == null) return;
                String nickname = user.getNickname();
                String content = post.getTag();
                String address = post.getAddress();
                String time = CommonUtil.formatTimeMillis(Long.valueOf(post.getTime()));
                String userTitle = "用户";
                if (user.getUsername().equals(LoginStatus.getUser().getUsername())) {
                    userTitle = "我";
                }
                Intent shareIntent = new Intent();
                ResolveInfo resolveInfo = (ResolveInfo) shareListAdapter.getItem(position);
                shareIntent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "来自爱记APP : " + "\n"
                        + userTitle + " " + nickname + " 在 " + address + " 的爱记" + "\n"
                        + "\n"
                        + content + "\n"
                        + "\n"
                        + time + "\n"
                        + "\n"
                        + "APP下载地址 https://github.com/chenjunqian/here/blob/master/app-release.apk");
                shareIntent.setType("text/plain");
                context.startActivity(shareIntent);
                ShareDialog.this.dismiss();
            }
        });
    }

    private List<ResolveInfo> getShareApps(final Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        Collections.sort(resInfo, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                return lhs.loadLabel(context.getPackageManager()).toString().
                        compareTo(rhs.loadLabel(context.getPackageManager()).toString());
            }
        });

        return resInfo;
    }

    class ShareListAdapter extends BaseAdapter {

        private List<ResolveInfo> resInfos;

        public ShareListAdapter(List<ResolveInfo> resInfo) {
            this.resInfos = resInfo;
        }

        @Override
        public int getCount() {
            return resInfos==null?0:resInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return resInfos==null?0:resInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            ResolveInfo resInfo = resInfos.get(position);
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_share_list_item,null);
                viewHolder = new ViewHolder();
                viewHolder.appIconView = (ImageView) convertView.findViewById(R.id.dialog_share_list_item_image_view);
                viewHolder.appNameView = (TextView) convertView.findViewById(R.id.dialog_share_list_item_text_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.setData(resInfo);

            return convertView;
        }
    }

    private class ViewHolder{
        public ImageView appIconView;
        public TextView appNameView;

        public void setData(ResolveInfo resInfo){
            appNameView.setText(resInfo.loadLabel(context.getPackageManager()).toString());
            appIconView.setImageDrawable(resInfo.loadIcon(context.getPackageManager()));
        }
    }
}
