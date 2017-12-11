package com.qyh.coderepository.dagger.component;

import com.qyh.coderepository.dagger.SecondFragment;
import com.qyh.coderepository.dagger.annotation.PreFragment;
import com.qyh.coderepository.dagger.module.SecondModule;

import dagger.Component;

/**
 * @author 邱永恒
 * @time 2017/11/15  8:57
 * @desc ${TODD}
 */
@PreFragment // 作用域
@Component(modules = SecondModule.class, dependencies = BaseComponent.class) // modules的作用就是声明该Component含有哪几个Module,当Component需要某个依赖对象时,就会通过这些Module类中对应的方法获取依赖对象
public interface SecondComponent {
    // 这个方法可以将依赖需求方对象送到Component类中,Component类就会根据依赖需求方对象中声明的依赖关系来注入依赖需求方对象中所需要的对象
    void inject(SecondFragment fragment);
}
