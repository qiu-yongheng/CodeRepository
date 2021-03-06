package com.kc.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 邱永恒
 * @time 2017/12/14  15:42
 * @desc ${TODD}
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected T presenter;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        createPresenter();
        init(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void setToolBar(AppCompatActivity activity, Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected abstract int getLayout();

    protected abstract void init(View view);

    protected abstract void  createPresenter();
}
