package com.qyh.coderepository.menu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.view.RQuadToView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/6  10:42
 * @desc ${TODD}
 */

public class QuadFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.sb_height)
    AppCompatSeekBar sbHeight;
    @BindView(R.id.sb_speed)
    AppCompatSeekBar sbSpeed;
    @BindView(R.id.sb_wave)
    AppCompatSeekBar sbWave;
    @BindView(R.id.sb_wave_height)
    AppCompatSeekBar sbWaveHeight;
    @BindView(R.id.rquad_to_view)
    RQuadToView rquadToView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quad, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {
        rquadToView.startAnim();
    }

    private void initListener() {
        sbHeight.setOnSeekBarChangeListener(this);
        sbSpeed.setOnSeekBarChangeListener(this);
        sbWave.setOnSeekBarChangeListener(this);
        sbWaveHeight.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * SeekBar进度改变回调
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_height:
                rquadToView.setHeight(progress);
                break;
            case R.id.sb_speed:
                rquadToView.setSpeed(progress);
                break;
            case R.id.sb_wave:
                rquadToView.setWave(progress);
                break;
            case R.id.sb_wave_height:
                rquadToView.setWaveHeight(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
