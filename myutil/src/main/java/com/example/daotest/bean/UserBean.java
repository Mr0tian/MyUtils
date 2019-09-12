package com.example.daotest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author tian on 2019/8/26
 */
@Entity
public class UserBean  {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "password")
    private String password;

    @Generated(hash = 1449433953)
    public UserBean(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

}
