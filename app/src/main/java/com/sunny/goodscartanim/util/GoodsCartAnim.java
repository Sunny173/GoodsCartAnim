package com.sunny.goodscartanim.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.PointF;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sunny.goodscartanim.R;

/**
 * Created by sunyanan on 2019/10/3.
 * 添加商品的动画
 */

public class GoodsCartAnim {


    public static void startAnim(View itemView, String imgUrl, View cart, RelativeLayout container) {
        startAnim(itemView, 40, imgUrl, cart, container);
    }

    /**
     * @param addV        点击的view
     * @param pianyiliang 偏移量-表示向←；+表示向右，0表示无偏移
     * @param imgUrl
     * @param shopImg     商品添加到的view
     * @param container
     */
    public static void startAnim(View addV, int pianyiliang, String imgUrl, View shopImg, RelativeLayout container) {
        int[] childCoordinate = new int[2];
        int[] parentCoordinate = new int[2];
        int[] shopCoordinate = new int[2];
        //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
        addV.getLocationInWindow(childCoordinate);
        container.getLocationInWindow(parentCoordinate);
        shopImg.getLocationInWindow(shopCoordinate);

        //2.自定义ImageView 继承ImageView
        MoveImageView img = new MoveImageView(addV.getContext());
        if (TextUtils.isEmpty(imgUrl)) {
            img.setImageResource(R.mipmap.gouwuche);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.defalut_load)//图片加载出来前，显示的图片
                    .transforms(new CircleCrop()).error(R.mipmap.defalut_load);
            Glide.with(img.getContext()).load(imgUrl) //图片地址
                    .apply(options).into(img);
        }

        //3.设置img在父布局中的坐标位置
        img.setX(childCoordinate[0] - parentCoordinate[0]);
        img.setY(childCoordinate[1] - parentCoordinate[1]);
        //4.父布局添加该Img
        container.addView(img);

        //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        //开始的数据点坐标就是 addV的坐标
        startP.x = childCoordinate[0] - parentCoordinate[0];
        startP.y = childCoordinate[1] - parentCoordinate[1];
        //结束的数据点坐标就是 shopImg的坐标
        endP.x = shopCoordinate[0] - parentCoordinate[0] + pianyiliang;
        endP.y = shopCoordinate[1] - parentCoordinate[1];
        //控制点坐标 x等于 购物车x；y等于 addV的y
        controlP.x = endP.x;
        controlP.y = startP.y;

        //启动属性动画
        ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        animator.setDuration(1000);
        animator.addListener(new CartAnimator(container, shopImg));
        animator.start();

    }


    public static class MoveImageView extends android.support.v7.widget.AppCompatImageView {

        public MoveImageView(Context context) {
            super(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,
                    150);
            setLayoutParams(params);
        }

        public void setMPointF(PointF pointF) {
            setX(pointF.x);
            setY(pointF.y);
        }
    }

    /**
     * 自定义估值器
     */
    public static class PointFTypeEvaluator implements TypeEvaluator<PointF> {
        /**
         * 每个估值器对应一个属性动画，每个属性动画仅对应唯一一个控制点
         */
        PointF control;
        /**
         * 估值器返回值
         */
        PointF mPointF = new PointF();

        public PointFTypeEvaluator(PointF control) {
            this.control = control;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return getBezierPoint(startValue, endValue, control, fraction);
        }

        /**
         * 二次贝塞尔曲线公式
         *
         * @param start   开始的数据点
         * @param end     结束的数据点
         * @param control 控制点
         * @param t       float 0-1
         * @return 不同t对应的PointF
         */
        private PointF getBezierPoint(PointF start, PointF end, PointF control, float t) {
            mPointF.x = (1 - t) * (1 - t) * start.x + 2 * t * (1 - t) * control.x + t * t * end.x;
            mPointF.y = (1 - t) * (1 - t) * start.y + 2 * t * (1 - t) * control.y + t * t * end.y;
            return mPointF;
        }
    }

    private static class CartAnimator implements Animator.AnimatorListener {
        RelativeLayout container;
        View shopImg;

        public CartAnimator(RelativeLayout container, View shopImg) {
            this.container = container;
            this.shopImg = shopImg;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
//动画结束后 父布局移除 img
            Object target = ((ObjectAnimator) animator).getTarget();
            container.removeView((View) target);
            //shopImg 开始一个放大动画
            Animation scaleAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.shop_scale);
            shopImg.startAnimation(scaleAnim);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}
