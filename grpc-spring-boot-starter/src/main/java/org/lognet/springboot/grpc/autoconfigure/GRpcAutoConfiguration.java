package org.lognet.springboot.grpc.autoconfigure;

import io.grpc.ServerBuilder;
import org.lognet.springboot.grpc.GRpcServerRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by alexf on 25-Jan-16.
 */

@AutoConfigureOrder
@EnableConfigurationProperties(GRpcServerProperties.class)
public class GRpcAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GRpcServerProperties grpcServerProperties;

    @Bean
    public GRpcServerRunner grpcServerRunner() {
        return new GRpcServerRunner(ServerBuilder.forPort(grpcServerProperties.getPort()));
    }

}
