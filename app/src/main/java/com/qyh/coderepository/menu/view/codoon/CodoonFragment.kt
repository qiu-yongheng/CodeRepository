package com.qyh.coderepository.menu.view.codoon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qyh.coderepository.MainActivity
import com.qyh.coderepository.R

/**
 * @author 邱永恒
 *
 * @time 2018/3/5  13:31
 *
 * @desc ${TODD}
 *
 */
class CodoonFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_codoon, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view!!.findViewById<Toolbar>(R.id.tool_bar)
        val mainActivity = activity as MainActivity
        mainActivity.setSupportActionBar(toolbar)
        val actionBar = mainActivity.supportActionBar
        actionBar!!.title = "03月06日 健走"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val vSlidingMenu = view.findViewById<MyFrameLayout>(R.id.v_slide_menu)
    }
}