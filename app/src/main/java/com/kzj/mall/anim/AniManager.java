package com.kzj.mall.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

public class AniManager {
    private AnimListener mListener;
    private ViewGroup mAnimMaskLayout;// 动画层
    private int time = 500;
    private Context mContext;

    public AniManager(Context context) {
        this.mContext = context;
    }

    public void setOnAnimListener(AnimListener listener) {
        mListener = listener;
    }


    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        int id = Integer.MAX_VALUE;
        animLayout.setId(id);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                SizeUtils.dp2px(10),
                SizeUtils.dp2px(10));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 套餐组合加入购物车执行此动画
     *
     * @param v
     * @param startLocation
     * @param endLocation
     */
    public void startGroupCartAnim(final View v, int[] startLocation, int[] endLocation) {
        mAnimMaskLayout = createAnimLayout();
        mAnimMaskLayout.addView(v);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(v, startLocation);

        //终点位置
        int endX = endLocation[0] - startLocation[0] + 20;
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(time);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
                mListener.setAnimBegin(AniManager.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束调用的方法
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                mListener.setAnimEnd(AniManager.this);
            }
        });
    }


    /**
     * 商品详情商品加入购物车执行此动画
     *
     * @param v
     */
    public void startCartAnim(final View v, final View startView, final int[] startLocation, final int[] endLocation) {
        mAnimMaskLayout = createAnimLayout();
        mAnimMaskLayout.addView(v);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(v, startLocation);
        startCartAnim1(view, startView, startLocation, endLocation);
    }

    private void startCartAnim1(final View view, final View startView, final int[] startLocation, final int[] endLocation) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", startView.getWidth()/2,startView.getWidth()/5);
        animator1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationY", 0, -100);
        animator2.setInterpolator(new LinearInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(animator1).with(animator2);
        set.setDuration(time / 2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                mListener.setAnimBegin(AniManager.this);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startCartAnim2(view, startLocation, endLocation);
            }
        });
        set.start();
    }

    private void startCartAnim2(final View v, int[] start_location, int[] end_location) {
        //终点位置
        int endX = end_location[0] - start_location[0] + 20;
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(v, "translationX", 50, endX);
        animator3.setInterpolator(new LinearInterpolator());
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(v, "translationY", -100, endY);
        animator4.setInterpolator(new AccelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(animator3).with(animator4);
        set.setDuration(time / 2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.GONE);
                mListener.setAnimBegin(AniManager.this);
            }
        });
        set.start();
    }


    //自定义时间接口
    public long setTime(int l) {
        time = l;
        return time;
    }

    //回调监听
    public interface AnimListener {

        void setAnimBegin(AniManager a);

        void setAnimEnd(AniManager a);

    }
}
