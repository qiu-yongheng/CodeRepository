package com.qyh.coderepository.dagger.component;

import com.qyh.coderepository.dagger.SubSecondFragment;
import com.qyh.coderepository.dagger.module.SecondModule;

import dagger.Subcomponent;

/**
 * @author 邱永恒
 * @time 2017/11/15  14:01
 * @desc
 * @Subcomponent注解的使用:
 * 1. 子组件的声明方式由 @Component改为 @Subcomponent
 * 2. 在父组件中要声明一个返回值为子组件的方法,当子组件需要什么Module时,就在该方法中添加该类型的参数
 *
 * 注意:
 * 用@Subcomponent注解声明的Component是无法单独使用的,想要获取该Component实例必须经过其父组件
 */
@Subcomponent(modules = SecondModule.class)
public interface SubSecondComponent {
    void inject(SubSecondFragment fragment);
}
