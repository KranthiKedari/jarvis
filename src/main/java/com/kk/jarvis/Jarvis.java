package com.kk.jarvis;

import com.google.common.collect.Lists;
import com.kk.jarvis.auth.*;
import com.kk.jarvis.dao.mysql.MySqlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableAutoConfiguration
@Import({ MySqlConfiguration.class})
@ComponentScan(basePackageClasses = Jarvis.class)
public class Jarvis extends DelegatingWebMvcConfiguration {

    @Autowired(required = true)
    private JarvisTokenDecoder jarvisTokenDecoder;

    @Autowired(required = true)
    private JarvisSecurity jarvisSecurity;

    public static void main(String[] args) {
        SpringApplication.run(Jarvis.class, args);
    }

    @Bean
    public JarvisAuthTokenResolver jarvisAuthTokenResolver() {
        JarvisAuthTokenResolver resolver =
                new JarvisAuthTokenResolver(
                        jarvisTokenDecoder,
                        JarvisAuthToken.class,
                        JarvisTokenConstants.DEFAULT_AUTH_TOKEN_HEADER,
                        jarvisSecurity,
                        JarvisTokenConstants.CLIENT_SECRET_KEY_NAME);

        RequestMappingHandlerAdapter requestMappingHandlerAdapter   = this.requestMappingHandlerAdapter();

        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = Lists.newLinkedList(requestMappingHandlerAdapter.getArgumentResolvers());

        handlerMethodArgumentResolvers.add(0, resolver);

        this.requestMappingHandlerAdapter().setArgumentResolvers(handlerMethodArgumentResolvers);

        return resolver;
    }

    @Bean
    public List<HandlerMethodReturnValueHandler> returnValueHandlers(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        final List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(  requestMappingHandlerAdapter.getReturnValueHandlers() );

        requestMappingHandlerAdapter.setReturnValueHandlers(handlers);
        return handlers;
    }
}