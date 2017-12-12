package com.qyh.coderepository.menu.dagger.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author 邱永恒
 * @time 2017/11/15  10:39
 * @desc Fragment作用域
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PreFragment {
}
