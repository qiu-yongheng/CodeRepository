package com.qyh.coderepository.menu.generic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.generic.entity.Child;
import com.qyh.coderepository.menu.generic.entity.Human;
import com.qyh.coderepository.menu.generic.entity.Man;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/3/14  13:37
 * @desc ${TODD}
 */

public class GenericFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Human> humans = new ArrayList<>();
        ArrayList<Man> men = new ArrayList<>();
        ArrayList<Child> children = new ArrayList<>();

        addExtend(children);
        addSuper(humans);
    }

    public void addExtend(List<? extends Man> list) {

    }

    public void addSuper(List<? super Man> list) {

    }
}
