package com.boom.discovry;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import com.boom.MultiDiscoveryManager;
import com.boom.discovry.properties.MetaDataProperties;
import com.boom.util.HostInfoUtils;
import com.boom.util.PropertyUtil;

/**
 * 多注册中心client，自启动配置
 * @author liuyong
 */
@ConditionalOnProperty(value = "discovery.multi.enabled", matchIfMissing = false)
public class MultiDiscoveryAutoConfiguration {

    private final ConfigurableEnvironment env;

    public MultiDiscoveryAutoConfiguration(ConfigurableEnvironment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        PropertyUtil.setEnv(env);
    }

    @Bean
    public MetaDataProperties metaDataProperties() {
        MetaDataProperties properties = new MetaDataProperties();
        String applicationName = PropertyUtil.getProperty("spring.application.name");
        if (applicationName ==  null || applicationName.length() <= 0) {
            throw new IllegalArgumentException("缺失spring.application.name配置");
        }
        properties.setApplicationName(applicationName);
        String ip = HostInfoUtils.getHostIp();
        if (ip ==  null || ip.length() <= 0) {
            throw new IllegalArgumentException("未找到本机ip");
        }
        properties.setIp(ip);
        String port = PropertyUtil.getProperty("server.port");
        if (port ==  null || port.length() <= 0) {
            throw new IllegalArgumentException("缺失server.port配置");
        }
        properties.setPort(Integer.parseInt(port));
        return properties;
    }

    @Bean
    public MultiDiscoveryManager multiDiscoveryManager(MetaDataProperties metaDataProperties) {
        return new MultiDiscoveryManager(metaDataProperties);
    }
}
