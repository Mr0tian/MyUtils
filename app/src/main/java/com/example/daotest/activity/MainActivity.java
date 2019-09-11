package com.example.daotest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.DaoUtil;
import com.example.daotest.R;
import com.example.daotest.bean.TestBean;
import com.example.daotest.dao.TestBeanDao;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.edit_text)
    EditText editText;
    private TestBeanDao testBeanDao;
    private DaoUtil daoUtil = new DaoUtil();

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        testBeanDao = daoUtil.getTestBeanDao();
    }

    @OnClick({R.id.btn_save, R.id.btn_upload, R.id.btn_delete, R.id.btn_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String name = editText.getText().toString();
                TestBean testBean = new TestBean();
                testBean.setName(name);
                id = testBeanDao.insert(testBean);
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_upload:
                TestBean testBean1 = new TestBean();
                testBean1.setName(editText.getText().toString());
                testBean1.setId(id);
                testBeanDao.update(testBean1);
                Toast.makeText(getApplicationContext(),"更新成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                testBeanDao.deleteByKey(id);
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_find:
                TestBean testBean2 = testBeanDao.load(id);

                if (testBean2 != null){
                    Toast.makeText(getApplicationContext(),testBean2.getName(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"数据库中无此数据",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
