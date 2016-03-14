package com.eason.marker.util.WidgetUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Eason on 2/26/16.
 */
public class RippleLayout extends RelativeLayout {

    private Paint mPaint;
    private int clickedViewWidth;
    private int clickedViewHeight;

    private int mMaxRippleRadius;
    private int mRippleRadiusGap;
    private int mRippleRadius = 0;
    private float mCenterX;
    private float mCenterY;
    private int[] mLocationInScreen = new int[2];
    private boolean shouldDrawRipple = false;
    private boolean isPressed = false;
    private int INVALIDATE_DURATION = 50;
    private View clickedView;

    private boolean isClickedParent = false;

    public RippleLayout(Context context) {
        super(context);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);
        mPaint.setColor(Color.BLACK);
    }

    public void setRippleColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            View clickedView = getTouchTarget(this, x, y);
            if (clickedView != null && clickedView.isClickable() && clickedView.isEnabled()) {
                this.clickedView = clickedView;
                isClickedParent = false;
                initParamForRipple(event, clickedView,false);
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                this.clickedView = this;
                isClickedParent = true;
                RippleLayout.this.setClickable(true);
                initParamForRipple(event, clickedView,true);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }

        } else if (action == MotionEvent.ACTION_UP) {
            isPressed = false;
            RippleLayout.this.setClickable(false);
            postInvalidateDelayed(INVALIDATE_DURATION);
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            isPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!shouldDrawRipple) {
            return;
        }

        if (mRippleRadius > mMaxRippleRadius / 2) {
            mRippleRadius += mRippleRadiusGap * 4;
        } else {
            mRippleRadius += mRippleRadiusGap;
        }


        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        clickedView.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + clickedView.getMeasuredWidth();
        int bottom = top + clickedView.getMeasuredHeight();

        canvas.save();
        if (!isClickedParent) {
            canvas.clipRect(left, top, right, bottom);
        }
        canvas.drawCircle(mCenterX, mCenterY, mRippleRadius, mPaint);
        canvas.restore();

        if (mRippleRadius <= mMaxRippleRadius) {
            if (isClickedParent) {
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            }

        } else if (!isPressed) {
            shouldDrawRipple = false;
            if (isClickedParent) {
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            }
        }
    }


    private void initParamForRipple(MotionEvent event, View view, boolean isClickedParent) {
        mCenterX = event.getX();
        mCenterY = event.getY();
        if (isClickedParent) {
            clickedViewWidth = this.getMeasuredWidth();
            clickedViewHeight = this.getMeasuredHeight();
        } else {
            clickedViewWidth = view.getMeasuredWidth();
            clickedViewHeight = view.getMeasuredHeight();
        }
        mMaxRippleRadius = (int) Math.sqrt((double) (clickedViewWidth * clickedViewWidth + clickedViewHeight * clickedViewHeight));
        mRippleRadius = 0;
        shouldDrawRipple = true;
        isPressed = true;
        mRippleRadiusGap = mMaxRippleRadius / 20;
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y) && child != RippleLayout.this) {
                target = child;
                break;
            }
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }
        return false;
    }

}
