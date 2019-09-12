package com.example.daotest.bean;

import java.util.List;

/**
 * @author tian on 2019/8/27
 */
public class OrderData  {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * attrClass : 5
         * attrGroupId : DT0000000275
         * attrGroupName : 个人信息
         * attrKeyName : PolicyHolderInfoTab
         * attrName : 购买人信息
         * classCode : 001015
         * customControlsName : IdCardRecognition
         * id : SX9999999610
         * inputMax : 30
         * inputMin : 0
         * isMust : false
         * valueType : 6
         * valueTypeString : Other
         * patternCondition :
         * attrValueList : ["身份证","统一社会信用代码"]
         * patternDesc : 车架号只能由数字字母组成
         */

        private int attrClass;
        private String attrGroupId;
        private String attrGroupName;
        private String attrKeyName;
        private String attrName;
        private String classCode;
        private String customControlsName;
        private String id;
        private int inputMax;
        private int inputMin;
        private boolean isMust;
        private int valueType;
        private String valueTypeString;
        private String patternCondition;
        private String patternDesc;
        private List<String> attrValueList;
        private boolean hidden;

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public int getAttrClass() {
            return attrClass;
        }

        public void setAttrClass(int attrClass) {
            this.attrClass = attrClass;
        }

        public String getAttrGroupId() {
            return attrGroupId;
        }

        public void setAttrGroupId(String attrGroupId) {
            this.attrGroupId = attrGroupId;
        }

        public String getAttrGroupName() {
            return attrGroupName;
        }

        public void setAttrGroupName(String attrGroupName) {
            this.attrGroupName = attrGroupName;
        }

        public String getAttrKeyName() {
            return attrKeyName;
        }

        public void setAttrKeyName(String attrKeyName) {
            this.attrKeyName = attrKeyName;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getClassCode() {
            return classCode;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
        }

        public String getCustomControlsName() {
            return customControlsName;
        }

        public void setCustomControlsName(String customControlsName) {
            this.customControlsName = customControlsName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getInputMax() {
            return inputMax;
        }

        public void setInputMax(int inputMax) {
            this.inputMax = inputMax;
        }

        public int getInputMin() {
            return inputMin;
        }

        public void setInputMin(int inputMin) {
            this.inputMin = inputMin;
        }

        public boolean isIsMust() {
            return isMust;
        }

        public void setIsMust(boolean isMust) {
            this.isMust = isMust;
        }

        public int getValueType() {
            return valueType;
        }

        public void setValueType(int valueType) {
            this.valueType = valueType;
        }

        public String getValueTypeString() {
            return valueTypeString;
        }

        public void setValueTypeString(String valueTypeString) {
            this.valueTypeString = valueTypeString;
        }

        public String getPatternCondition() {
            return patternCondition;
        }

        public void setPatternCondition(String patternCondition) {
            this.patternCondition = patternCondition;
        }

        public String getPatternDesc() {
            return patternDesc;
        }

        public void setPatternDesc(String patternDesc) {
            this.patternDesc = patternDesc;
        }

        public List<String> getAttrValueList() {
            return attrValueList;
        }

        public void setAttrValueList(List<String> attrValueList) {
            this.attrValueList = attrValueList;
        }
    }
}
