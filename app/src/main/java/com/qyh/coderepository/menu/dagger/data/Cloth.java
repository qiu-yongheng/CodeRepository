package com.qyh.coderepository.menu.dagger.data;

/**
 * @author 邱永恒
 * @time 2017/11/14  17:22
 * @desc 布料类
 */

public class Cloth {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "布料";
    }
}
