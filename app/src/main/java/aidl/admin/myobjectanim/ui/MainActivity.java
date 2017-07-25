package aidl.admin.myobjectanim.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import aidl.admin.myobjectanim.R;
import aidl.admin.myobjectanim.anim.ColorEvaluate;
import aidl.admin.myobjectanim.view.MyAnimView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private TextView tvHzj;
    private MyAnimView myAnimView;

    private ValueAnimator valueAnimator;
    private ObjectAnimator objectAnimator;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initObjectAnim();
//        initValueAnim();
        initAnimSet();
//        initXmlAnim();
//        initViewPropertyAnim();
    }

    private void initView() {
        tvHzj = (TextView) findViewById(R.id.tv_hzj);
        tvHzj.setAlpha(0f);
    }

    private void initValueAnim() {
        valueAnimator = ValueAnimator.ofInt(0, 100, 0);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tvHzj.setText(value + "");
                Log.i(TAG, "value="+value);
            }
        });
        valueAnimator.setStartDelay(2000);
        valueAnimator.setRepeatCount(2);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initObjectAnim() {
        objectAnimator = ObjectAnimator.ofFloat(tvHzj, "alpha", 1f, 0f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initAnimSet() {
        float currentTransY = tvHzj.getTranslationY();
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(tvHzj, "translationY", -500, currentTransY);
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(tvHzj, "rotation", 0f, 360f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(tvHzj, "scaleX", 1f, 3f, 1f);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(tvHzj, "alpha", 0f, 1f);
        animatorSet = new AnimatorSet();
        animatorSet.play(transYAnim).with(scaleXAnim).with(alphaAnim).before(rotationAnim);
        animatorSet.setStartDelay(2000);
        animatorSet.setDuration(6000);
        animatorSet.start();
    }

    private void initXmlAnim() {
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.anim_set);
        anim.setTarget(tvHzj);
        anim.start();
    }

    private void initViewPropertyAnim() {
        tvHzj.animate().y(500).scaleX(3).scaleX(1).alpha(1).setDuration(3000);
        tvHzj.animate().rotation(360).setStartDelay(3000).setDuration(2000);
    }

}
