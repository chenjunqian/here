package com.eason.marker.util.WidgetUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.eason.marker.R;
import com.eason.marker.util.CommonUtil;

/**
 * Created by Eason on 9/20/15.
 */
public class CircleImageView extends ImageView{

    private Context context;

    public CircleImageView(Context context){
        super(context);
        this.context = context;
    }

    public CircleImageView(Context context ,AttributeSet attrs){
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs,defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap tempBitmap = ((BitmapDrawable) drawable).getBitmap();
        if (tempBitmap == null) {
            tempBitmap = CommonUtil.drawableToBitmap(getResources().getDrawable(
                    R.drawable.default_avatar));
        }

        int w = getWidth();
        int h = getHeight();

        Bitmap roundBitmap = getCroppedBitmap(tempBitmap, w > h ? h : w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    /**
     * 获取圆形的Bitmap
     *
     * @param bmp
     * @param radius
     * @return
     */
    private static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap targetBitmap;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            // 设置成可以缩放的Bitmap
            targetBitmap = Bitmap
                    .createScaledBitmap(bmp, radius, radius, false);
        } else {
            targetBitmap = bmp;
        }

        Bitmap output = Bitmap.createBitmap(targetBitmap.getWidth(),
                targetBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        // 设置画布
        final Rect rect = new Rect(0, 0, targetBitmap.getWidth(),
                targetBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(targetBitmap.getWidth() / 2 + 0.7f,
                targetBitmap.getHeight() / 2 + 0.7f,
                targetBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(targetBitmap, rect, rect, paint);

        return output;
    }
}
