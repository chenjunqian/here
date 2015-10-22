package com.eason.here.util.ImageScan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.eason.here.BaseActivity;
import com.eason.here.R;
import com.eason.here.util.ImageProcessParams;

import java.util.List;

public class ShowImageActivity extends BaseActivity {
	private GridView mGridView;
	private List<String> list;
	private ChildAdapter adapter;
	
	public static final int INTENT_FROM_POST = 0x01;
	public static final int INTENT_FROM_COMMENT = 0x02;
	public static final int INTENT_FROM_EIDT_AVATAR = 0x03;
	
	public static String TAG = "ShowImageActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_scan_show_image_activity);
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");
		
		adapter = new ChildAdapter(this, list, mGridView);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(gridViewListener);
		
	}

	
	//返回键监听
	public Button.OnClickListener backBtnListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mGridView.setVisibility(View.VISIBLE);
		}
	};
	
	//图片点击事件监听
	public OnItemClickListener gridViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final String path = (String) adapter.getItem(position);
			
			Intent in = new Intent();
			in.putExtra(ImageProcessParams.IMAGE_PATH_EXTRA_NAME, path);
			setResult(RESULT_OK,in);
			finish();
		}
	};
	
	@Override
	public void onBackPressed() {
		setResult(ImageProcessParams.SHOW_IMAGE_ACTIVITY_BACK);
		finish();
	};
	
}

