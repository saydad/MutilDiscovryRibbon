package com.boom.util;

import java.util.Objects;
import java.util.Optional;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 配置读取
 * @author liuyong
 */
public class PropertyUtil {

    private static ConfigurableEnvironment env;

    public static void setEnv(ConfigurableEnvironment env) {
        PropertyUtil.env = env;
    }

    /**
     * 从配置文件中获取指定key的value
     * @param key 配置项
     * @return 配置项-value
     */
    public static String getProperty(String key) {
        Optional<Object> res = env.getPropertySources().stream()
                .map(item -> item.getProperty(key))
                .filter(Objects::nonNull).findFirst();
        if (res.isPresent()) {
            return String.valueOf(res.get());
        }
        return "";
    }
}
