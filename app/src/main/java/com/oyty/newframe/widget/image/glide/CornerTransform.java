package com.oyty.newframe.widget.image.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Datetime: 2018/7/31 9:37
 * Author: zcj
 */
public class CornerTransform extends BitmapTransformation {

    private static final String ID = "com.flash.coupon.image.glide.CornerTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int roundingRadius;

    private boolean exceptLeftTop, exceptRightTop, exceptLeftBottom, exceptRightBottom;

    public CornerTransform(int roundingRadius) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");
        this.roundingRadius = roundingRadius;
    }

    /**
     * 哪些角不需要圆角化
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    public CornerTransform setExceptCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        this.exceptLeftTop = leftTop;
        this.exceptRightTop = rightTop;
        this.exceptLeftBottom = leftBottom;
        this.exceptRightBottom = rightBottom;
        return this;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap outBitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        //关联画笔绘制的原图bitmap
        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //计算中心位置,进行偏移
        int width = (toTransform.getWidth() - outWidth) / 2;
        int height = (toTransform.getHeight() - outHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float) (-width), (float) (-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0F, 0.0F, (float) canvas.getWidth(), (float) canvas.getHeight());
        canvas.drawRoundRect(rectF, this.roundingRadius, this.roundingRadius, paint); //先绘制圆角矩形

        if (exceptLeftTop) { //左上角不为圆角
            canvas.drawRect(0, 0, roundingRadius, roundingRadius, paint);
        }
        if (exceptRightTop) {//右上角不为圆角
            canvas.drawRect(canvas.getWidth() - roundingRadius, 0, canvas.getWidth() - roundingRadius, roundingRadius, paint);
        }

        if (exceptLeftBottom) {//左下角不为圆角
            canvas.drawRect(0, canvas.getHeight() - roundingRadius, roundingRadius, canvas.getHeight(), paint);
        }

        if (exceptRightBottom) {//右下角不为圆角
            canvas.drawRect(canvas.getWidth() - roundingRadius, canvas.getHeight() - roundingRadius, canvas.getWidth(), canvas.getHeight(), paint);
        }

        return outBitmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CornerTransform that = (CornerTransform) o;
        return roundingRadius == that.roundingRadius &&
                exceptLeftTop == that.exceptLeftTop &&
                exceptRightTop == that.exceptRightTop &&
                exceptLeftBottom == that.exceptLeftBottom &&
                exceptRightBottom == that.exceptRightBottom;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(roundingRadius,
                Util.hashCode(exceptLeftTop,
                        Util.hashCode(exceptRightTop,
                                Util.hashCode(exceptLeftBottom,
                                        Util.hashCode(exceptRightBottom)))));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(5).putInt(roundingRadius)
                .put(getBooleanValue()).array();
        messageDigest.update(radiusData);
    }

    private byte getBooleanValue() {
        byte value[] = new byte[4];
        if (exceptLeftTop) {
            value[0] = 1;
        }
        if (exceptRightTop) {
            value[1] = 2;
        }
        if (exceptLeftBottom) {
            value[2] = 4;
        }
        if (exceptRightBottom) {
            value[3] = 8;
        }
        return (byte) (value[0] | value[1] | value[2] | value[3]);
    }
}
