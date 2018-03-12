package com.qyh.coderepository.menu.rxjava.core

import com.qyh.coderepository.menu.rxjava.core.Observable.OnSubscribe
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


/**
 * @author 邱永恒
 *
 * @time 2018/3/11  21:03
 *
 * @desc 被订阅者
 *
 */

class Observable<out T> private constructor(private val onSubscribe: OnSubscribe<T>) {

    companion object {
        /**
         * 创建被订阅者
         */
        fun <T> create(onSubscribe: OnSubscribe<T>): Observable<T> {
            return Observable(onSubscribe)
        }
    }

    /**
     * 订阅
     */
    fun subscribe(subscriber: Subscriber<T>) {
        subscriber.onStart()
        // 传入订阅者, 给子类发射事件
        onSubscribe.call(subscriber)
    }

    /**
     * map操作符, 桥接
     */
    fun <R> map(transformer: Transformer<T, R>): Observable<R> {
        return create(MapOnSubscribe(this, transformer))
    }

    // 提取出内部类, 订阅
    inner class MapOnSubscribe<T, out R>(private val source: Observable<T>, private val transformer: Observable.Transformer<T, R>) : Observable.OnSubscribe<R> {
        override fun call(subscriber: Subscriber<R>) {
            // 订阅源被订阅者, 桥接
            source.subscribe(MapSubscriber(subscriber, transformer))
        }
    }

    // 提取出内部类, 订阅者
    inner class MapSubscriber<T, in R>(private val actual: Subscriber<T>, private val transformer: Observable.Transformer<R, T>) : Subscriber<R>() {
        override fun onCompleted() {
            actual.onCompleted()
        }

        override fun onError(t: Throwable) {
            actual.onError(t)
        }

        override fun onNext(var1: R) {
            actual.onNext(transformer.call(var1))
        }
    }

    /**
     * 将订阅事件抽象成接口, 给子类实现
     * in T : 只可被消费, 不能被生产
     */
    interface OnSubscribe<out T> {
        fun call(subscriber: Subscriber<T>)
    }

    interface Transformer<in T, out R> {
        fun call(from: T): R
    }
}