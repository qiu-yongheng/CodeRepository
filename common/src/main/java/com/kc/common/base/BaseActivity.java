package com.kc.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kc.common.util.activity.ActivityManager;

/**
 * @author 邱永恒
 * @time 2017/12/14  15:42
 * @desc ${TODD}
 */

public abstract class BaseActivity<T extends MyPresenter> extends AppCompatActivity implements BaseView {
    protected T mPresenter;
    protected Activity mContext;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;

        createPresenter();

        ActivityManager.getInstance().addActivity(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected abstract int getLayout();

    protected abstract void init();

    protected abstract void  createPresenter();
}
