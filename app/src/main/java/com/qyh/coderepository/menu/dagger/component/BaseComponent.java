package com.qyh.coderepository.menu.dagger.component;

import com.qyh.coderepository.menu.dagger.data.ClothHandler;
import com.qyh.coderepository.menu.dagger.module.BaseModule;
import com.qyh.coderepository.menu.dagger.module.SecondModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author 邱永恒
 * @time 2017/11/15  11:38
 * @desc ${TODD}
 */

@Singleton //对应Module中声明的单例
@Component(modules = BaseModule.class)
public interface BaseComponent {
    ClothHandler getClothHandler();

    //@Subcomponent使用的声明方式,声明一个返回值为子组件的方法,子组件需要什么Module,就在方法参数中添加什么
    SubSecondComponent getSubSecondComponent(SecondModule secondModule);
}
