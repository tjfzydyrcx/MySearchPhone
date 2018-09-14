package com.example.my360windowmanager;

/**
 * Created by Administrator on 2017-08-07 0007.
 */
public class Item_b implements TypeInterf {

    int img;
    String text1;
    String text2;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    @Override
    public int getType() {
        return R.layout.item_b;
    }

}
