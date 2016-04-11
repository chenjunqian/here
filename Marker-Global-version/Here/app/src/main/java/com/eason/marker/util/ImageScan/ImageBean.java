package com.eason.marker.util.ImageScan;

/**
 * GridView��ÿ��item�����ݶ���
 * 
 * @author len
 *
 */
public class ImageBean{
	/**
	 * �ļ��еĵ�һ��ͼƬ·��
	 */
	private String topImagePath;
	/**
	 * �ļ�����
	 */
	private String folderName; 
	/**
	 * �ļ����е�ͼƬ��
	 */
	private int imageCounts;
	
	public String getTopImagePath() {
		return topImagePath;
	}
	public void setTopImagePath(String topImagePath) {
		this.topImagePath = topImagePath;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getImageCounts() {
		return imageCounts;
	}
	public void setImageCounts(int imageCounts) {
		this.imageCounts = imageCounts;
	}
	
}
