package com.boom.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ServerList;
import com.boom.MultiDiscoveryManager;
import com.boom.ribbon.rule.RealTimeRoundRobinRule;
import com.boom.ribbon.serverList.MultiDiscoveryServerList;

@Configuration
@ConditionalOnClass(ServerList.class)
public class MultiDiscoveryRibbonClientConfiguration {

    @Autowired
    private PropertiesFactory propertiesFactory;

    @Bean
    @ConditionalOnMissingBean
    public ServerList<?> ribbonServerList(IClientConfig config, MultiDiscoveryManager discoveryManager) {
        if (this.propertiesFactory.isSet(ServerList.class, config.getClientName())) {
            return this.propertiesFactory.get(ServerList.class, config,
                    config.getClientName());
        }
        MultiDiscoveryServerList serverList = new MultiDiscoveryServerList(discoveryManager);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

    @Bean
    public IRule realTimeRoundRobinRule() {
        return new RealTimeRoundRobinRule();
    }
}
