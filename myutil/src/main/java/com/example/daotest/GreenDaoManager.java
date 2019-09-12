package com.example.daotest;

import com.example.daotest.dao.DaoMaster;
import com.example.daotest.dao.DaoSession;
import com.example.daotest.dao.TestBeanDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

/**
 * @author tian on 2019/8/26
 */
public class GreenDaoManager  {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager(){
        init();
    }

    /**
     * 静态内部类,实例化对象使用
     */
    private static class SingleInstanceHolder{
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    /**
     * 对外唯一实例的接口
     * @return
     */
    public static GreenDaoManager getInstance(){
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init() {


        //GreenDao 升级更新初始化
        //如果你想查看日志信息，请将DEBUG设置为true
        MigrationHelper.DEBUG = true;
        DaoUpdataHelper helper = new DaoUpdataHelper(App.getmAppContext(),"test_guide");

        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getmDaoMaster(){
        return mDaoMaster;
    }
    public DaoSession getmDaoSession(){
        return mDaoSession;
    }
    public DaoSession getNewSession(){
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

}
