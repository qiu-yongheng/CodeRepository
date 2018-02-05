package com.qyh.coderepository.menu.view.slid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/2/5  22:58
 * @desc 仿QQ侧滑菜单
 */

public class SlidingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding, container, false);
        initView();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initListener() {

    }
}
