package org.lognet.springboot.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class GRpcServerRunner implements CommandLineRunner, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(GRpcServerRunner.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    @Autowired
    private GRpcServerProperties gRpcServerProperties;



    private Server server;

    private final ServerBuilder<?> serverBuilder;

    public GRpcServerRunner(ServerBuilder<?> serverBuilder) {
        this.serverBuilder = serverBuilder;
    }

    @Override
    public void run(String... args) throws Exception {

        // find and register all GRpcService-enabled beans
        getBeanNamesByTypeWithAnnotation(GRpcService.class, BindableService.class)
                .forEach(name -> {
                    BindableService srv = applicationContext.getBeanFactory().getBean(name, BindableService.class);
                    ServerServiceDefinition serviceDefinition = srv.bindService();
                    serverBuilder.addService(serviceDefinition);
                });

        server = serverBuilder.build().start();

        log.info("gRPC Server started, listening on port {}.", server.getPort());
        startDaemonAwaitThread();

    }


    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread(()->{
                try {
                    GRpcServerRunner.this.server.awaitTermination();
                } catch (InterruptedException e) {
                    log.error("gRPC server stopped.", e);
                }
            });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    @Override
    public void destroy() throws Exception {
        log.info("Shutting down gRPC server ...");
        Optional.ofNullable(server).ifPresent(Server::shutdown);
        log.info("gRPC server stopped.");
    }

    private <T> Stream<String> getBeanNamesByTypeWithAnnotation(Class<? extends Annotation> annotationType, Class<T> beanType) throws Exception {

        return Stream.of(applicationContext.getBeanNamesForType(beanType))
                .filter(name -> {
                    final BeanDefinition beanDefinition = applicationContext.getBeanFactory().getBeanDefinition(name);
                    final Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotationType);

                    if (beansWithAnnotation.containsKey(name)) {
                        return true;
                    } else if (beanDefinition.getSource() instanceof AnnotatedTypeMetadata) {
                        return AnnotatedTypeMetadata.class.cast(beanDefinition.getSource()).isAnnotated(annotationType.getName());

                    }

                    return false;
                });
    }


}
