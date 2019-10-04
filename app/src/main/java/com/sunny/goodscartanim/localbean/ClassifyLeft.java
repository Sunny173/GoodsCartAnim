package com.sunny.goodscartanim.localbean;

/**
 * Created by sunyanan on 2019/9/6.
 * 分类左边的数据
 */

public class ClassifyLeft {
    public String parent;
    public ClassifyLeft[] child;
//    public String[] child;
    public int id;


    public ClassifyLeft(String parent, int id, ClassifyLeft[] child) {
        this.parent = parent;
        this.child = child;
        this.id = id;
    }

    public ClassifyLeft(String parent, ClassifyLeft[] child) {
        this.parent = parent;
        this.child = child;
    }

}
