package com.maemresen.infsec.keylogapp.model;

import com.maemresen.infsec.keylogapp.Helper;
import com.maemresen.infsec.keylogapp.util.DateTimeHelper;

import java.util.Date;

public class KeyLog {

    private String uuid;
    private String keyLogDate;
    private String accessibilityEvent;
    private String msg;

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
