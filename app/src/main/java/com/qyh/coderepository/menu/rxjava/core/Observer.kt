package com.qyh.coderepository.menu.rxjava.core




/**
 * @author 邱永恒
 *
 * @time 2018/3/11  14:06
 *
 * @desc 观察者接口
 *
 */

interface Observer<in T> {
    fun onNext(var1: T)
    fun onError(t: Throwable)
    fun onCompleted()
}