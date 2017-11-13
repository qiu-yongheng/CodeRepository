package com.qyh.coderepository.database.manager;

import android.content.Context;

import com.qyh.coderepository.entity.DaoMaster;
import com.qyh.coderepository.entity.DaoSession;
import com.qyh.coderepository.entity.Student;

import org.greenrobot.greendao.AbstractDao;

/**
 * @author 邱永恒
 * @time 2017/11/10  9:16
 * @desc ${TODD}
 */

public class DbHelper {
    private static final String DB_NAME = "CodeRepository.db";//数据库名称
    private static DbHelper instance;
    private static DBManager<Student, Long> student;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DbHelper() {

    }

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public void init(Context context, String dbName) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取AuthorModel DAO进行数据库CRUD操作
     * @return
     */
    public DBManager<Student, Long> student() {
        if (student == null) {
            student = new DBManager<Student, Long>(){
                @Override
                public AbstractDao<Student, Long> getAbstractDao() {
                    return daoSession.getStudentDao();
                }
            };
        }
        return student;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void clear() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public void close() {
        clear();
        if (devOpenHelper != null) {
            devOpenHelper.close();
            devOpenHelper = null;
        }
    }
}
