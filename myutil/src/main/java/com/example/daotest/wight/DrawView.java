package com.example.daotest.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author tian on 2019/9/3
 * 自定义控件,动态绘制轨迹球
 */
public class DrawView extends View {

    private float currentX = 40f;
    private float currentY = 50f;
    //定义,并创建画笔
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画笔颜色
        paint.setColor(Color.RED);
        //绘制一个小球
        canvas.drawCircle(currentX,currentY,15F,paint);
    }
    /**
     * 为该组件的触碰事件重写事件处理方法
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //修改currentX, currentY 两个成员变量
        currentX = event.getX();
        currentY = event.getY();

        //通知当前组件重绘自己
        invalidate();

        //返回true 表示该处理方法已经处理该事件



        return true;


    }
}
