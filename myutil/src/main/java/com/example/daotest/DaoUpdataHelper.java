package com.example.daotest;

import android.content.Context;

import com.example.daotest.dao.DaoMaster;
import com.example.daotest.dao.TestBeanDao;
import com.example.daotest.dao.UserBeanDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @author tian on 2019/8/26
 */
public class DaoUpdataHelper extends DaoMaster.OpenHelper {

    public DaoUpdataHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db,ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db,ifExists);
            }
        }, TestBeanDao.class, UserBeanDao.class);
    }
}
