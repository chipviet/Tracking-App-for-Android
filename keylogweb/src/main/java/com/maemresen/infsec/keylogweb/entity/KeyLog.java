package com.maemresen.infsec.keylogweb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maemresen.infsec.keylogweb.model.KeyLogModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "keylogger")

public class KeyLog {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "key_log_date")
    private String keyLogDate;

    @Column(name = "accessibility_event")
    private String accessibilityEvent;

    @Column(name = "msg")
    private String msg;

    public KeyLog(){

    }

    public KeyLog(KeyLogModel keyLogModel) {
        this.uuid = keyLogModel.getUuid();
        this.keyLogDate = keyLogModel.getKeyLogDate();
        this.accessibilityEvent = keyLogModel.getAccessibilityEvent();
        this.msg = keyLogModel.getMsg();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKeyLogDate() {
        return keyLogDate;
    }

    public void setKeyLogDate(String keyLogDate) {
        this.keyLogDate = keyLogDate;
    }

    public String getAccessibilityEvent() {
        return accessibilityEvent;
    }

    public void setAccessibilityEvent(String accessibilityEvent) {
        this.accessibilityEvent = accessibilityEvent;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
