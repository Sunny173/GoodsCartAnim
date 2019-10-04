package com.sunny.goodscartanim.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunny.goodscartanim.R;
import com.sunny.goodscartanim.localbean.ClassifyRight;
import com.sunny.goodscartanim.localbean.GoodsInfo;
import com.sunny.goodscartanim.logic.GoodsCart;

import java.util.List;

public class RightFragment extends Fragment implements View.OnClickListener {


    private RecyclerView rvRight;
    private View view;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify_right, container, false);

        view.findViewById(R.id.tv_zonghe).setOnClickListener(this);
        view.findViewById(R.id.tv_jiage).setOnClickListener(this);
        view.findViewById(R.id.tv_xiaoliang).setOnClickListener(this);

        setUnSel();
        setSel(R.id.tv_zonghe);

        rvRight = (RecyclerView) view.findViewById(R.id.rv_right);
        rvRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        id = bundle.getString("id");
        List<ClassifyRight> rightList = GsonUtils.fromJson(agrs1, GsonUtils.getListType(ClassifyRight.class));
//        rvRight.setAdapter(new GoodsAdapter(rightList));

//        ------------------
        String data = rightList.get(0).data;
        final List<GoodsInfo> goodsInfo = GsonUtils.fromJson(data, GsonUtils.getListType(GoodsInfo.class));
        BaseQuickAdapter<GoodsInfo, BaseViewHolder> baseQuickAdapter;
        baseQuickAdapter = new GoodsinfoAdapter(R.layout.item_classify_goods, goodsInfo);
        rvRight.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Context context = rvRight.getContext();
                String json = GsonUtils.toJson(goodsInfo.get(position));
//                        ActivityUtil.toDetailActivity(context, json);
                ToastUtils.showLong("去往详情");

            }
        });

        return view;
    }

    private boolean isUp = true;

    @Override
    public void onClick(View view) {
        setUnSel();
        switch (view.getId()) {
            case R.id.tv_zonghe:
                setSel(R.id.tv_zonghe);
                sortCount();

                break;
            case R.id.tv_jiage:
                setSel(R.id.tv_jiage);
                isUp = !isUp;
                setSortUp(R.id.tv_jiage, isUp);
                getData(isUp ? "asc" : "desc");
                break;
            case R.id.tv_xiaoliang:
                setSel(R.id.tv_xiaoliang);
                sortSaleNums();
                break;
        }
    }


    private void sortCount() {
        getData("count");
    }

    private void sortSaleNums() {
        getData("saleNums");
    }

    private void getData(String sort) {

    }

    private void toEmptyFragment() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof FenLeiFragment) {
            ((FenLeiFragment) fragment).toEmptyFragment();
        }

    }

    public void setSortUp(int id, boolean sortUp) {
        Drawable up = getResources().getDrawable(R.mipmap.sort_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());

        Drawable down = getResources().getDrawable(R.mipmap.sort_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

        TextView textView = view.findViewById(id);
        textView.setCompoundDrawables(null, null, sortUp ? up : down, null);
    }

    public void setSel(int idSel) {
        TextView textView = view.findViewById(idSel);
        textView.setBackgroundResource(R.drawable.green_stroke);
        textView.setTextColor(Color.parseColor("#4DC32D"));
    }

    private void setUnSel() {

        TextView tv_xiaoliang = view.findViewById(R.id.tv_xiaoliang);
        tv_xiaoliang.setTextColor(Color.parseColor("#666666"));
        tv_xiaoliang.setBackgroundResource(R.drawable.gray_stroke);

        TextView tv_zonghe = view.findViewById(R.id.tv_zonghe);
        tv_zonghe.setTextColor(Color.parseColor("#666666"));
        tv_zonghe.setBackgroundResource(R.drawable.gray_stroke);

        Drawable icon = getResources().getDrawable(R.mipmap.sort_unsel);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        TextView textView = view.findViewById(R.id.tv_jiage);
        textView.setTextColor(Color.parseColor("#666666"));
        textView.setBackgroundResource(R.drawable.gray_stroke);
        textView.setCompoundDrawables(null, null, icon, null);
    }

    public static RightFragment newInstance(String param1, int id) {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        args.putString("id", String.valueOf(id));
        fragment.setArguments(args);
        return fragment;
    }

    public RightFragment() {

    }


    private class GoodsAdapter extends RecyclerView.Adapter {
        List<ClassifyRight> rightList;

        public GoodsAdapter(List<ClassifyRight> rightList) {
            this.rightList = rightList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == ClassifyRight.Horizontal_Classify) {
//                return new HorizontalList(inflater.inflate(R.layout.item_classify_flowlayout, parent, false));
            } else if (viewType == ClassifyRight.goods) {
                return new GoodsList(inflater.inflate(R.layout.fragment_recycler_list, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String json = rightList.get(position).data;
//            if (holder instanceof HorizontalList) {
////                ((HorizontalList) holder).setData(json);
//            } else
//
            if (holder instanceof GoodsList) {
                ((GoodsList) holder).setData(json);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return rightList.get(position).type;
        }

        @Override
        public int getItemCount() {
            return rightList.size();
        }

        private class GoodsList extends RecyclerView.ViewHolder {
            RecyclerView recyclerView;

            public GoodsList(View inflate) {
                super(inflate);
                recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(inflate.getContext()));
            }

            public void setData(String data) {
                final List<GoodsInfo> goodsInfo = GsonUtils.fromJson(data, GsonUtils.getListType(GoodsInfo.class));
                BaseQuickAdapter<GoodsInfo, BaseViewHolder> baseQuickAdapter;
                baseQuickAdapter = new GoodsinfoAdapter(R.layout.item_classify_goods, goodsInfo);
                recyclerView.setAdapter(baseQuickAdapter);
                baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Context context = recyclerView.getContext();
                        String json = GsonUtils.toJson(goodsInfo.get(position));
//                        ActivityUtil.toDetailActivity(context, json);
                        ToastUtils.showLong("去往详情");

                    }
                });
            }
        }


    }

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
}

