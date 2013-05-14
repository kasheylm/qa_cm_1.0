package com.playtech.cm.utils.db;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 04/10/12
 * Time: 09:03
 */
public class DbSpringUtils {
    private JdbcTemplate jdbcTemplate;

    public DbSpringUtils(ApplicationContext applicationContext) {
        this.jdbcTemplate = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public String getActivityStartDate(String activityID){
        List<Timestamp> timestamps = getJdbcTemplate().queryForList("select start_date from activity where activity_id=?",Timestamp.class,activityID);
        return timestamps.get(0).toString().substring(0, 16);
    }

    public String getActivityEndDate(String activityID){
        List<Timestamp> timestamps = getJdbcTemplate().queryForList("select end_date from activity where activity_id=?",Timestamp.class,activityID);
        return timestamps.get(0).toString().substring(0, 16);
    }

}
