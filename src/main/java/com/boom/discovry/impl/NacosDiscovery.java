package com.boom.discovry.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.boom.discovry.DiscoveryWrapper;
import com.boom.discovry.properties.MetaDataProperties;
import com.boom.model.InstanceWrapperObj;
import com.boom.util.PropertyUtil;

/**
 * nacos封装
 * @author liuyong
 */
public class NacosDiscovery implements DiscoveryWrapper<NamingService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NacosDiscovery.class);

    private NamingService namingService;
    private MetaDataProperties metaDataProperties;

    @Override
    public String getClientKey() {
        return "nacos";
    }

    @Override
    public String getEnabledKey() {
        return "discovery.nacos.enabled";
    }

    @Override
    public String getRegisterKey() {
        return "discovery.nacos.register";
    }

    @Override
    public String getPriorityKey() {
        return "discovery.nacos.priority";
    }

    @Override
    public NamingService buildClient(MetaDataProperties metaDataProperties) {
        try {
            namingService = NamingFactory.createNamingService(PropertyUtil.getProperty("discovery.nacos.address"));
        } catch (NacosException e) {
            throw new IllegalStateException(e);
        }
        return namingService;
    }

    @Override
    public void registerSelf(MetaDataProperties metaDataProperties) {
        try {
            namingService.registerInstance(metaDataProperties.getApplicationName(), metaDataProperties.getIp(), metaDataProperties.getPort());
            this.metaDataProperties = metaDataProperties;
        } catch (NacosException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<InstanceWrapperObj> getInstanceListByAppName(String appName) {
        try {
            List<Instance> instances = namingService.selectInstances(appName, true, true);
            if (instances == null || instances.size() <= 0) {
                return Collections.emptyList();
            }

            return instances.stream()
                    .map(item -> new InstanceWrapperObj(item.getIp(), item.getPort()))
                    .collect(Collectors.toList());
        } catch (NacosException e) {
           throw new IllegalStateException(e);
        }
    }

    @Override
    public void destroy() {
        if (namingService != null) {
            try {
                if (metaDataProperties != null) {
                    // 取消本机注册信息
                    namingService.deregisterInstance(metaDataProperties.getApplicationName(),
                            metaDataProperties.getIp(), metaDataProperties.getPort());
                }
                namingService.shutDown();
            } catch (NacosException e) {
                LOGGER.warn("关闭nacos client异常", e);
            }
        }
    }
}
