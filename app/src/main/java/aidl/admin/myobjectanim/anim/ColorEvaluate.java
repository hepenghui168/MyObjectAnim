package aidl.admin.myobjectanim.anim;

import android.animation.TypeEvaluator;

/**
 * Created by admin on 2017/7/25.
 */

public class ColorEvaluate implements TypeEvaluator {

    private int mCurrentRed = -1;

    private int mCurrentGreen = -1;

    private int mCurrentBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        int startRed = Integer.valueOf(startColor.substring(1, 3), 16);
        int startGreen = Integer.valueOf(startColor.substring(3, 5), 16);
        int startBlue = Integer.valueOf(startColor.substring(5, 7), 16);
        int endRed = Integer.valueOf(endColor.substring(1, 3), 16);
        int endGreen = Integer.valueOf(endColor.substring(3, 5), 16);
        int endBlue = Integer.valueOf(endColor.substring(5, 7), 16);

        if (mCurrentRed == -1)
            mCurrentRed = startRed;
        if (mCurrentGreen == -1)
            mCurrentGreen = startGreen;
        if (mCurrentBlue == -1)
            mCurrentBlue = startBlue;

        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;

        if (mCurrentRed != endRed)
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,fraction);
        else if (mCurrentGreen != endGreen)
            mCurrentGreen = getCurrentColor(startBlue, endGreen, colorDiff, redDiff, fraction);
        else if (mCurrentBlue != endBlue)
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff, fraction);
        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);

        return currentColor;
    }

    /*
    * 根据fraction值来计算当前的颜色
    * */
    private int getCurrentColor(int startColor, int endColor, int colorDiff, int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor)
                currentColor = endColor;
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor)
                currentColor = endColor;
        }
        return currentColor;
    }

    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
