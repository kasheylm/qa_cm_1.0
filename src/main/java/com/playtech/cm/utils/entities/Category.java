package com.playtech.cm.utils.entities;

/**
 * User: Denis Veselovskiy
 * Date: 5/16/12
 * Time: 11:52 AM
 */
public enum Category {
    ACQUISITION ("Acquisition"),
    CONVERSION("Conversion"),
    CROSS_MARKETING("Cross Marketing"),
    REACTIVATION("Reactivation"),
    RETENTION("Retention");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
