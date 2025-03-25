package com.lil4869.verticalseekbar;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;


public class VerticalSeekBar extends AppCompatSeekBar {
    private VerticalProgressChangedListener verticalProgressChangedListener;
    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    public VerticalProgressChangedListener getVerticalProgressChangedListener() {
        return verticalProgressChangedListener;
    }

    public void setVerticalProgressChangedListener(VerticalProgressChangedListener verticalProgressChangedListener) {
        this.verticalProgressChangedListener = verticalProgressChangedListener;
    }

    protected void onDraw(Canvas c) {
        //将SeekBar转转90度
        c.rotate(-90);
        //将旋转后的视图移动回来
        c.translate(-getHeight(),0);
//        Log.i("getHeight()",getHeight()+"");
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i=0;
                //获取滑动的距离
                i=getMax() - (int) (getMax() * event.getY() / getHeight());
                i=Math.min(getMax(),Math.max(i,getMin()));
                //设置进度
                setProgress(i);
                if(event.getAction()==MotionEvent.ACTION_UP)
                {if(verticalProgressChangedListener!=null)verticalProgressChangedListener.onVerticalProgressChange(true,i);}
                else if(event.getAction()==MotionEvent.ACTION_MOVE)  if(verticalProgressChangedListener!=null)verticalProgressChangedListener.onVerticalMove();
                //每次拖动SeekBar都会调用
                onSizeChanged(getWidth(), getHeight(), 0, 0);

                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(),getHeight(),0,0);
        if(verticalProgressChangedListener!=null)verticalProgressChangedListener.onVerticalProgressChange(false,progress);
    }

    public synchronized void setFromUserProgress(int progress,boolean fromUser) {
        super.setProgress(progress);
        onSizeChanged(getWidth(),getHeight(),0,0);
        if(verticalProgressChangedListener!=null)verticalProgressChangedListener.onVerticalProgressChange(fromUser,progress);
    }
    public interface VerticalProgressChangedListener{
        void onVerticalProgressChange(boolean fromUser,int progress);
        default void onVerticalMove(){};
    }

}