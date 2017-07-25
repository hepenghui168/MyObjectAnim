package aidl.admin.myobjectanim.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ViewAnimator;

import aidl.admin.myobjectanim.anim.ColorEvaluate;
import aidl.admin.myobjectanim.anim.DecelerateAccelerateInterpolator;
import aidl.admin.myobjectanim.anim.PointEvaluator;
import aidl.admin.myobjectanim.model.Point;

/**
 * Created by admin on 2017/7/25.
 */

public class MyAnimView extends View {
    public static final float RADIUS = 50f;

    private Point currentPoint;

    private Paint paint;

    private String color;

    public MyAnimView(Context context) {
        super(context);
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#8B49F6"));
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(String color) {
        this.color = color;
        paint.setColor(Color.parseColor(color));
        invalidate();
    }

    public String getColor() {
        return color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
//            startAnimator();
            startAnimFreeFall();
        } else {
            drawCircle(canvas);
        }

    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, paint);
    }

    private void startAnimator() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentPoint.setX(RADIUS);
                currentPoint.setY(RADIUS);
                paint.setColor(Color.parseColor("#8B49F6"));
                invalidate();
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "color", new ColorEvaluate(),
                "#0000FF", "#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.setDuration(6000);
        animatorSet.start();
    }

    private void startAnimFreeFall() {
        Point startPoint = new Point(getWidth()/2, RADIUS);
        Point endPoint = new Point(getWidth()/2, getHeight() - RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new DecelerateAccelerateInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }
}
