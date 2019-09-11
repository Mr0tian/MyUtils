package com.example.daotest.bean;

/**
 * @author tian on 2019/9/3
 * 用来存放列表数据的bean类
 */
public class ListDataBean  {

    /**
     * 自身id,用uuid代替
     */
    private String id;
    /**
     * 父类id
     */
    private String parentId;
    /**
     * 级别 分属于几级列表,回传数据时使用
     */
    private String rank;

    /**
     * 类型,在列表展示时使用
     */
    private String type;

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数值
     */
    private String value;

    /**
     * 传递回去的参数值
     */
    private String responseValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * 是否显示的状态
     */
    private boolean show;


}
