package com.qyh.coderepository.menu.view.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/8  16:42
 * @desc ${TODD}
 */

public class FlowFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flow, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
