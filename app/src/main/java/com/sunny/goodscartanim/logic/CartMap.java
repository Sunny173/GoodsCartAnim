package com.sunny.goodscartanim.logic;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sunyanan on 2019/8/9.
 * 这是一个数据结构
 * 1.添加某一对象
 * 2.删除某一对象
 * 3.计算传入对象的个数
 * 4.是否包含某一对象
 * 5.返回已添加的对象的个数
 * 6.返回已添加的所有对象
 * 7.清空所有对象
 */

public class CartMap<T> {

    Map<String, Count> objMap = new HashMap<>();

    public void addObject(T obj, int count) {
        if (obj == null)
            return;
        String id = GsonUtils.toJson(obj);
        objMap.put(id, new Count(count, obj));
    }

    public void addObject(T obj) {
        if (obj == null)
            return;
        String id = GsonUtils.toJson(obj);
        int count = 1;
        if (objMap.containsKey(id)) {
            count = objMap.get(id).count + 1;
        }
        objMap.put(id, new Count(count, obj));
    }

    public void removeOneByOne(T obj) {

        if (obj == null)
            return;
        String id = GsonUtils.toJson(obj);
        LogUtils.e("removeOneByOne one " + objMap.size());
        if (!objMap.containsKey(id)) {
            return;
        }
        int count = objMap.get(id).count;
        if (count == 1) {
            objMap.remove(id);
        } else {
            count--;
            objMap.put(id, new Count(count, obj));
        }
        LogUtils.e("removeOneByOne one after-->" + objMap.size());

    }

    public void removeObject(T obj) {
        if (obj == null)
            return;
        String id = GsonUtils.toJson(obj);
        LogUtils.e("removeOneByOne " + objMap.size());
        if (!objMap.containsKey(id)) {
            return;
        }
        objMap.remove(id);
        LogUtils.e("removeOneByOne after-->" + objMap.size());

    }

    public int getObjCount(T obj) {
        String id = GsonUtils.toJson(obj);
        if (objMap.containsKey(id)) {
            return objMap.get(id).count;
        }
        return 0;
    }

    public int getObjCount() {
        int count = 0;
        Iterator<Map.Entry<String, Count>> it = objMap.entrySet().iterator();
        while (it.hasNext()) {
            Count tmp = it.next().getValue();
            count = count + tmp.count;
        }
        return count;
    }

    public boolean containsKey(String json) {
        return objMap.containsKey(json);
    }

    public int size() {
        return objMap.size();
    }

    public List<T> getAllObj() {
        List<T> list = new ArrayList<>();
        Iterator<Map.Entry<String, Count>> it = objMap.entrySet().iterator();
        while (it.hasNext()) {
            Count tmp = it.next().getValue();
            list.add(tmp.obj);
        }
        return list;
    }

    public void clear() {
        objMap.clear();
    }

    class Count {
        int count;
        T obj;

        public Count(int count, T obj) {
            this.count = count;
            this.obj = obj;

        }
    }
}
