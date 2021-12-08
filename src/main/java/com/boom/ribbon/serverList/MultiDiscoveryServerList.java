package com.boom.ribbon.serverList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;
import com.boom.MultiDiscoveryManager;
import com.boom.model.InstanceWrapperObj;

/**
 * ribbon-实际服务列表获取实例
 * @author liuyong
 */
public class MultiDiscoveryServerList extends AbstractServerList<Server> {

    private String serviceId;
    private final MultiDiscoveryManager discoveryManager;

    public MultiDiscoveryServerList(MultiDiscoveryManager discoveryManager) {
        this.discoveryManager = discoveryManager;
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return getServer();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return getServer();
    }

    private List<Server> getServer() {
        List<InstanceWrapperObj> instanceList = discoveryManager.getInstanceList(serviceId);
        if (instanceList == null || instanceList.size() == 0) {
            return Collections.emptyList();
        }

        return instanceList.stream()
                .map(item -> new Server(item.getIp(), item.getPort()))
                .collect(Collectors.toList());
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        this.serviceId = iClientConfig.getClientName();
    }
}
