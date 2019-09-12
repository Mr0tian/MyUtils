package com.example.daotest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author tian on 2019/8/26
 */
@Entity
public class TestBean  {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "sex")
    private String sex;

    @Property(nameInDb = "name")
    private String name;

    @Transient
    private String age;

    @Generated(hash = 1313479120)
    public TestBean(Long id, String sex, String name) {
        this.id = id;
        this.sex = sex;
        this.name = name;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
