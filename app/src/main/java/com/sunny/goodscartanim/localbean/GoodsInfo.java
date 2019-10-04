package com.sunny.goodscartanim.localbean;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by sunyanan on 2019/8/7.
 */

public class GoodsInfo {

    public String id;
    public String name;
    public String name2;
    public String imgUrl;
    public int saleNums;
    public int status;
    public double priceHight;
    public double priceLow;
    public String code;
    public String unitName;
    public int gouWucheCount;
    public double salePrice;

    public GoodsInfo() {
    }

    public GoodsInfo(String name, double priceLow, double priceHight, String imgUrl) {
        this.name = name;
        this.priceHight = priceHight;
        this.priceLow = priceLow;
        this.imgUrl = imgUrl;

    }

    public GoodsInfo(String name, String name2, double priceLow, double priceHight, String imgUrl) {
        this.name = name;
        this.name2 = name2;
        this.priceHight = priceHight;
        this.priceLow = priceLow;
        this.imgUrl = imgUrl;

    }

    public GoodsInfo(String path) {
        this.imgUrl = path;
    }

    public GoodsInfo(String name, double salePrice, String id) {
        this.name = name;
        this.priceLow = salePrice;
        this.id = id;
    }


    public String getPriceHight() {
        if (priceHight == 0) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(priceHight);
    }

    public String getPriceLow() {
        if (priceLow == 0) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(priceLow);
    }

    /**
     * 获取单价
     *
     * @return
     */
    public String getPrice() {
        if (salePrice == 0) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(salePrice);
    }
}
