package com.qyh.coderepository.dagger.data;

import javax.inject.Inject;

/**
 * @author 邱永恒
 * @time 2017/11/15  9:51
 * @desc 使用@Inject注解进行注入
 */

public class Shoe {
    @Inject
    public Shoe() {
    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
