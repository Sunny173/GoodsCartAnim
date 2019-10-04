package com.sunny.goodscartanim.logic;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.sunny.goodscartanim.localbean.GoodsInfo;
import com.sunny.goodscartanim.util.Contants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by sunyanan on 2019/8/9.
 * 购物车
 */

public class GoodsCart {
    private static CartMap cartMap = new CartMap();

    public static void removeGoods(final GoodsInfo item, Context context) {

        if (Contants.isTest) {
            cartMap.removeOneByOne(item);
            EventBus.getDefault().post(item);
            return;
        }


    }

    public static void addGoods(final GoodsInfo item, Context context) {
        if (Contants.isTest) {
            ToastUtils.showLong("商品+1");
            cartMap.addObject(item);
            EventBus.getDefault().post(item);
            return;
        }
    }

    public static void addGoods(GoodsInfo item, int count) {
        cartMap.addObject(item, count);
    }


    public static int getCount(GoodsInfo item) {
        return cartMap.getObjCount(item);
    }


    /**
     * get all goods count
     *
     * @return
     */
    public static int getCount() {
        return cartMap.getObjCount();
    }

    public static void clear() {
        cartMap.clear();
    }


    public static List<GoodsInfo> getAllGoods() {
        return cartMap.getAllObj();
    }
}
