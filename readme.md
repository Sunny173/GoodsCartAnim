Android 抛物线动画实现购物车加入物品效果
# 1.效果图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191005112503600.gif)
# 2.关键代码
	1.xml布局代码，item布局
    ```
    <?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <RelativeLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:id="@+id/tb"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal" />

        <com.ashokvarma.bottomnavigation.BottomNavigationBar
            android:id="@+id/bottom_navigation_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true" />

    </RelativeLayout>

</android.support.design.widget.CoordinatorLayout>
    ```
    item布局
      ```
       <?xml version="1.0" encoding="utf-8"?>
       <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/iv_image"
        android:layout_width="85dp"
        android:layout_height="85dp" />

    <TextView
        android:id="@+id/tv_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="110dp"
        android:text="新鲜白菜两颗"
        android:textColor="#333333"
        android:textSize="14sp"
        app:layout_constraintStart_toStartOf="parent" />

    <TextView
        android:id="@+id/tv_2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="110dp"
        android:layout_marginTop="20dp"
        android:paddingBottom="3dp"
        android:paddingRight="8dp"
        android:paddingTop="3dp"
        android:text="让你吃的安心 放心 舒心"
        android:textColor="#999999"
        android:textSize="12sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <TextView
        android:id="@+id/tv_low_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="110dp"
        android:layout_marginTop="50dp"
        android:text="¥29.90"
        android:textColor="#FF8500"
        android:textSize="16sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_high_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="174dp"
        android:text="¥59.90"
        android:textColor="#999999"
        android:textSize="12sp"
        app:layout_constraintBottom_toBottomOf="@+id/tv_low_price"
        app:layout_constraintStart_toStartOf="parent" />


    <TextView
        android:id="@+id/tv_yuexiaoliang"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="110dp"
        android:layout_marginTop="73dp"
        android:background="@drawable/orange_dotted"
        android:paddingBottom="3dp"
        android:paddingLeft="8dp"
        android:paddingRight="8dp"
        android:paddingTop="3dp"
        android:text="月销量"
        android:textColor="#FF8500"
        android:textSize="10sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_sale_nums"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="3dp"
        android:layout_marginLeft="163dp"
        android:text="3026件"
        android:textColor="#999999"
        android:textSize="10sp"
        app:layout_constraintBottom_toBottomOf="@+id/tv_yuexiaoliang"
        app:layout_constraintStart_toStartOf="parent" />



    <ImageView
        android:id="@+id/iv_gouwuche"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="52dp"
        android:padding="20dp"
        android:src="@mipmap/gouwuche"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
      ```
	2.adapter 使用了简化写法，当然需要第三方类库支持，这里主要关注点击事件是如何传递给外面的
    ```
        private class GoodsinfoAdapter extends BaseQuickAdapter<GoodsInfo, BaseViewHolder> {
        public GoodsinfoAdapter(int layoutResId, List<GoodsInfo> orderDetails) {
            super(layoutResId, orderDetails);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsInfo item) {
            helper.setText(R.id.tv_name, item.name)
                    .setText(R.id.tv_2, item.name2)
                    .setText(R.id.tv_low_price, "¥ " + item.getPriceLow());
            helper.setText(R.id.tv_sale_nums, item.saleNums + "件");
            if (item.getPriceHight() != null) {
                TextView textView = helper.getView(R.id.tv_high_price);
                textView.setText("¥ " + item.getPriceHight());
                // 中间加横线
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            ImageView imageview = helper.getView(R.id.iv_image);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.defalut_load)//图片加载出来前，显示的图片
                    .error(R.mipmap.defalut_load);
            Glide.with(imageview.getContext()).load(item.imgUrl) //图片地址
                    .apply(options).into(imageview);
            helper.getView(R.id.iv_gouwuche).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addGoodsAnim(view, item.imgUrl);
                    GoodsCart.addGoods(item, view.getContext());
                }
            });

        }
    }

    private void addGoodsAnim(View view, String imgUrl) {
        Fragment fm = getParentFragment();
        if (fm instanceof FenLeiFragment) {
            ((FenLeiFragment) fm).addGoodsAnim(view, imgUrl);
        }
    }
    ```
	3.自定义动画的控件
    ```
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
    ```
	4.activity实现
	5.插值器
    ```
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
    ```
# 3.抽取，简化调用
# 4.使用方法
把 GoodsCartAnim 类拷贝到自己项目目录下，在自己activity内调用 startAnim 方法。第一个参数表示被点击的商品；第二个参数表示想要传入的图片路径非必填；第三个参数表示真正的购物车；第四个参数表示包含购物车和点击view的父布局。
  ```
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
   ``` 
# 5.其他
1.需要引入的依赖 在app->build.gradle下添加如下内容
dependencies {
   ....
    //    --------------ui----------------
    implementation 'com.android.support:recyclerview-v7:27.1.1'
    
      implementation 'com.ashokvarma.android:bottom-navigation-bar:2.1.0'// 底部导航

    //    --------------ui----------------
    //  https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md
    implementation 'com.blankj:utilcode:1.25.5'
	.....

}
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }//添加jitpack仓库地址
        ...
    }
}

2.项目里用到了简单列表，所以需要在项目的gradle中增加仓库 
 implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'    


下载二维码
https://gitee.com/sunyn/gouwuchedonghua/raw/master/apk/app-release.apk
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191005150619811.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0xvdmVfaG9yc2U=,size_16,color_FFFFFF,t_70)

项目地址：https://gitee.com/sunyn/gouwuchedonghua.git
参考出处：https://www.cnblogs.com/mthoutai/archive/2017/08/20/7399533.html