package com.qyh.coderepository.dagger.data;

/**
 * @author 邱永恒
 * @time 2017/11/15  11:03
 * @desc 工具类, 根据布料生产衣服
 */

public class ClothHandler {
    public Clothes handle(Cloth cloth){
        return new Clothes(cloth);
    }
}
