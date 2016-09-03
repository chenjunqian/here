package com.eason.marker.about_us_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eason.marker.BaseActivity;
import com.eason.marker.R;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.WidgetUtil.GreenToast;
import com.eason.marker.util.WidgetUtil.ModelDialog;

/**
 * Created by Eason on 12/23/15.
 */
public class AboutUsActivity extends BaseActivity{

    private Button shareAppBtn;
    private Button reportBtn;
    private String contentBackup = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_layout);
        init();
    }

    private void init(){
        shareAppBtn = (Button) findViewById(R.id.about_us_share_app_btn);
        reportBtn = (Button) findViewById(R.id.about_us_report_us_btn);

        shareAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportIssue();
            }
        });
    }

    private void shareApp(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.about_us_activity_share_content));
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "分享到..."));
    }


    private void reportIssue(){
        final ModelDialog reportDialog = new ModelDialog(AboutUsActivity.this,
                R.layout.dialog_report_issue_layout,R.style.Theme_dialog);

        Button okBtn = (Button) reportDialog.findViewById(R.id.report_dialog_ok);
        Button cancelBtn = (Button) reportDialog.findViewById(R.id.report_dialog_cancel);
        final EditText contentEditText = (EditText) reportDialog.findViewById(R.id.report_dialog_edit_text);

        if (!CommonUtil.isEmptyString(contentBackup)){
            contentEditText.setText(contentBackup);
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpResponseHandler reportIssueHandler = new HttpResponseHandler(){
                    @Override
                    public void getResult() {
                        if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT){
                            GreenToast.makeText(AboutUsActivity.this,
                                    getResources().getString(R.string.report_us_success_toast), Toast.LENGTH_SHORT).show();
                            contentBackup = "";
                        }else{
                            GreenToast.makeText(AboutUsActivity.this,
                                    getResources().getString(R.string.net_work_invalid), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                if (CommonUtil.isEmptyString(contentEditText.getText().toString())){
                    GreenToast.makeText(AboutUsActivity.this,
                            getResources().getString(R.string.about_us_activity_report_content_can_not_be_empty), Toast.LENGTH_SHORT).show();
                }else{
                    if (LoginStatus.getIsUserMode()){
                        HttpRequest.reportIssue(contentEditText.getText().toString(), LoginStatus.getUser().getUsername(),reportIssueHandler);
                    }else{
                        HttpRequest.reportIssue(contentEditText.getText().toString(), "tourist",reportIssueHandler);
                    }

                }

                reportDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentEditText.getText()==null){
                    contentBackup = "";
                }else{
                    contentBackup = contentEditText.getText().toString();
                }
                reportDialog.dismiss();
            }
        });

        reportDialog.show();
    }
}
