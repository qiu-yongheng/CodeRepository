package com.qyh.coderepository.menu.view.explosion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/6  10:43
 * @desc ${TODD}
 */

public class ExplosionFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explosion, container, false);
        return view;
    }
}
