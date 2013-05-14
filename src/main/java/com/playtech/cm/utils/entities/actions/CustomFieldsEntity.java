package com.playtech.cm.utils.entities.actions;

/**
 * User: Denis Veselovskiy
 * Date: 11/15/12 * Time: 6:02 PM
 */
public class CustomFieldsEntity {
    private String action;
    private String name;
    private String value;

    public CustomFieldsEntity() {
        this.action = "Update Player Details";
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public void setDefaultCustomField() {
        setName("Custom01 (TEXT)");
        setValue("777");
    }
}
