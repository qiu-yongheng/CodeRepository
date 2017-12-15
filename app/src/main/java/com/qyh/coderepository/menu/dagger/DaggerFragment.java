package com.qyh.coderepository.menu.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qyh.coderepository.MainActivity;
import com.qyh.coderepository.R;
import com.qyh.coderepository.app.App;
import com.qyh.coderepository.menu.dagger.component.DaggerComponent;
import com.qyh.coderepository.menu.dagger.component.DaggerDaggerComponent;
import com.qyh.coderepository.menu.dagger.data.Cloth;
import com.qyh.coderepository.menu.dagger.data.ClothHandler;
import com.qyh.coderepository.menu.dagger.data.Clothes;
import com.qyh.coderepository.menu.dagger.data.Shoe;
import com.qyh.coderepository.menu.dagger.module.DaggerModule;
import com.kc.common.util.log.LoggerUtil;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/13  9:07
 * @desc ${TODD}
 */

public class DaggerFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btn_sample)
    Button btnSample;
    Unbinder unbinder;
    @BindView(R.id.btn_inject)
    Button btnInject;
    @Inject
    Cloth cloth;
    @Inject
    Shoe shoe;
    @Inject
    Clothes clothes;
    @Inject
    @Named("red")
    Cloth redCloth;
    @Inject
    @Named("blue")
    Cloth blueCloth;
    @Inject
    ClothHandler clothHandler;


    @BindView(R.id.btn_clothes)
    Button btnClothes;
    @BindView(R.id.btn_named)
    Button btnNamed;
    @BindView(R.id.btn_singleton)
    Button btnSingleton;
    @BindView(R.id.btn_dependencies)
    Button btnDependencies;
    @BindView(R.id.btn_subcomponent)
    Button btnSubcomponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dagger, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        inject();
    }

    private void init() {
        btnSample.setOnClickListener(this);
        btnInject.setOnClickListener(this);
        btnClothes.setOnClickListener(this);
        btnNamed.setOnClickListener(this);
        btnSingleton.setOnClickListener(this);
        btnDependencies.setOnClickListener(this);
        btnSubcomponent.setOnClickListener(this);
    }

    private void inject() {
        DaggerComponent build = DaggerDaggerComponent.builder()
                .baseComponent(((App) getActivity().getApplication()).getBaseComponent())
                .daggerModule(new DaggerModule()).build();
        build.inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sample:
                /** 使用module进行注入 */
                LoggerUtil.d("我现在有" + cloth);
                break;
            case R.id.btn_inject:
                /** 使用注解进行注入 */
                LoggerUtil.d("我现在有" + cloth + "和" + shoe);
                break;
            case R.id.btn_clothes:
                /** 创建依赖类clothes */
                LoggerUtil.d("我现在有" + cloth + "和" + shoe + "和" + clothes);
                break;
            case R.id.btn_named:
                /** @Named注解的使用 */
                LoggerUtil.d("我现在有" + redCloth + "和" + blueCloth);
                break;
            case R.id.btn_singleton:
                /** @Singleton注解的使用 */
                LoggerUtil.d("cloth=clothes中的cloth吗?:" + (cloth == clothes.getCloth()));
                break;
            case R.id.btn_dependencies:
                /** 组件依赖dependencies的使用 */
                LoggerUtil.d("红布料加工后变成了" + clothHandler.handle(redCloth) + "\nclothHandler地址:" + clothHandler);

                ((MainActivity) getActivity()).push(new SecondFragment());
                break;
            case R.id.btn_subcomponent:
                /** 组件依赖@Subcomponent的使用 */
                LoggerUtil.d("红布料加工后变成了" + clothHandler.handle(redCloth) + "\nclothHandler地址:" + clothHandler);

                ((MainActivity) getActivity()).push(new SubSecondFragment());
                break;
        }
    }
}
