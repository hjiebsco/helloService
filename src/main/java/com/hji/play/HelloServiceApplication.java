package com.hji.play;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;

import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class HelloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloServiceApplication.class, args);
	}

	@Value("${server.port}")
	private int port;

	@Value("${address.local}")
	private boolean addressLocal;

	@Bean
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("hello-service");
		config.setDataCenterInfo(info);

		if (addressLocal) {
			info.getMetadata().put(AmazonInfo.MetaDataKey.localHostname.getName(),
					info.get(AmazonInfo.MetaDataKey.localIpv4));
			config.setHostname(info.get(AmazonInfo.MetaDataKey.localHostname));
			config.setIpAddress(info.get(AmazonInfo.MetaDataKey.localIpv4));
		} else {
			info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(),
					info.get(AmazonInfo.MetaDataKey.publicIpv4));
			config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
			config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		}

		config.setNonSecurePort(port);
		return config;
	}
}
