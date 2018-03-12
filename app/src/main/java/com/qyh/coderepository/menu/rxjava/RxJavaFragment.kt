package com.qyh.coderepository.menu.rxjava

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kc.common.util.log.LoggerUtil
import com.qyh.coderepository.R
import com.qyh.coderepository.menu.rxjava.core.Observable
import com.qyh.coderepository.menu.rxjava.core.Subscriber

/**
 * @author 邱永恒
 *
 * @time 2018/3/11  21:30
 *
 * @desc ${TODO}
 *
 */

class RxJavaFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_rxjava, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view?.findViewById<Button>(R.id.btn)
        btn?.setOnClickListener {
            Observable.create(object : Observable.OnSubscribe<Int> {
                override fun call(subscriber: Subscriber<Int>) {
                    for (i in 0..9) {
                        subscriber.onNext(i)
                    }
                    subscriber.onCompleted()
                }
            }).map(object : Observable.Transformer<Int, String> {
                override fun call(from: Int): String {
                    return "使用map操作符转换: $from"
                }
            }).subscribe(object : Subscriber<String>() {
                override fun onNext(var1: String) {
                    LoggerUtil.d(var1)
                }

                override fun onError(t: Throwable) {
                }

                override fun onCompleted() {
                    LoggerUtil.d("onCompleted")
                }
            })
        }
    }
}