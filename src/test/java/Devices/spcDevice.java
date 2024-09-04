package Devices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class spcDevice {
    private String deviceId;
    private String ipAddr;
    private String acVolt;
    private String status;
    private String pingSer;
    private String url;
    private String time;

    public spcDevice(String deviceId, String ipAddr, String acVolt, String status, String pingSer, String url, String time) {
        this.deviceId = deviceId;
        this.ipAddr = ipAddr;
        this.acVolt = acVolt;
        this.status = status;
        this.pingSer = pingSer;
        this.url = url;
        this.time = time;
    }

    public spcDevice() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getAcVolt() {
        return acVolt;
    }

    public void setAcVolt(String acVolt) {
        this.acVolt = acVolt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPingSer() {
        return pingSer;
    }

    public void setPingSer(String pingSer) {
        this.pingSer = pingSer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.time = sdf.format(new Date());
    }
}
