package com.boom.ribbon.rule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.boom.MultiDiscoveryManager;
import com.boom.model.InstanceWrapperObj;

/**
 * ribbon实时服务地址&轮询策略
 * @author liuyong
 */
public class RealTimeRoundRobinRule extends AbstractLoadBalancerRule {
    private static final Logger log = LoggerFactory.getLogger(RealTimeRoundRobinRule.class);

    private final AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    @Autowired
    private MultiDiscoveryManager discoveryManager;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @SuppressWarnings("rawtypes")
    private Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) lb;
        String name = loadBalancer.getName();
        InstanceWrapperObj server;
        List<InstanceWrapperObj> providerInfo;
        int count = 0;
        while (count++ < 10) {
            providerInfo = discoveryManager.getInstanceList(name);
            int serverCount = providerInfo.size();
            if (serverCount == 0) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            int nextServerIndex = incrementAndGetModulo(serverCount);
            server = providerInfo.get(nextServerIndex);

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            return new Server(server.getIp(), server.getPort());
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: " + lb);
        }
        return null;
    }

    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}
