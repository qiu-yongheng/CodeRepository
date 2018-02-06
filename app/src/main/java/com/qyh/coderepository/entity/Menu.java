package com.qyh.coderepository.entity;

/**
 * @author 邱永恒
 * @time 2018/2/6  10:16
 * @desc ${TODD}
 */

public class Menu {
    private int imgSrc;
    private String content;

    public Menu(int imgSrc, String content) {
        this.imgSrc = imgSrc;
        this.content = content;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
