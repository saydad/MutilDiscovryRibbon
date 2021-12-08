package com.boom.discovry;

import java.util.List;

import com.boom.discovry.properties.MetaDataProperties;
import com.boom.model.InstanceWrapperObj;

/**
 * 注册中心client封装接口
 * 用于屏蔽各个client的差异性
 * @author liuyong
 */
public interface DiscoveryWrapper<T> {

    /**
     * 客户端类型-唯一标识
     * @return client的类型
     */
    String getClientKey();

    /**
     * 是否启用该注册中心client，开关key
     * @return 开关key
     */
    String getEnabledKey();

    /**
     * 注册本机到远端，开关key
     * @return 开关key
     */
    String getRegisterKey();

    /**
     * 获取优先级key
     * @return 优先级
     */
    String getPriorityKey();

    /**
     * 构建客户端且注册本机
     * @return 客户端实例
     */
    T buildClient(MetaDataProperties metaDataProperties);

    /**
     * 注册本机
     */
    void registerSelf(MetaDataProperties metaDataProperties);

    /**
     * 获取指定名称的实例列表
     * @param appName 服务-名称
     * @return 实例列表
     */
    List<InstanceWrapperObj> getInstanceListByAppName(String appName);

    /**
     * client销毁
     */
    void destroy();
}
