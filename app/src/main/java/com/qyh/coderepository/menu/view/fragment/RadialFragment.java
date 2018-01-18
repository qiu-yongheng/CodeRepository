package com.qyh.coderepository.menu.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.common.util.toast.ToastManager;
import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.view.RadialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/8  13:34
 * @desc ${TODD}
 */

public class RadialFragment extends Fragment {
    @BindView(R.id.btn_radial)
    RadialButton btnRadial;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radial, container, false);
        unbinder = ButterKnife.bind(this, view);
        btnRadial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.getToast().tip("点击了");
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
