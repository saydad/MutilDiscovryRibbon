package com.boom.discovry.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInfo;

/**
 * eureka client构造方法需要实现的接口
 */
public class EurekaInstanceConfig implements com.netflix.appinfo.EurekaInstanceConfig {

    private String appname;

    private String appGroupName;

    private boolean instanceEnabledOnit;

    private int nonSecurePort;

    private int securePort;

    private boolean nonSecurePortEnabled = true;

    private boolean securePortEnabled;

    private int leaseRenewalIntervalInSeconds = 30;

    private int leaseExpirationDurationInSeconds = 90;

    private String virtualHostName = "unknown";

    private String instanceId;

    private String secureVirtualHostName = "unknown";

    private String aSGName;

    private Map<String, String> metadataMap = new HashMap<>();

    private DataCenterInfo dataCenterInfo = new MyDataCenterInfo(DataCenterInfo.Name.MyOwn);

    private String ipAddress;

    private String statusPageUrlPath;

    private String statusPageUrl;

    private String homePageUrlPath = "/";

    private String homePageUrl;

    private String healthCheckUrlPath;

    private String healthCheckUrl;

    private String secureHealthCheckUrl;

    private String namespace = "eureka";

    private String hostname;

    private boolean preferIpAddress = false;

    private InstanceInfo.InstanceStatus initialStatus = InstanceInfo.InstanceStatus.UP;

    private String[] defaultAddressResolutionOrder = new String[0];

    @Override
    public String getAppname() {
        return appname;
    }

    public EurekaInstanceConfig setAppname(String appname) {
        this.appname = appname;
        return this;
    }

    @Override
    public String getAppGroupName() {
        return appGroupName;
    }

    public EurekaInstanceConfig setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
        return this;
    }

    @Override
    public boolean isInstanceEnabledOnit() {
        return instanceEnabledOnit;
    }

    public EurekaInstanceConfig setInstanceEnabledOnit(boolean instanceEnabledOnit) {
        this.instanceEnabledOnit = instanceEnabledOnit;
        return this;
    }

    @Override
    public int getNonSecurePort() {
        return nonSecurePort;
    }

    public EurekaInstanceConfig setNonSecurePort(int nonSecurePort) {
        this.nonSecurePort = nonSecurePort;
        return this;
    }

    @Override
    public int getSecurePort() {
        return securePort;
    }

    public EurekaInstanceConfig setSecurePort(int securePort) {
        this.securePort = securePort;
        return this;
    }

    @Override
    public boolean isNonSecurePortEnabled() {
        return nonSecurePortEnabled;
    }

    @Override
    public boolean getSecurePortEnabled() {
        return securePortEnabled;
    }

    public EurekaInstanceConfig setNonSecurePortEnabled(boolean nonSecurePortEnabled) {
        this.nonSecurePortEnabled = nonSecurePortEnabled;
        return this;
    }

    public EurekaInstanceConfig setSecurePortEnabled(boolean securePortEnabled) {
        this.securePortEnabled = securePortEnabled;
        return this;
    }

    @Override
    public int getLeaseRenewalIntervalInSeconds() {
        return leaseRenewalIntervalInSeconds;
    }

    public EurekaInstanceConfig setLeaseRenewalIntervalInSeconds(int leaseRenewalIntervalInSeconds) {
        this.leaseRenewalIntervalInSeconds = leaseRenewalIntervalInSeconds;
        return this;
    }

    @Override
    public int getLeaseExpirationDurationInSeconds() {
        return leaseExpirationDurationInSeconds;
    }

    public EurekaInstanceConfig setLeaseExpirationDurationInSeconds(int leaseExpirationDurationInSeconds) {
        this.leaseExpirationDurationInSeconds = leaseExpirationDurationInSeconds;
        return this;
    }

    @Override
    public String getVirtualHostName() {
        return virtualHostName;
    }

    public EurekaInstanceConfig setVirtualHostName(String virtualHostName) {
        this.virtualHostName = virtualHostName;
        return this;
    }

    @Override
    public String getInstanceId() {
        return instanceId;
    }

    public EurekaInstanceConfig setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    @Override
    public String getSecureVirtualHostName() {
        return secureVirtualHostName;
    }

    @Override
    public String getASGName() {
        return aSGName;
    }

    @Override
    public String getHostName(boolean refresh) {
        return null;
    }

    public EurekaInstanceConfig setSecureVirtualHostName(String secureVirtualHostName) {
        this.secureVirtualHostName = secureVirtualHostName;
        return this;
    }

    public EurekaInstanceConfig setASGName(String aSGName) {
        this.aSGName = aSGName;
        return this;
    }

    @Override
    public Map<String, String> getMetadataMap() {
        return metadataMap;
    }

    public EurekaInstanceConfig setMetadataMap(Map<String, String> metadataMap) {
        this.metadataMap = metadataMap;
        return this;
    }

    @Override
    public DataCenterInfo getDataCenterInfo() {
        return dataCenterInfo;
    }

    public EurekaInstanceConfig setDataCenterInfo(DataCenterInfo dataCenterInfo) {
        this.dataCenterInfo = dataCenterInfo;
        return this;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    public EurekaInstanceConfig setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    @Override
    public String getStatusPageUrlPath() {
        return statusPageUrlPath;
    }

    public EurekaInstanceConfig setStatusPageUrlPath(String statusPageUrlPath) {
        this.statusPageUrlPath = statusPageUrlPath;
        return this;
    }

    @Override
    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public EurekaInstanceConfig setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
        return this;
    }

    @Override
    public String getHomePageUrlPath() {
        return homePageUrlPath;
    }

    public EurekaInstanceConfig setHomePageUrlPath(String homePageUrlPath) {
        this.homePageUrlPath = homePageUrlPath;
        return this;
    }

    @Override
    public String getHomePageUrl() {
        return homePageUrl;
    }

    public EurekaInstanceConfig setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
        return this;
    }

    @Override
    public String getHealthCheckUrlPath() {
        return healthCheckUrlPath;
    }

    public EurekaInstanceConfig setHealthCheckUrlPath(String healthCheckUrlPath) {
        this.healthCheckUrlPath = healthCheckUrlPath;
        return this;
    }

    @Override
    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public EurekaInstanceConfig setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
        return this;
    }

    @Override
    public String getSecureHealthCheckUrl() {
        return secureHealthCheckUrl;
    }

    public EurekaInstanceConfig setSecureHealthCheckUrl(String secureHealthCheckUrl) {
        this.secureHealthCheckUrl = secureHealthCheckUrl;
        return this;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public EurekaInstanceConfig setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public EurekaInstanceConfig setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public boolean isPreferIpAddress() {
        return preferIpAddress;
    }

    public EurekaInstanceConfig setPreferIpAddress(boolean preferIpAddress) {
        this.preferIpAddress = preferIpAddress;
        return this;
    }

    public InstanceInfo.InstanceStatus getInitialStatus() {
        return initialStatus;
    }

    public EurekaInstanceConfig setInitialStatus(InstanceInfo.InstanceStatus initialStatus) {
        this.initialStatus = initialStatus;
        return this;
    }

    @Override
    public String[] getDefaultAddressResolutionOrder() {
        return defaultAddressResolutionOrder;
    }

    public EurekaInstanceConfig setDefaultAddressResolutionOrder(String[] defaultAddressResolutionOrder) {
        this.defaultAddressResolutionOrder = defaultAddressResolutionOrder;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
