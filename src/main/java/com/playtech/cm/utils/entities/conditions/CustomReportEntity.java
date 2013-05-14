package com.playtech.cm.utils.entities.conditions;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:32 PM
 */
public class CustomReportEntity extends ConditionEntity {
    private String reportCategory = "";
    private String customReport = "";

    private String clientType = "";
    private String clientPlatform = "";
    private String vipLevel = "";
    private Filter suspended = Filter.ALL;
    private Filter frozen = Filter.ALL;
    private String summary = "";

    public CustomReportEntity() {
        reportCategory = "Players";
        clientType = "All";
        clientPlatform = "All";
    }

    public String getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(String reportType) {
        this.reportCategory = reportType;
    }

    public String getCustomReport() {
        return customReport;
    }

    public void setCustomReport(String customReport) {
        this.customReport = customReport;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientPlatform() {
        return clientPlatform;
    }

    public void setClientPlatform(String clientPlatform) {
        this.clientPlatform = clientPlatform;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Filter getSuspended() {
        return suspended;
    }

    public void setSuspended(Filter suspended) {
        this.suspended = suspended;
    }

    public Filter getFrozen() {
        return frozen;
    }

    public void setFrozen(Filter frozen) {
        this.frozen = frozen;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomReportEntity that = (CustomReportEntity) o;

        if (clientPlatform != null ? !clientPlatform.equals(that.clientPlatform) : that.clientPlatform != null)
            return false;
        if (clientType != null ? !clientType.equals(that.clientType) : that.clientType != null) return false;
        if (customReport != null ? !customReport.equals(that.customReport) : that.customReport != null) return false;
        if (frozen != that.frozen) return false;
        if (reportCategory != null ? !reportCategory.equals(that.reportCategory) : that.reportCategory != null)
            return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (suspended != that.suspended) return false;
        if (vipLevel != null ? !vipLevel.equals(that.vipLevel) : that.vipLevel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        super.hashCode();
        int result = reportCategory != null ? reportCategory.hashCode() : 0;
        result = 31 * result + (customReport != null ? customReport.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        result = 31 * result + (clientPlatform != null ? clientPlatform.hashCode() : 0);
        result = 31 * result + (vipLevel != null ? vipLevel.hashCode() : 0);
        result = 31 * result + (suspended != null ? suspended.hashCode() : 0);
        result = 31 * result + (frozen != null ? frozen.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}
