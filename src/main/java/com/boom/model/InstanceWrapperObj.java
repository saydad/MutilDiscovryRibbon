package com.boom.model;

/**
 * 实例信息包装类
 * 屏蔽不同client返回的实例信息类型不一致
 * @author liuyong
 */
public class InstanceWrapperObj {

    private String ip;

    private Integer port;

    public InstanceWrapperObj() {
    }

    public InstanceWrapperObj(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
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
