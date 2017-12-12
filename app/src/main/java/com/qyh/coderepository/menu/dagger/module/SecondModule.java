package com.qyh.coderepository.menu.dagger.module;

import com.qyh.coderepository.menu.dagger.annotation.PreFragment;
import com.qyh.coderepository.menu.dagger.data.Cloth;
import com.qyh.coderepository.menu.dagger.data.Clothes;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author 邱永恒
 * @time 2017/11/14  17:24
 * @desc 类似工厂类, 提供需要注入的对象
 */
@Module
public class SecondModule {

    // 声明Module类中哪些方法是用来提供依赖对象的
    //@Singleton
    @PreFragment
    @Provides
    public Cloth getCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    @Named("red")
    public Cloth getRedCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    @Named("blue")
    public Cloth getBlueCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }

    // 只要在getClothes方法中添加Cloth参数,Dagger2就会像帮依赖需求方找依赖对象一样帮你找到该方法依赖的Cloth实例
    @Provides
    public Clothes getClothes(Cloth cloth) {
        return new Clothes(cloth);
    }

//    @PreFragment
//    @Provides
//    public ClothHandler getClothHandler() {
//        return new ClothHandler();
//    }
}
