package com.playtech.cm.utils.entities.conditions;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:20 PM
 */
public class ConditionEntity {
    protected String conditionToAdd = "";
    protected String condition = "";

    public ConditionEntity(){
        conditionToAdd = "Eligibility List";
        condition = "CUSTOM_REPORT";
    }

    public String getConditionToAdd() {
        return conditionToAdd;
    }

    public void setConditionToAdd(String conditionToAdd) {
        this.conditionToAdd = conditionToAdd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionEntity that = (ConditionEntity) o;

        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;
        if (conditionToAdd != null ? !conditionToAdd.equals(that.conditionToAdd) : that.conditionToAdd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = conditionToAdd != null ? conditionToAdd.hashCode() : 0;
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        return result;
    }
}
