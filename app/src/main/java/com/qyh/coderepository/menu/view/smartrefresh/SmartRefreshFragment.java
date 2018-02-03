package com.qyh.coderepository.menu.view.smartrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.view.smartrefresh.footer.BallPulseFooter;
import com.qyh.coderepository.menu.view.smartrefresh.footer.ballpulse.BallPulseView;
import com.qyh.coderepository.menu.view.smartrefresh.header.bezierradar.RippleView;
import com.qyh.coderepository.menu.view.smartrefresh.header.bezierradar.RoundDotView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/30  16:20
 * @desc ${TODD}
 */

public class SmartRefreshFragment extends Fragment {
    @BindView(R.id.ball_pulse_view)
    BallPulseView ballPulseView;
    Unbinder unbinder;
    @BindView(R.id.ball_pulse_footer)
    BallPulseFooter ballPulseFooter;
    @BindView(R.id.ripple_view)
    RippleView rippleView;
    @BindView(R.id.round_dot_view)
    RoundDotView roundDotView;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        ballPulseView.startAnim();
        ballPulseFooter.onStartAnimator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                rippleView.startReveal();
            }
        });
        roundDotView.startAnim(1.5f);
    }

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
