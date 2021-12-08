package com.boom.discovry.properties;

/**
 * 注册本机的基本信息
 * @author liuyong
 */
public class MetaDataProperties {

    private String applicationName;

    private String ip;

    private Integer port;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
