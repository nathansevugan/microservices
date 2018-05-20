package org.lognet.springboot.grpc.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by alexf on 26-Jan-16.
 */

@ConfigurationProperties
public class GRpcServerProperties {
    /**
     * gRPC server port
     *
     */

    @Value("${server.port}")
    private int port = 8080;


    public int getPort(){
        return port;
    }
}
