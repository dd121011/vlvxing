package com.vlvxing.app.album.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;

public class OverlayBitmapUtils {
	
	
		public static Bitmap createOverlayBitmap(Context context , Bitmap bitmap, String letter, int offset, int left, String color) {
			
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(imgTemp);
			Paint paint = new Paint();
			paint.setDither(true);
			paint.setFilterBitmap(true);
			Rect src = new Rect(0, 0, width, height);
			Rect dst = new Rect(0, 0, width, height);
			canvas.drawBitmap(bitmap, src, dst, paint);
	
			Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
					| Paint.DEV_KERN_TEXT_FLAG);
			float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics());
			textPaint.setTextSize(textSize);
			textPaint.setTypeface(Typeface.DEFAULT_BOLD);
			textPaint.setColor(Color.parseColor(color));
			Rect bounds = new Rect();
			textPaint.getTextBounds(letter,0,letter.length(),bounds);
	
			canvas.drawText(letter, (width - bounds.width()) /2, (height + bounds.height())/2-10+ offset,textPaint);
			
			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
					
			return 	(new BitmapDrawable(context.getResources(), imgTemp)).getBitmap();
		}
	

}
