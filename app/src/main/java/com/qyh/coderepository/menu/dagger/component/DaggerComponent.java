package com.qyh.coderepository.menu.dagger.component;

import com.qyh.coderepository.menu.dagger.DaggerFragment;
import com.qyh.coderepository.menu.dagger.annotation.PreFragment;
import com.qyh.coderepository.menu.dagger.module.DaggerModule;

import dagger.Component;

/**
 * @author 邱永恒
 * @time 2017/11/15  8:57
 * @desc ${TODD}
 */
@PreFragment // 作用域
@Component(modules = DaggerModule.class, dependencies = BaseComponent.class) // modules的作用就是声明该Component含有哪几个Module,当Component需要某个依赖对象时,就会通过这些Module类中对应的方法获取依赖对象
public interface DaggerComponent {
    // 这个方法可以将依赖需求方对象送到Component类中,Component类就会根据依赖需求方对象中声明的依赖关系来注入依赖需求方对象中所需要的对象
    void inject(DaggerFragment fragment);
}
