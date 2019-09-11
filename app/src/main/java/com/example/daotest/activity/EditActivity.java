package com.example.daotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.DaoUtil;
import com.example.daotest.R;
import com.example.daotest.bean.UserBean;
import com.example.daotest.dao.UserBeanDao;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author tian on 2019/8/26
 */
public class EditActivity extends AppCompatActivity {


    @InjectView(R.id.user_name)
    EditText userName;
    @InjectView(R.id.user_password)
    EditText userPassword;

    private UserBeanDao userBeanDao;
    private DaoUtil daoUtil = new DaoUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        userBeanDao = daoUtil.getUserBeanDao();
    }

    @OnClick({R.id.btn_save, R.id.btn_see})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:

                UserBean userBean = new UserBean();
                userBean.setId(10086);
                userBean.setName(userName.getText().toString());
                userBean.setPassword(userPassword.getText().toString());
                userBeanDao.insert(userBean);

                break;
            case R.id.btn_see:
                Intent intent = new Intent(this,ShowActivity.class);
                startActivity(intent);
                break;
        }
    }
}
