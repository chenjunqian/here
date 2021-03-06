package com.eason.marker.util.WidgetUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class ModelDialog extends Dialog {
	public ModelDialog(Context context, int layout, int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// float density = getDensity(context);
		// params.width = (int) (default_width * density);
		// params.height = (int) (default_height * density);
		params.width =  ((Activity)context).getWindowManager()
				.getDefaultDisplay().getWidth() * 3 / 4;
		; // 默认宽度
			// params.height = default_height;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}

	public ModelDialog(Context context, int layout, int style,
			Boolean isMatchWidth) {
		super(context, style);
		int default_width =  ((Activity)context).getWindowManager()
				.getDefaultDisplay().getWidth() * 5 / 6;
		if (!isMatchWidth) {
			default_width = default_width * 9 / 10;
		}
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = default_width;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}

	public ModelDialog(Context context, Boolean isFullWith, int layout,
			int style) {
		super(context, style);
		int default_width =  ((Activity)context).getWindowManager()
				.getDefaultDisplay().getWidth();
		if (!isFullWith) {
			default_width = default_width * 9 / 10;
		}
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = default_width;
		params.gravity = Gravity.BOTTOM;
		window.setAttributes(params);
	}

}