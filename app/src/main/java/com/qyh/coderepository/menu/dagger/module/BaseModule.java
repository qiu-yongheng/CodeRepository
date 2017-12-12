package com.qyh.coderepository.menu.dagger.module;

import com.qyh.coderepository.menu.dagger.data.ClothHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author 邱永恒
 * @time 2017/11/15  11:34
 * @desc ${TODD}
 */
@Module
public class BaseModule {
    @Singleton //单例
    @Provides
    public ClothHandler getClothHandler(){
        return new ClothHandler();
    }
}
