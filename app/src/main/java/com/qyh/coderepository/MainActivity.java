package com.qyh.coderepository;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.qyh.coderepository.menu.dagger.DaggerFragment;
import com.qyh.coderepository.menu.database.DBFragment;
import com.qyh.coderepository.menu.executor.ThreadExecutorFragment;
import com.qyh.coderepository.menu.killer.KillerFragment;
import com.kc.common.util.fragment.FragmentStack;
import com.qyh.coderepository.menu.mvp.MvpFragment;
import com.kc.common.util.log.LoggerUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
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
                fragmentStack.replace(new DBFragment());
                break;
            case R.id.menu_nav_dagger2:
                /** 依赖注入 */
                fragmentStack.replace(new DaggerFragment());
                break;
            case R.id.menu_nav_kill:
                /** 进程保活 */
                fragmentStack.replace(new KillerFragment());
                break;
            case R.id.menu_nav_runnable:
                /** 任务队列 */
                fragmentStack.replace(new ThreadExecutorFragment());
                break;
            case R.id.menu_nav_mvp:
                /** MVP */
                fragmentStack.replace(new MvpFragment());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("==", "back fragment size : " + fragmentStack.size());
        if (!fragmentStack.pop()) {
            super.onBackPressed();
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
}
