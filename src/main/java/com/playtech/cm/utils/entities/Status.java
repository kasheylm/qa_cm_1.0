package com.playtech.cm.utils.entities;

/**
 * User: Denis Veselovskiy
 * Date: 5/18/12
 * Time: 3:23 PM
 */
public enum Status {
    ACTIVE ("Active"),
    APPROVED("Approved"),
    COMPLETED("Completed"),
    DISABLED("Disabled"),
    IN_DESIGN("In Design"),
    PAUSED ("Paused"),
    PENDING_APPROVAL("Pending Approval");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
