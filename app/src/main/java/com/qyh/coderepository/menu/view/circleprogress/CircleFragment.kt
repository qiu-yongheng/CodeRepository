package com.qyh.coderepository.menu.view.circleprogress

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qyh.coderepository.R

/**
 * @author 邱永恒
 *
 * @time 2018/3/19  11:42
 *
 * @desc ${TODD}
 *
 */
class CircleFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_circle, container, false)
    }
}