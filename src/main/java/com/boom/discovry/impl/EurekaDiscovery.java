package com.boom.discovry.impl;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.boom.discovry.DiscoveryWrapper;
import com.boom.discovry.config.EurekaInstanceConfig;
import com.boom.discovry.properties.MetaDataProperties;
import com.boom.model.InstanceWrapperObj;
import com.boom.util.PropertyUtil;

/**
 * eureka 封装
 *
 * @author liuyong
 */
public class EurekaDiscovery implements DiscoveryWrapper<EurekaClient> {

    private EurekaClient client;

    @Override
    public String getClientKey() {
        return "eureka";
    }

    @Override
    public String getEnabledKey() {
        return "discovery.eureka.enabled";
    }

    @Override
    public String getRegisterKey() {
        return "discovery.eureka.register";
    }

    @Override
    public String getPriorityKey() {
        return "discovery.eureka.priority";
    }

    @Override
    public EurekaClient buildClient(MetaDataProperties metaDataProperties) {
        //加载配置
        Properties properties = buildEurekaConfigProps();
        String selfRegister = PropertyUtil.getProperty("discovery.eureka.register");
        if ("false".equals(selfRegister)) {
            /* 客户端取消自我注册，仅发现 */
            properties.setProperty("eureka.registration.enabled", "false");
        }
        String refreshInterval = PropertyUtil.getProperty("discovery.eureka.refresh-interval");
        if (refreshInterval != null && refreshInterval.length() > 0) {
            properties.setProperty("eureka.client.refresh.interval", refreshInterval);
        }
        ConfigurationManager.loadProperties(properties);

        //本机实例信息
        EurekaInstanceConfig instanceConfig = new EurekaInstanceConfig()
                .setInstanceId(metaDataProperties.getIp() + ":" + metaDataProperties.getPort())
                .setAppname(metaDataProperties.getApplicationName())
                .setIpAddress(metaDataProperties.getIp())
                .setNonSecurePort(metaDataProperties.getPort());
        ApplicationInfoManager applicationInfoManager =
                new ApplicationInfoManager(instanceConfig, (ApplicationInfoManager.OptionalArgs) null);
        //构造client
        client = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());
        //状态由启动变更为上线
        if (!"false".equals(selfRegister)) {
            applicationInfoManager.setInstanceStatus(InstanceStatus.UP);
        }
        return client;
    }

    private Properties buildEurekaConfigProps() {
        Properties properties = new Properties();
        properties.setProperty("eureka.serviceUrl.default", PropertyUtil.getProperty("discovery.eureka.address"));
        properties.setProperty("eureka.appinfo.initial.replicate.time", "0");

        return properties;
    }

    @Override
    public void registerSelf(MetaDataProperties metaDataProperties) {
        /*eureka在构造函数中控制*/
    }

    @Override
    public List<InstanceWrapperObj> getInstanceListByAppName(String appName) {
        List<InstanceInfo> instances = client.getApplication(appName).getInstances();
        if (instances == null || instances.size() <= 0) {
            return Collections.emptyList();
        }

        return instances.stream().map(item -> new InstanceWrapperObj(
                item.getHostName(),
                item.isPortEnabled(InstanceInfo.PortType.SECURE) ? item.getSecurePort() : item.getPort())
        ).collect(Collectors.toList());
    }

    @Override
    public void destroy() {
        if (client != null) {
            client.shutdown();
        }
    }
}
