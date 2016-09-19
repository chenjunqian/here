package com.eason.marker.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.R;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.User;
import com.eason.marker.controller.profile_activity.ProfileActivity;
import com.eason.marker.util.CommonUtil;

/**
 * Created by Eason on 12/22/15.
 */
public class ListItemDialog extends Dialog implements View.OnClickListener {

    private RelativeLayout shareLayout;
    private RelativeLayout enterProfileLayout;
    private RelativeLayout reportLayout;
    private RelativeLayout backgroundLayout;
    private Context context;
    private Post post;
    private User user;
    private ModelDialog modelDialog;
    private Button okBtn, cancelBtn;
    private TextView otherTextView, rubbishTextView, illegalTextView, notForPublicationTextView;
    private EditText otherEditView;
    private LinearLayout rubbishPostLayout, illegalPostLayout, notForPublicationPostLayout, otherPostLayout;
    private String reportContent;
    private View shareView;

    public ListItemDialog(Context context, Post post, User user , View shareView) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.post = post;
        this.user = user;
        this.shareView = shareView;
    }

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_post_list_item_click_layout);
        shareLayout = (RelativeLayout) findViewById(R.id.share_layout);
        enterProfileLayout = (RelativeLayout) findViewById(R.id.enter_profile_layout);
        reportLayout = (RelativeLayout) findViewById(R.id.report_layout);
        backgroundLayout = (RelativeLayout) findViewById(R.id.dialog_post_list_item_background_layout);

        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemDialog.this.dismiss();
            }
        });

        enterProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("username", user.getUsername());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,shareView,"avatar");
                context.startActivity(intent,options.toBundle());
                ListItemDialog.this.dismiss();
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareDialog(context, post, user).show();
                sharePost(post,user);
                ListItemDialog.this.dismiss();
            }
        });

        reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportPost();
                ListItemDialog.this.dismiss();
            }
        });
    }

    private void sharePost(Post post, User user) {
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
        context.startActivity(Intent.createChooser(shareIntent, "分享到..."));
    }

    /**
     * 举报帖子
     */
    private void reportPost() {
        modelDialog = new ModelDialog(context, R.layout.dialog_report_post_layout, R.style.Theme_dialog);

        okBtn = (Button) modelDialog.findViewById(R.id.report_post_ok_button);
        okBtn.setOnClickListener(this);
        cancelBtn = (Button) modelDialog.findViewById(R.id.report_post_cancel_button);
        cancelBtn.setOnClickListener(this);
        otherEditView = (EditText) modelDialog.findViewById(R.id.other_invalid_post_edit_text);
        otherTextView = (TextView) modelDialog.findViewById(R.id.other_invalid_post_text_view);
        rubbishTextView = (TextView) modelDialog.findViewById(R.id.rubbish_post_text_view);
        illegalTextView = (TextView) modelDialog.findViewById(R.id.illegal_post_text_view);
        notForPublicationTextView = (TextView) modelDialog.findViewById(R.id.not_for_publication_post_text_view);
        rubbishPostLayout = (LinearLayout) modelDialog.findViewById(R.id.rubbish_post_layout);
        rubbishPostLayout.setOnClickListener(this);
        illegalPostLayout = (LinearLayout) modelDialog.findViewById(R.id.illegal_post_layout);
        illegalPostLayout.setOnClickListener(this);
        notForPublicationPostLayout = (LinearLayout) modelDialog.findViewById(R.id.not_for_publication_post_layout);
        notForPublicationPostLayout.setOnClickListener(this);
        otherPostLayout = (LinearLayout) modelDialog.findViewById(R.id.other_invalid_post_layout);
        otherPostLayout.setOnClickListener(this);
        modelDialog.show();
    }

    private void setTextViewColor(int viewId) {
        rubbishTextView.setTextColor(context.getResources().getColor(R.color.universal_item_text_color_black));
        illegalTextView.setTextColor(context.getResources().getColor(R.color.universal_item_text_color_black));
        notForPublicationTextView.setTextColor(context.getResources().getColor(R.color.universal_item_text_color_black));
        otherEditView.setVisibility(View.GONE);
        otherTextView.setVisibility(View.VISIBLE);

        switch (viewId) {
            case R.id.rubbish_post_layout:
                rubbishTextView.setTextColor(context.getResources().getColor(R.color.universal_title_background_red));
                reportContent = rubbishTextView.getText().toString();
                break;
            case R.id.illegal_post_layout:
                illegalTextView.setTextColor(context.getResources().getColor(R.color.universal_title_background_red));
                reportContent = illegalTextView.getText().toString();
                break;
            case R.id.not_for_publication_post_layout:
                notForPublicationTextView.setTextColor(context.getResources().getColor(R.color.universal_title_background_red));
                reportContent = notForPublicationTextView.getText().toString();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_post_ok_button:
                HttpResponseHandler reportPostHandler = new HttpResponseHandler() {
                    @Override
                    public void getResult() {
                        if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                            GreenToast.makeText(context,
                                    context.getResources().getString(R.string.current_list_page_report_report_success), Toast.LENGTH_SHORT).show();
                        } else {
                            GreenToast.makeText(context,
                                    context.getResources().getString(R.string.net_work_invalid), Toast.LENGTH_SHORT).show();
                        }

                        modelDialog.dismiss();
                    }
                };

                if (!CommonUtil.isEmptyString(reportContent)) {
                    HttpRequest.reportPost(reportContent, LoginStatus.getUser().getUsername(), post.getPostId(), reportPostHandler);
                } else {
                    GreenToast.makeText(context, context.getResources().getString(R.string.current_list_page_report_report_content_empty), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.report_post_cancel_button:
                modelDialog.dismiss();
                break;
            case R.id.rubbish_post_layout:
            case R.id.illegal_post_layout:
            case R.id.not_for_publication_post_layout:
                setTextViewColor(v.getId());
                break;
            case R.id.other_invalid_post_layout:
                otherEditView.setVisibility(View.VISIBLE);
                otherTextView.setVisibility(View.GONE);
                break;
        }
    }

}
