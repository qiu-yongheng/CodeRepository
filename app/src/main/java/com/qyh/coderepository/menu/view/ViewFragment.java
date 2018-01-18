package com.qyh.coderepository.menu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qyh.coderepository.MainActivity;
import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.view.fragment.ExplosionFragment;
import com.qyh.coderepository.menu.view.fragment.LinearGradientFragment;
import com.qyh.coderepository.menu.view.fragment.QuadFragment;
import com.qyh.coderepository.menu.view.fragment.QuadLlFragment;
import com.qyh.coderepository.menu.view.fragment.RedPointFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/2  15:56
 * @desc ${TODD}
 */

public class ViewFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_quad)
    Button btnQuad;
    @BindView(R.id.btn_quad_ll)
    Button btnQuadLl;
    @BindView(R.id.btn_red_point)
    Button btnRedPoint;
    @BindView(R.id.btn_explosion)
    Button btnExplosion;
    Unbinder unbinder;
    @BindView(R.id.btn_ll_shape)
    Button btnLlShape;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initListener() {
        btnQuad.setOnClickListener(this);
        btnQuadLl.setOnClickListener(this);
        btnRedPoint.setOnClickListener(this);
        btnExplosion.setOnClickListener(this);
        btnLlShape.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quad: // 贝塞尔曲线动画
                ((MainActivity) getActivity()).push(new QuadFragment());
                break;
            case R.id.btn_quad_ll: // ViewGroup背景动画
                ((MainActivity) getActivity()).push(new QuadLlFragment());
                break;
            case R.id.btn_red_point: // 小红点
                ((MainActivity) getActivity()).push(new RedPointFragment());
                break;
            case R.id.btn_explosion: // 爆炸悬浮框
                ((MainActivity) getActivity()).push(new ExplosionFragment());
                break;
            case R.id.btn_ll_shape: // 闪动文字
                ((MainActivity) getActivity()).push(new LinearGradientFragment());
                break;
        }
    }
}