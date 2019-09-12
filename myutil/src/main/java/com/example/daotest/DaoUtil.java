package com.example.daotest;


import com.example.daotest.bean.TestBean;
import com.example.daotest.bean.UserBean;
import com.example.daotest.dao.TestBeanDao;
import com.example.daotest.dao.UserBeanDao;

import java.util.List;

/**
 * @author tian on 2019/8/26
 */
public class DaoUtil {

    public TestBeanDao getTestBeanDao(){
        return GreenDaoManager.getInstance().getmDaoSession().getTestBeanDao();
    }

    public TestBean getTestBean(Long id){
        return getTestBeanDao().load(id);
    }

    public List<TestBean> getAllTestBean(){
        return getTestBeanDao().loadAll();
    }

    public UserBeanDao getUserBeanDao(){
        return GreenDaoManager.getInstance().getmDaoSession().getUserBeanDao();
    }
    public UserBean getUserBean(long id){
        return getUserBeanDao().load(id);
    }

}
