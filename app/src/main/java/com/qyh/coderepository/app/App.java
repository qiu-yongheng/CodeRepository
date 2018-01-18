package com.qyh.coderepository.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qyh.coderepository.menu.dagger.component.BaseComponent;
import com.qyh.coderepository.menu.dagger.component.DaggerBaseComponent;
import com.qyh.coderepository.menu.dagger.module.BaseModule;
import com.qyh.coderepository.menu.database.manager.DbHelper;
import com.kc.common.util.toast.ToastManager;


/**
 * @author 邱永恒
 * @time 2017/11/9  11:00
 * @desc ${TODD}
 */

public class App extends Application{
    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        baseComponent = DaggerBaseComponent.builder().baseModule(new BaseModule()).build();

        initLogger();
        initToast();
        Utils.init(this);
        DbHelper.getInstance().init(this);
    }

    private void initToast() {
        ToastManager.getToast().init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("CodeRepository")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public BaseComponent getBaseComponent() {
        return baseComponent;
    }
}
