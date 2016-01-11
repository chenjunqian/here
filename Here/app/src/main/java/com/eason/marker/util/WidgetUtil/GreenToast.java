package com.eason.marker.util.WidgetUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.R;

/**
 * 自定义样式的Toast
 * Created by Eason on 10/24/15.
 */
public class GreenToast {

    private static Context context;
    private static String toast_text;
    private static int gravity=0;
    private static int xOffset;
    private static int yOffset;
    private static int duration;

    private static Toast toast;

    /**
     * 标准的Toast位置
     *
     * @param context
     * @param text
     * @param duration
     * @return
     */
    public static Toast makeText(Context context,String text,int duration) {
        GreenToast.context = context;
        toast_text = text;
        GreenToast.duration = duration;

        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.green_toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.green_toast_text_view);
        textView.setText(toast_text);

        toast = new Toast(context);
        toast.setView(view);
        return toast;
    }

    /**
     * 可设置位置的Toast
     *
     * @param context
     * @param text
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @return
     */
    public static Toast makeText(Context context,String text,int duration,int gravity, int xOffset, int yOffset){
        GreenToast.gravity = gravity;
        GreenToast.xOffset =xOffset;
        GreenToast.yOffset = yOffset;
        return makeText(context, text, duration);
    }

    public void show() {
        if (gravity!= 0){
            toast.setGravity(gravity,xOffset,yOffset);
        }

        toast.setDuration(duration);
        toast.show();
    }

}
