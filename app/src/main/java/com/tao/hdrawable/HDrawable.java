package com.tao.hdrawable;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by wt on 2018/7/2.
 */
public class HDrawable extends Drawable {
    Rect mRect = new Rect();
    Paint mPaint;
    Path mPath;
    BitmapShader mShader;
    Bitmap mBitmap;

    public HDrawable() {
        this(null);
    }

    public HDrawable(Bitmap bitmap) {
        init();
        setBitmap(bitmap);
    }

    private void init() {
        initPaint();
        initPath();
    }

    private void ensurePaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    private void ensurePath() {
        if (mPath == null) {
            mPath = new Path();
        }
    }

    private void initPaint() {
        ensurePaint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    // 设置Bitmap的时候初始化shader，并设置给paint
    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (bitmap == null) {
            mShader = null;
        } else {
            mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    // 初始化好Path要走的路径
    private void initPath() {
        ensurePath();
        double d15 = Math.toRadians(15);
        double d45 = Math.toRadians(45);
        float l = (float) (mRect.width() / Math.cos(d15));
        l = l / 2;
        mPath.reset();
        mPath.moveTo((float) (Math.cos(d45) * l), 0);
        mPath.lineTo(0, (float) (Math.cos(d45) * l));
        mPath.lineTo((float) (Math.sin(d15) * l), (float) ((Math.cos(d45) + Math.cos(d15)) * l));
        mPath.lineTo((float) (Math.sin(d15) + Math.cos(d15)) * l, (float) ((Math.cos(d45) + Math.cos(d15) + Math.sin(d15)) * l));
        mPath.lineTo((float) (Math.cos(d45) + Math.cos(d15) + Math.sin(d15)) * l, (float) ((Math.sin(d15) + Math.cos(d15)) * l));
        mPath.lineTo((float) (Math.cos(d15) + Math.sin(d45)) * l, (float) (Math.sin(d15) * l));
        mPath.lineTo((float) (Math.cos(d45) * l), 0);
        mPath.close();

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint != null) {
            mPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mPaint != null) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity() {
        return 0;
    }

    // 设置边界信息
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect.set(left, top, right, bottom);
        initPath();
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return mBitmap.getWidth();
        } else {
            return super.getIntrinsicWidth();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return mBitmap.getHeight();
        }
        return super.getIntrinsicHeight();
    }
}
