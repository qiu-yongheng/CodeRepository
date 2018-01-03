package com.qyh.coderepository.menu.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.qyh.coderepository.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/2  15:56
 * @desc ${TODD}
 */

public class ViewFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    Unbinder unbinder;
    @BindView(R.id.rquad_to_view)
    RQuadToView rquadToView;
    @BindView(R.id.sb_height)
    AppCompatSeekBar sbHeight;
    @BindView(R.id.sb_speed)
    AppCompatSeekBar sbSpeed;
    @BindView(R.id.sb_wave)
    AppCompatSeekBar sbWave;
    @BindView(R.id.sb_wave_height)
    AppCompatSeekBar sbWaveHeight;
    @BindView(R.id.ql)
    QuadLinearLayout ql;
    private boolean isOpen;

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
        //rquadToView.startAnim();
        ql.startAnim();
    }

    private void initListener() {
        ivMenu.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                if (!isOpen) {
                    show();
                } else {
                    dismiss();
                }
                break;
            case R.id.iv_1:
            case R.id.iv_2:
            case R.id.iv_3:
            case R.id.iv_4:
                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    private void dismiss() {
        isOpen = false;
        doAnimateClose(iv1, 0, 4, 300);
        doAnimateClose(iv2, 1, 4, 300);
        doAnimateClose(iv3, 2, 4, 300);
        doAnimateClose(iv4, 3, 4, 300);
    }

    private void show() {
        isOpen = true;
        doAnimateOpen(iv1, 0, 4, 300);
        doAnimateOpen(iv2, 1, 4, 300);
        doAnimateOpen(iv3, 2, 4, 300);
        doAnimateOpen(iv4, 3, 4, 300);
    }


    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90) / (total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "rotation", 180, 0),
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(700).start();
    }

    private void doAnimateClose(View view, int index, int total, int radius) {
        double degree = Math.toRadians(90) / (total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "rotation", 0, 180),
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        //动画周期为500ms
        set.setDuration(500).start();
    }

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
