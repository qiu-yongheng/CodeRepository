package com.qyh.coderepository.menu.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qyh.coderepository.R;
import com.qyh.coderepository.menu.database.manager.DbHelper;
import com.qyh.coderepository.entity.Student;
import com.qyh.coderepository.entity.StudentDao;
import com.kc.common.util.log.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/9  9:56
 * @desc ${TODD}
 */

public class DBFragment extends Fragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.btn_insert)
    Button btnInsert;
    @BindView(R.id.btn_insert_list)
    Button btnInsertList;
    @BindView(R.id.btn_query_all)
    Button btnQueryAll;
    @BindView(R.id.btn_query_where)
    Button btnQueryWhere;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_delete_all)
    Button btnDeleteAll;
    private ArrayList<Student> list;
    private BaseQuickAdapter<Student, BaseViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new Student(null, "001", "张三", "男", "88"));
        list.add(new Student(null, "002", "李四", "男", "45"));
        list.add(new Student(null, "003", "王五", "女", "23"));
        list.add(new Student(null, "004", "赵六", "女", "69"));
        list.add(new Student(null, "005", "刘七", "男", "97"));
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnInsert.setOnClickListener(this);
        btnInsertList.setOnClickListener(this);
        btnQueryAll.setOnClickListener(this);
        btnQueryWhere.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDeleteAll.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                Random random = new Random();
                // [0, 5)之间的int整数
                Student student = list.get(random.nextInt(5));
                final boolean insert = DbHelper.getInstance().student().insert(student);
                LoggerUtil.d(insert);
                break;
            case R.id.btn_insert_list:
                boolean insertInTx = DbHelper.getInstance().student().insertInTx(list);
                LoggerUtil.d(insertInTx);
                break;
            case R.id.btn_query_all:
                List<Student> students = DbHelper.getInstance().student().queryBuilder().list();
                adapter = new BaseQuickAdapter<Student, BaseViewHolder>(R.layout.item_student, students) {
                    @Override
                    protected void convert(BaseViewHolder helper, Student item) {
                        helper.setText(R.id.tv_num, item.getStuNo())
                                .setText(R.id.tv_name, item.getStuName())
                                .setText(R.id.tv_sex, item.getStuSex())
                                .setText(R.id.tv_score, item.getStuScore());
                    }
                };

                recyclerView.setAdapter(adapter);
                break;
            case R.id.btn_query_where:
                List<Student> list = DbHelper.getInstance().student().queryBuilder().where(StudentDao.Properties.StuScore.le("80")).orderAsc(StudentDao.Properties.StuScore).limit(3).list();
                adapter = new BaseQuickAdapter<Student, BaseViewHolder>(R.layout.item_student, list) {
                    @Override
                    protected void convert(BaseViewHolder helper, Student item) {
                        helper.setText(R.id.tv_num, item.getStuNo())
                                .setText(R.id.tv_name, item.getStuName())
                                .setText(R.id.tv_sex, item.getStuSex())
                                .setText(R.id.tv_score, item.getStuScore());
                    }
                };

                recyclerView.setAdapter(adapter);
                break;
            case R.id.btn_delete:
                DbHelper.getInstance().student().queryBuilder().where(StudentDao.Properties.StuName.eq("张三")).buildDelete().executeDeleteWithoutDetachingEntities();
                adapter = new BaseQuickAdapter<Student, BaseViewHolder>(R.layout.item_student, DbHelper.getInstance().student().queryBuilder().list()) {
                    @Override
                    protected void convert(BaseViewHolder helper, Student item) {
                        helper.setText(R.id.tv_num, item.getStuNo())
                                .setText(R.id.tv_name, item.getStuName())
                                .setText(R.id.tv_sex, item.getStuSex())
                                .setText(R.id.tv_score, item.getStuScore());
                    }
                };

                recyclerView.setAdapter(adapter);
                break;
            case R.id.btn_delete_all:
                boolean b = DbHelper.getInstance().student().deleteAll();
                LoggerUtil.d(b);

                adapter = new BaseQuickAdapter<Student, BaseViewHolder>(R.layout.item_student, DbHelper.getInstance().student().queryBuilder().list()) {
                    @Override
                    protected void convert(BaseViewHolder helper, Student item) {
                        helper.setText(R.id.tv_num, item.getStuNo())
                                .setText(R.id.tv_name, item.getStuName())
                                .setText(R.id.tv_sex, item.getStuSex())
                                .setText(R.id.tv_score, item.getStuScore());
                    }
                };

                recyclerView.setAdapter(adapter);
                break;
            case R.id.btn_update:
                Student lisi = DbHelper.getInstance().student().queryBuilder().where(StudentDao.Properties.StuName.eq("李四")).build().unique();
                if (lisi != null) {
                    lisi.setStuScore("100");
                    DbHelper.getInstance().student().update(lisi);
                }
                Toast.makeText(getContext(), "更新成功~", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
