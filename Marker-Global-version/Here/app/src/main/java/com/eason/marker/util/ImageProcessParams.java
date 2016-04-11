package com.eason.marker.util;

public class ImageProcessParams {
	/**
	 *
	 */
	public static final int REQ_CATEGORY_OPTION_SUBMIT = 0x04;

	public static final int IMAGE_SOURCE_CAMERA = 0x01;
	public static final int IMAGE_SOURCE_LOCAL_ALBUM = 0x02;
	public static final int IMAGE_SOURCE_ONLINE = 0x03;
	public static final int IMAGE_EDIT = 0x04;
	public static final int SHOW_IMAGE_ACTIVITY_BACK = 0x05;

	public static final String REQ_SOURCE_EXTRA_NAME = "request_source";// 图片用途:头像、发帖
	public static final String IMAGE_SOURCE_EXTRA_NAME = "image_source";// 照片来源
	public static final String IMAGE_PATH_EXTRA_NAME = "image_path";
	public static final String SEARCH_WORD_EXTRA_NAME = "search_word";

	public static final String BTN_TYPE_LABEL = "btnKind";
	public static final String RESULT_CODE = "resultCode";

	public static final String IMG_ROTATE_DEGREE_LABEL = "imgRotateDegreeLabel";
	public static final int MSG_EDIT = 15;
	public static final int MSG_SUCCESS = 20;

	public static final int RESULT_NOT_OK = 0;
}
