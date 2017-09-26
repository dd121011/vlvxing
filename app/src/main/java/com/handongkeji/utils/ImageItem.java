package com.handongkeji.utils;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;


public class ImageItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	public boolean isSelected = false;
	private String netUrl;
	private float orientation;
	
	public String getNetUrl() {
		return netUrl;
	}
	public void setNetUrl(String netUrl) {
		this.netUrl = netUrl;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Bitmap getBitmap() {
		if(bitmap == null){
			try {
				bitmap = Bimp.revitionImageSize(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o ) {
			return true;
		}
		
		if (!(o instanceof ImageItem)) {
			return false;
		}
		
		
		ImageItem item = (ImageItem) o;
		
		
		return this == null ? item == null:((this.imageId == null ? item.imageId == null : this.imageId.equals(item.imageId))
				&& (this.imagePath == null ? item.imagePath == null : this.imagePath.equals(item.imagePath))&& (this.thumbnailPath == null ? item.thumbnailPath == null : this.thumbnailPath.equals(item.thumbnailPath)));
		
	}
	
	@Override
	public int hashCode() {
		return (imageId == null ? 0 : imageId.hashCode())^((imagePath == null ?0 :imagePath.hashCode())*31)^((thumbnailPath == null ? 0:thumbnailPath.hashCode())*31);
	}
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
}
