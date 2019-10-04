package com.sunny.goodscartanim.test;

import com.blankj.utilcode.util.GsonUtils;
import com.sunny.goodscartanim.localbean.ClassifyRight;
import com.sunny.goodscartanim.localbean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyanan on 2019/10/3.
 */

public class TestGoodsData {
    public static List<GoodsInfo> getGoodsInfos() {
        String str1 = "http://b15.photo.store.qq.com/psu?/2d6eaaea-ab42-45ff-b330-796a6eb473b2/XUmxnrGbDyPWv71q3pkeBAPtFe7mxpqe3aFYzRYJKmo!/b/YaEqKQ3DNQAAYi8H*QjmwQAA&a=22&b=15&bo=QAHwAAAAAAABEIY!&rf=viewer_4";
        String str2 ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570186391422&di=3586df520200747fcbdfe80861c514ce&imgtype=0&src=http%3A%2F%2Fwx3.sinaimg.cn%2Forj360%2F86ec109cly1g6etr2u61xj20bf06wmzz.jpg";
        String str3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570186424010&di=74dad6006362c4f2352b927977f800f9&imgtype=0&src=http%3A%2F%2Fpic54.nipic.com%2Ffile%2F20141116%2F17961491_170714944000_2.jpg";
        List<GoodsInfo> objs = new ArrayList<>();
        objs.add(new GoodsInfo("三只松鼠750g一包", "最好的零食陪伴", 17.20, 23.9, str1));
        objs.add(new GoodsInfo("益达木糖醇一盒益达木糖醇一盒", "最好的零食陪伴", 17.20, 23.9, str1));
        objs.add(new GoodsInfo("Bast草莓味一包益达木糖醇一盒", "最好的零食陪伴", 17.20, 23.9, str1));
        objs.add(new GoodsInfo("立白洗衣液21kg益达木糖醇一盒", "洗衣最好的选择", 12.20, 28.9, str2));
        objs.add(new GoodsInfo("超能洗衣液2kg", "洗衣最好的选择", 12.20, 28.9, str2));
        objs.add(new GoodsInfo("洁净洗衣液3kg", "洗衣最好的选择", 12.20, 28.9, str2));
        objs.add(new GoodsInfo("护舒宝10片超薄", "护舒宝10片超薄", 27.20, 58.9, str3));
        objs.add(new GoodsInfo("清风3包原木纯品", "护舒宝10片超薄", 27.20, 58.9, str3));
        objs.add(new GoodsInfo("ABC卫生巾8片", "护舒宝10片超薄", 27.20, 58.9, str3));
        return objs;
    }


    public static List<ClassifyRight> getRightTest() {
        List<ClassifyRight> rightList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        stringList.add("爆款特价");
        stringList.add("爆款区");
        stringList.add("爆款");
        String[] strings = new String[]{"爆款特价", "爆款区", "爆款"};
//        rightList.add(new ClassifyRight(ClassifyRight.Horizontal_Classify, GsonUtils.toJson(strings)));
        List<GoodsInfo> goodsInfos = getGoodsInfos();
        rightList.add(new ClassifyRight(ClassifyRight.goods, GsonUtils.toJson(goodsInfos)));
        return rightList;
    }
}
