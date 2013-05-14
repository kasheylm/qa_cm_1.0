package com.playtech.cm.utils.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 09/10/12
 * Time: 09:42
 */
public class HibernateUtils {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getActivityStartDate(String activityID){
        List<Timestamp> timestamps = getJdbcTemplate().queryForList("select start_date from activity where activity_id=?", Timestamp.class, activityID);
        return timestamps.get(0).toString().substring(0, 16);
    }

    public String getActivityEndDate(String activityID){
        List<Timestamp> timestamps = getJdbcTemplate().queryForList("select end_date from activity where activity_id=?", Timestamp.class, activityID);
        return timestamps.get(0).toString().substring(0, 16);
    }

    public String getCampaignStatus(String campaignID){
        String status = getJdbcTemplate().queryForObject("select status_name from campaign where campaign_id=?", String.class, campaignID);
        return status;
    }
}
