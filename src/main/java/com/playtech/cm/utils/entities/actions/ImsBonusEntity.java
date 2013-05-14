package com.playtech.cm.utils.entities.actions;

import java.util.Arrays;
import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 3:06 PM
 */
public class ImsBonusEntity {
    private String action;
    private List<String> availableTemplates;
    private String searchFieldText;
    private String sourceForBonusCalcAmount;
    private String selectedTemplateName;

    public String getSelectedTemplateName() {
        return selectedTemplateName;
    }

    public void setSelectedTemplateName(String selectedTemplateName) {
        this.selectedTemplateName = selectedTemplateName;
    }

    public List<String> getAvailableTemplates() {
        return availableTemplates;
    }

    public void setAvailableTemplates(List<String> availableTemplates) {
        this.availableTemplates = availableTemplates;
    }

    public String getSearchFieldText() {
        return searchFieldText;
    }

    public void setSearchFieldText(String searchFieldText) {
        this.searchFieldText = searchFieldText;
    }

    public String getSourceForBonusCalcAmount() {
        return sourceForBonusCalcAmount;
    }

    public void setSourceForBonusCalcAmount(String sourceForBonusCalcAmount) {
        this.sourceForBonusCalcAmount = sourceForBonusCalcAmount;
    }

    public ImsBonusEntity() {
        this.action = "IMS Bonus";
        this.setSearchFieldText("");
        this.setSourceForBonusCalcAmount("No amount required");
        this.setAvailableTemplates(Arrays.asList("CM Test Bonus 2 - 50%"));
        this.setSelectedTemplateName(null);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ImsBonusEntity setThisToDefaultEntity(){
        this.setAvailableTemplates(Arrays.asList("CM Test Bonus 2 - 50%"));
        this.setSearchFieldText("");
        this.setSourceForBonusCalcAmount("No amount required");
        this.setSelectedTemplateName("");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImsBonusEntity)) return false;

        ImsBonusEntity that = (ImsBonusEntity) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (availableTemplates != null ? !availableTemplates.equals(that.availableTemplates) : that.availableTemplates != null)
            return false;
        if (searchFieldText != null ? !searchFieldText.equals(that.searchFieldText) : that.searchFieldText != null)
            return false;
        if (selectedTemplateName != null ? !selectedTemplateName.equals(that.selectedTemplateName) : that.selectedTemplateName != null)
            return false;
        if (sourceForBonusCalcAmount != null ? !sourceForBonusCalcAmount.equals(that.sourceForBonusCalcAmount) : that.sourceForBonusCalcAmount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (availableTemplates != null ? availableTemplates.hashCode() : 0);
        result = 31 * result + (searchFieldText != null ? searchFieldText.hashCode() : 0);
        result = 31 * result + (sourceForBonusCalcAmount != null ? sourceForBonusCalcAmount.hashCode() : 0);
        result = 31 * result + (selectedTemplateName != null ? selectedTemplateName.hashCode() : 0);
        return result;
    }
}
