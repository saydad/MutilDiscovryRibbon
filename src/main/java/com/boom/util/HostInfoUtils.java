package com.boom.util;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 主机信息
 * @author liuyong
 */
public class HostInfoUtils {

    private static final String IP;
    private static final String HOST_NAME;

    public HostInfoUtils() {
    }

    public static String getHostIp() {
        return IP;
    }

    public static String getHostName() {
        return HOST_NAME;
    }

    static {
        try {
            InetAddress host = InetAddress.getLocalHost();
            HOST_NAME = host != null ? host.getHostName() : null;
            IP = InetAddress.getByName(HOST_NAME).getHostAddress();
        } catch (UnknownHostException var1) {
            throw new UncheckedIOException(var1.getMessage(), var1);
        }
    }
}
