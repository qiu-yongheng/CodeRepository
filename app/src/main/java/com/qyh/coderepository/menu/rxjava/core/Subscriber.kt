package com.qyh.coderepository.menu.rxjava.core

/**
 * @author 邱永恒
 *
 * @time 2018/3/11  14:07
 *
 * @desc 订阅者
 *
 */

abstract class Subscriber<in T> : Observer<T> {
    fun onStart() {}
}