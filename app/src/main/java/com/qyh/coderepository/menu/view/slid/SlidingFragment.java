package com.qyh.coderepository.menu.view.slid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qyh.coderepository.R;
import com.qyh.coderepository.entity.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/2/5  22:58
 * @desc 仿QQ侧滑菜单
 */

public class SlidingFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.slid_menu)
    SlidingMenu slidMenu;
    Unbinder unbinder;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private List<Menu> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        initListener();
        return view;
    }

    private void initData() {
        list.clear();
        list.add(new Menu(R.mipmap.img_1, "亚马逊"));
        list.add(new Menu(R.mipmap.img_2, "计算机"));
        list.add(new Menu(R.mipmap.img_3, "日历"));
        list.add(new Menu(R.mipmap.img_4, "股票"));
        list.add(new Menu(R.mipmap.img_5, "理财"));
    }

    private void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BaseQuickAdapter<Menu, BaseViewHolder> adapter = new BaseQuickAdapter<Menu, BaseViewHolder>(R.layout.view_menu, list) {

            @Override
            protected void convert(BaseViewHolder helper, Menu item) {
                helper.setImageResource(R.id.iv_icon, item.getImgSrc())
                        .setText(R.id.tv_content, item.getContent());
            }
        };
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                slidMenu.closeMenu();
            }
        });
    }

    private void initListener() {
        rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidMenu.closeMenu();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
