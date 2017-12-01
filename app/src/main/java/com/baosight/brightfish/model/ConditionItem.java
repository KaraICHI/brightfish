package com.baosight.brightfish.model;


public class ConditionItem {
    private String conditionName;
    private String conditionContent;

    public ConditionItem(String conditionName,String conditionContent){
        this.conditionName=conditionName;
        this.conditionContent=conditionContent;
    }
    public String getConditionContent() {
        return conditionContent;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionContent(String conditionContent) {
        this.conditionContent = conditionContent;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}
