package com.qyh.coderepository.menu.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qyh.coderepository.R;
import com.qyh.coderepository.app.App;
import com.qyh.coderepository.menu.dagger.component.DaggerSecondComponent;
import com.qyh.coderepository.menu.dagger.data.Cloth;
import com.qyh.coderepository.menu.dagger.data.ClothHandler;
import com.qyh.coderepository.menu.dagger.module.SecondModule;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/15  11:13
 * @desc ${TODD}
 */

public class SecondFragment extends Fragment {
    @Inject
    ClothHandler clothHandler;
    @Inject
    @Named("blue")
    Cloth blueCloth;


    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dagger_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject();
        tv.setText("蓝布料加工后变成了" + clothHandler.handle(blueCloth) + "\nclothHandler地址:" + clothHandler);
    }

    private void inject() {
        DaggerSecondComponent.builder()
                .baseComponent(((App)getActivity().getApplication()).getBaseComponent())
                .secondModule(new SecondModule()).build().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
