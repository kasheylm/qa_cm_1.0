package com.playtech.cm.event;

import java.util.Date;
import java.util.Map;

/**
 * User: Denis Veselovskiy
 * Date: 6/13/12
 * Time: 3:32 PM
 */
public class Event {

    private String casinoId;
    private String eventType;
    private String eventId;
    private Date eventTimeStamp;
    private Map<String, ?> payload;
    private String playerLoginName;
    private Platform platform;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform =  platform ;
    }

    public String getCasinoId() {
        return casinoId;
    }

    public void setCasinoId(String casinoId) {
        this.casinoId = casinoId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(Date eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    public Map<String, ?> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, ?> payload) {
        this.payload = payload;
    }

    public String getPlayerLoginName() {
        return playerLoginName;
    }

    public void setPlayerLoginName(String playerLoginName) {
        this.playerLoginName = playerLoginName;
    }

}
