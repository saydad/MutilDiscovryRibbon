package com.boom.ribbon;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

import com.boom.discovry.MultiDiscoveryAutoConfiguration;

/**
 * ribbon 启动配置
 * @author liuyong
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnBean(SpringClientFactory.class)
@AutoConfigureAfter(value = {RibbonAutoConfiguration.class, MultiDiscoveryAutoConfiguration.class})
@RibbonClients(defaultConfiguration = MultiDiscoveryRibbonClientConfiguration.class)
public class MultiDiscoveryRibbonAutoConfiguration {

}
