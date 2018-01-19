package com.qyh.coderepository;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kc.common.util.fragment.FragmentStack;
import com.kc.common.util.log.LoggerUtil;
import com.qyh.coderepository.menu.baidu.asr.AsrFragment;
import com.qyh.coderepository.menu.baidu.tts.TtsFragment;
import com.qyh.coderepository.menu.dagger.DaggerFragment;
import com.qyh.coderepository.menu.database.DBFragment;
import com.qyh.coderepository.menu.executor.ThreadExecutorFragment;
import com.qyh.coderepository.menu.killer.KillerFragment;
import com.qyh.coderepository.menu.mvp.MvpFragment;
import com.qyh.coderepository.menu.view.ViewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentStack fragmentStack;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
        initPermission();
    }

    private void initFragment() {
        fragmentStack = new FragmentStack(this, getSupportFragmentManager(), R.id.content_frame);
        fragmentStack.replace(new MainFragment());
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    /**
     * menu点击监听
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nav_db:
                /** 数据库操作 */
                fragmentStack.push(new DBFragment());
                break;
            case R.id.menu_nav_dagger2:
                /** 依赖注入 */
                fragmentStack.push(new DaggerFragment());
                break;
            case R.id.menu_nav_kill:
                /** 进程保活 */
                fragmentStack.push(new KillerFragment());
                break;
            case R.id.menu_nav_runnable:
                /** 任务队列 */
                fragmentStack.push(new ThreadExecutorFragment());
                break;
            case R.id.menu_nav_mvp:
                /** MVP */
                fragmentStack.push(new MvpFragment());
                break;
            case R.id.menu_nav_view:
                /** 自定义View */
                fragmentStack.push(new ViewFragment());
                break;
            case R.id.menu_nav_tts:
                /** 语音合成 */
                fragmentStack.push(new TtsFragment());
                break;
            case R.id.menu_nav_asr:
                /** 语音识别 */
                fragmentStack.push(new AsrFragment());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("==", "back fragment size : " + fragmentStack.size());
        if (!fragmentStack.pop()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void push(Fragment fragment) {
        fragmentStack.push(fragment);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LoggerUtil.d("内存等级: " + level);
    }

    private void initPermission() {
        String permissions[] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.RECORD_AUDIO
        };
        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }

}
