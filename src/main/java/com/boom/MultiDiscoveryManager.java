package com.boom;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;

import com.alibaba.nacos.common.utils.MapUtils;
import com.boom.discovry.DiscoveryWrapper;
import com.boom.discovry.properties.MetaDataProperties;
import com.boom.model.InstanceWrapperObj;
import com.boom.util.PropertyUtil;

/**
 * @author liuyong
 */
public class MultiDiscoveryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDiscoveryManager.class);

    /**
     * client原始对象集合
     * clientKey - rawClient
     */
    private final LinkedHashMap<String, Object> rawClientMap = new LinkedHashMap<>();
    /**
     * client包装对象集合
     * clientKey - clientWrapper
     */
    private final LinkedHashMap<String, DiscoveryWrapper<?>> clientWrapperMap = new LinkedHashMap<>();
    /**
     * 强制指定使用哪个client
     */
    private static volatile String enforceClientKey;

    private final MetaDataProperties metaDataProperties;

    public MultiDiscoveryManager(MetaDataProperties metaDataProperties) {
        this.metaDataProperties = metaDataProperties;
    }

    /**
     * 增加获取客户端的口子
     *
     * @param key 注册中心clientKey
     * @param <T> client类型
     * @return client实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getClient(String key) {
        return (T) rawClientMap.get(key);
    }

    public static String getEnforceClientKey() {
        return MultiDiscoveryManager.enforceClientKey;
    }

    public static void setEnforceClientKey(String enforceClientKey) {
        MultiDiscoveryManager.enforceClientKey = enforceClientKey;
    }

    @PostConstruct
    @SuppressWarnings("rawtypes")
    public void loadDiscovery() {
        List<DiscoveryWrapper> clientWrappers = SpringFactoriesLoader.loadFactories(DiscoveryWrapper.class, null);
        //优先级排序
        clientWrappers = clientWrappers.stream()
                .sorted(Comparator.<DiscoveryWrapper>comparingInt(item -> getPriority(item.getPriorityKey())).reversed())
                .collect(Collectors.toList());

        Object rawClient;
        String clientKey;
        for (DiscoveryWrapper clientWrapper : clientWrappers) {
            if (!"true".equals(PropertyUtil.getProperty(clientWrapper.getEnabledKey()))) {
                continue;
            }

            clientKey = clientWrapper.getClientKey();
            rawClient = clientWrapper.buildClient(metaDataProperties);
            if ("true".equals(PropertyUtil.getProperty(clientWrapper.getRegisterKey()))) {
                clientWrapper.registerSelf(metaDataProperties);
            }

            rawClientMap.put(clientKey, rawClient);
            clientWrapperMap.put(clientKey, clientWrapper);
        }
    }

    private Integer getPriority(String priorityKey) {
        String property = PropertyUtil.getProperty(priorityKey);
        if (property == null || property.length() <= 0) {
            return 1;
        }
        return Integer.parseInt(property);
    }

    /**
     * 销毁所有的client
     */
    @PreDestroy
    public void destroy() {
        if (MapUtils.isEmpty(clientWrapperMap)) {
            return;
        }

        Collection<DiscoveryWrapper<?>> clientWrappers = clientWrapperMap.values();
        for (DiscoveryWrapper<?> clientWrapper : clientWrappers) {
            clientWrapper.destroy();
        }
    }

    /**
     * 获取指定名称的实例列表
     * @param appName 需要获取实例列表的服务名称
     * @return 实例列表
     */
    public List<InstanceWrapperObj> getInstanceList(String appName) {
        if (enforceClientKey != null && enforceClientKey.length() > 0 && clientWrapperMap.containsKey(enforceClientKey)) {
            return clientWrapperMap.get(enforceClientKey).getInstanceListByAppName(appName);
        }

        Set<Entry<String, DiscoveryWrapper<?>>> entries = clientWrapperMap.entrySet();
        for (Entry<String, DiscoveryWrapper<?>> entry : entries) {
            try {
                return entry.getValue().getInstanceListByAppName(appName);
            } catch (Exception e) {
                LOGGER.warn("通过{}获取{}实例列表异常", entry.getKey(), appName, e);
            }
        }
        return Collections.emptyList();
    }
}
