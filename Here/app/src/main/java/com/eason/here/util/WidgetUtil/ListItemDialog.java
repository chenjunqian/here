package com.eason.here.util.WidgetUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.eason.here.R;
import com.eason.here.model.Post;
import com.eason.here.model.User;
import com.eason.here.profile_activity.ProfileActivity;

/**
 * Created by Eason on 12/22/15.
 */
public class ListItemDialog extends Dialog {

    private RelativeLayout likeLayout;
    private RelativeLayout enterProfileLayout;
    private RelativeLayout reportLayout;
    private RelativeLayout backgroundLayout;
    private Context context;
    private Post post;
    private User user;

    public ListItemDialog(Context context,Post post,User user) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.post = post;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_post_list_item_click_layout);
        likeLayout = (RelativeLayout) findViewById(R.id.like_layout);
        enterProfileLayout = (RelativeLayout) findViewById(R.id.enter_profile_layout);
        reportLayout = (RelativeLayout) findViewById(R.id.enter_profile_layout);
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
                intent.putExtra("username",user.getUsername());
                context.startActivity(intent);
            }
        });

        likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
