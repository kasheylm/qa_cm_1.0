package com.playtech.cm.utils.entities.conditions;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:42 PM
 */
public class LapsedPlayerReportEntity extends CustomReportEntity {
    private String lastPlayedAgo = "";
    private String maxBalance = "";
    private String minBalance = "";
    private String minEarnings = "";
    private String maxEarnings = "";

    public  LapsedPlayerReportEntity() {
        setCustomReport("Lapsed player report");
        setLastPlayedAgo("5");
        setSummary("Lapsed player report");
    }

    public void setAllFields() {
        setCustomReport("Lapsed player report");
        setClientType("Admin");
        setClientPlatform("Flash");
        setLastPlayedAgo("5");
        setVipLevel("15");
        setMaxBalance("999");
        setMinBalance("1");
        setMinEarnings("1");
        setMaxEarnings("999");
        setSuspended(Filter.YES);
        setFrozen(Filter.YES);
        setSummary("Lapsed player report");
    }

    public void setAllFieldsForEdit() {
        setCustomReport("Lapsed player report");
        setClientType("Casino");
        setClientPlatform("Download");
        setLastPlayedAgo("13");
        setVipLevel("3");
        setMaxBalance("777");
        setMinBalance("6");
        setMinEarnings("56");
        setMaxEarnings("777");
        setSuspended(Filter.NO);
        setFrozen(Filter.NO);
        setSummary("Lapsed player report");
    }

    public String getLastPlayedAgo() {
        return lastPlayedAgo;
    }

    public void setLastPlayedAgo(String lastPlayedAgo) {
        this.lastPlayedAgo = lastPlayedAgo;
    }

    public String getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(String maxBalance) {
        this.maxBalance = maxBalance;
    }

    public String getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(String minBalance) {
        this.minBalance = minBalance;
    }

    public String getMinEarnings() {
        return minEarnings;
    }

    public void setMinEarnings(String minEarnings) {
        this.minEarnings = minEarnings;
    }

    public String getMaxEarnings() {
        return maxEarnings;
    }

    public void setMaxEarnings(String maxEarnings) {
        this.maxEarnings = maxEarnings;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (!(o instanceof LapsedPlayerReportEntity)) return false;

        LapsedPlayerReportEntity that = (LapsedPlayerReportEntity) o;

        if (lastPlayedAgo != null ? !lastPlayedAgo.equals(that.lastPlayedAgo) : that.lastPlayedAgo != null)
            return false;
        if (maxBalance != null ? !maxBalance.equals(that.maxBalance) : that.maxBalance != null) return false;
        if (maxEarnings != null ? !maxEarnings.equals(that.maxEarnings) : that.maxEarnings != null) return false;
        if (minBalance != null ? !minBalance.equals(that.minBalance) : that.minBalance != null) return false;
        if (minEarnings != null ? !minEarnings.equals(that.minEarnings) : that.minEarnings != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        super.hashCode();
        int result = lastPlayedAgo != null ? lastPlayedAgo.hashCode() : 0;
        result = 31 * result + (maxBalance != null ? maxBalance.hashCode() : 0);
        result = 31 * result + (minBalance != null ? minBalance.hashCode() : 0);
        result = 31 * result + (minEarnings != null ? minEarnings.hashCode() : 0);
        result = 31 * result + (maxEarnings != null ? maxEarnings.hashCode() : 0);
        return result;
    }
}
