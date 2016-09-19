package com.eason.marker.util.ImageScan;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.eason.marker.controller.BaseActivity;
import com.eason.marker.R;
import com.eason.marker.util.ImageProcessParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImageScanMainActivity extends BaseActivity {
	private static final String TAG = "ImageScanMainActivity";
	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	private List<ImageBean> list = new ArrayList<ImageBean>();
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GroupAdapter adapter;
	private GridView mGroupGridView;
	private final static int IMAGE_SCAN = 0x01;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				//关闭进度条
				mProgressDialog.dismiss();
				
				adapter = new GroupAdapter(ImageScanMainActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
				if (list==null) {
					Toast.makeText(ImageScanMainActivity.this, "本地相册为空，请返回使用其他选项", Toast.LENGTH_LONG).show();
					return;
				}
				mGroupGridView.setAdapter(adapter);
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_scan_main);
		mGroupGridView = (GridView) findViewById(R.id.main_grid);
		getImages();
		
		mGroupGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<String> childList = mGruopMap.get(list.get(position).getFolderName());
				
				Intent mIntent = new Intent(ImageScanMainActivity.this, ShowImageActivity.class);
				mIntent.putStringArrayListExtra("data", (ArrayList<String>)childList);
				
				startActivityForResult(mIntent,IMAGE_SCAN);
			}
		});
		
	}


	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
	 */
	private void getImages() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = ImageScanMainActivity.this.getContentResolver();

				//只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaColumns.MIME_TYPE + "=? or "
								+ MediaColumns.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" }, MediaColumns.DATE_MODIFIED);
				
				while (mCursor.moveToNext()) {
					//获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaColumns.DATA));
					
					//获取该图片的父路径名
					String parentName = new File(path).getParentFile().getName();

					
					//根据父路径名将图片放入到mGruopMap中
					if (!mGruopMap.containsKey(parentName)) {
						List<String> chileList = new ArrayList<String>();
						chileList.add(path);
						mGruopMap.put(parentName, chileList);
					} else {
						mGruopMap.get(parentName).add(path);
					}
				}
				
				mCursor.close();
				
				//通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);
				
			}
		}).start();
		
	}
	
	
	/**
	 * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
	 * 所以需要遍历HashMap将数据组装成List
	 * 
	 * @param mGruopMap
	 * @return
	 */
	private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
		if(mGruopMap.size() == 0){
			return null;
		}
		List<ImageBean> list = new ArrayList<ImageBean>();
		
		Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			
			mImageBean.setFolderName(key);
			mImageBean.setImageCounts(value.size());
			mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
			
			list.add(mImageBean);
		}
		
		return list;
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode== ImageProcessParams.SHOW_IMAGE_ACTIVITY_BACK){
    		//如果从上一页面finsh()返回则什么都不处理
    	}else if (resultCode!=RESULT_OK) {
    		Log.d("image scan","image select error");
    		finish();
		}else{
    		setResult(resultCode,data);
    		finish();
    	}
/*    	
    	if(requestCode == AddCommentActivity.REQ_CODE_FOLDER){
    		Log.d("image scan", "preparing result for add comment");
            setResult(resultCode, data);
            finish();
    	}
    	
        if (requestCode != AndroidUtilsBridge.REQ_CODE_LOCAL_PHOTO) finish();
        //用户在图像编辑界面按了手机的返回键，此时data为null，data.getStringExtra("result")值也为空,
        //此时返回到该界面继续搜索图片
        if (data == null) return;
        String result = data.getStringExtra(AndroidUtilsBridge.BTN_TYPE_LABEL);
        //用户点击了图像编辑界面中的确定或者取消按钮，则直接返回到主界面
        if (result != null && !result.equals("return")) {
            setResult(resultCode, data);
            finish();
        }*/
    }
    
	@Override
	public void onResume() {
	    super.onResume();
	}
	@Override
	public void onPause() {
	    super.onPause();
	}
	
}