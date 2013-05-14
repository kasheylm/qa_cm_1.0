package com.playtech.cm.utils.entities.conditions;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:28 PM
 */
public class UploadCSVEntity extends ConditionEntity {

    private String name;
    private String summary;
    private boolean includeHeaders = false;
    private String file = "";

    public UploadCSVEntity(){
        conditionToAdd = "Eligibility List";
        condition = "CSV_FILE";
        file = "withoutHeaders.csv";
        name = "Eligibility List - CSV File";
        summary = file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
        summary = file;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UploadCSVEntity that = (UploadCSVEntity) o;

        if (includeHeaders != that.includeHeaders) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (includeHeaders ? 1 : 0);
        return result;
    }
}
