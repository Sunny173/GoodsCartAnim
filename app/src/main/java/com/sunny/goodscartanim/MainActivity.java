package com.sunny.goodscartanim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.blankj.utilcode.util.ConvertUtils;
import com.sunny.goodscartanim.fragment.CartFragment;
import com.sunny.goodscartanim.fragment.FenLeiFragment;
import com.sunny.goodscartanim.fragment.HomeFragment;
import com.sunny.goodscartanim.fragment.MyFragment;
import com.sunny.goodscartanim.localbean.GoodsInfo;
import com.sunny.goodscartanim.logic.GoodsCart;
import com.sunny.goodscartanim.util.GoodsCartAnim;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

public class MainActivity  extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private MyFragment mMyFragment;
    private FenLeiFragment mScanFragment;
    private HomeFragment mHomeFragment;
    private CartFragment mCartFragment;
    private static TextBadgeItem mBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        /**
         * bottomNavigation 设置
         */
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#4DC32D") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#FCFCFC");//导航栏背景色
        mBadgeItem = new TextBadgeItem().setBackgroundColor(Color.TRANSPARENT)
                .setBorderWidth(4)
                .setAnimationDuration(200);
//                .setHideOnSelect(true);
        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_fenlei, "分类"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_cart, "购物车").setBadgeItem(mBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.tab_my, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise(); //initialise 一定要放在 所有设置的最后一项
        setBottomNavigationItem(bottomNavigationBar, 6, 26, 10);
        setDefaultFragment();//设置默认导航栏
    }

    public void selectTab(int postion) {
        onTabReselected(postion);
        bottomNavigationBar.selectTab(postion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mMyFragment != null) {
                mMyFragment.onResume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setXiaoHongDian(GoodsInfo item) {
        int count = GoodsCart.getCount();
        if (count != 0) {
            mBadgeItem.setBackgroundColor(Color.RED);
            mBadgeItem.setText(String.valueOf(count));
        } else {
            mBadgeItem.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    /*
        * @param bottomNavigationBar，需要修改的 BottomNavigationBar
        * @param space 图片与文字之间的间距
        * @param imgLen 单位：dp，图片大小，应 <= 36dp
        * @param textSize 单位：dp，文字大小，应 <= 20dp
        *
        *  使用方法：直接调用setBottomNavigationItem(bottomNavigationBar, 6, 26, 10);
        *  代表将bottomNavigationBar的文字大小设置为10dp，图片大小为26dp，二者间间距为6dp
        *
        * */
    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize) {
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try {
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    for (int j = 0; j < mTabContainer.getChildCount(); j++) {
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ConvertUtils.dp2px(56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(ConvertUtils.dp2px(12), ConvertUtils.dp2px(0),
                                ConvertUtils.dp2px(12), ConvertUtils.dp2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0, 0, 0,
                                ConvertUtils.dp2px(20 - textSize - space / 2));

                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(ConvertUtils.dp2px(imgLen), ConvertUtils.dp2px(imgLen));
                        params.setMargins(0, 0, 0, space / 2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public View getCartView() throws IllegalAccessException {
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                return mTabContainer.getChildAt(2);
            }
        }
        return null;
    }

    public void addGoodsAnim(View itemView, String imgUrl) {
        try {
            View cart = getCartView();
            if (cart != null) {
                RelativeLayout container = findViewById(R.id.container);
                GoodsCartAnim.startAnim(itemView,imgUrl, cart, container);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置默认导航栏
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = HomeFragment.newInstance("首页");
        transaction.replace(R.id.tb, mHomeFragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mMyFragment != null) {
            mMyFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (mCartFragment != null) {
            mCartFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (mHomeFragment != null) {
            mHomeFragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    /**
     * 设置导航选中的事件
     */
    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance("首页");
                }
                transaction.replace(R.id.tb, mHomeFragment);
                break;
            case 1:
                if (mScanFragment == null) {
                    mScanFragment = FenLeiFragment.newInstance("分类");
                }
                transaction.replace(R.id.tb, mScanFragment);
                break;
            case 2:
                if (mCartFragment == null) {
                    mCartFragment = CartFragment.newInstance("购物车");
                }
                transaction.replace(R.id.tb, mCartFragment);
                break;
            case 3:
                if (mMyFragment == null) {
                    mMyFragment = MyFragment.newInstance("我的");
                }
                transaction.replace(R.id.tb, mMyFragment);
                break;

            default:
                break;
        }

        transaction.commit();// 事务提交
    }

    /**
     * 设置未选中Fragment 事务
     */
    @Override
    public void onTabUnselected(int position) {

    }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {

    }


}

