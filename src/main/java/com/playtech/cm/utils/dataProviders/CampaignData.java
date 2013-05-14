package com.playtech.cm.utils.dataProviders;


import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.entities.Category;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.DataProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 03/05/12
 * Time: 11:13
 */
public class CampaignData extends BaseData {

    private String name;
    private String description;
    private String casinoBrand;
    private boolean ongoing;
    private String startDate;
    private String publicationDate;
    private String endDate;
    private String tags;
    private String links;
    public String category;
    String tmp = Config.getNow();



    public CampaignData() {
        this.name = "Campaign_" + tmp;
        this.description="Description" + tmp;
        this.casinoBrand = "playtech81004";
        setCategory(Category.REACTIVATION);
        this.startDate = Config.getDate();
        this.publicationDate = Config.getDate();
        this.ongoing = false;
        this.endDate = Config.getFloatingFutureDate(13,"yyyy-MM-dd HH:mm");
        this.tags = "tags" + tmp;
        this.links = "links" + tmp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(Category c) {
        this.category = c.getCategory();
    }

    public boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCasinoBrand() {
        return casinoBrand;
    }

    public void setCasinoBrand(String casinoBrand) {
        this.casinoBrand = casinoBrand;
    }

    @DataProvider
    public static Object[][] validateFieldBase() {
        Object[][] retkeyword = {
            {"<script language='JavaScript'> alert('Добрый день') </script>"},
            {"ASDGFFLKLKLKlddvddsghdsh"},
            {"12344557688008853"},
            {":!@#$%^&*()_."},
            {"ASDASDasdas-123123211212&^%&^%"}
        };
        return(retkeyword);
    }

    @DataProvider
    public static Object[][] validateFieldName() {
        return ArrayUtils.add(validateFieldBase(), new Object[]{generateText(199)});
    }

    @DataProvider
    public static Object[][] validateLongNameWarn() {
        Object[][] name = {   new Object[]{generateText(250)} };
        return(name);
    }

    @DataProvider
    public static Object[][] validateFieldDescription() {
        return ArrayUtils.add(validateFieldBase(), new Object[]{generateText(4000)});
    }

    @DataProvider
    public static Object[][] validateFieldTags() {
        return ArrayUtils.addAll(validateFieldBase(), new Object[]{generateText(100)},
                new Object[]{"tag1, tag2, tag3"},
                new Object[]{"tag1; tag2; tag3"},
                new Object[]{"tag1, tag2, tag3, tag4;tag5;tag6"}
        );
    }

    @DataProvider
    public static Object[][] validateFieldLinks() {
        return ArrayUtils.addAll(validateFieldBase(), new Object[]{generateText(4000)},
                new Object[]{"Http://google.com"});
    }

}
