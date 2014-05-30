package com.kk.jarvis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kk.jarvis.auth.*;
import com.kk.jarvis.dao.mysql.MySqlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@Import({ MySqlConfiguration.class})
@ComponentScan(basePackageClasses = Jarvis.class)
public class Jarvis extends DelegatingWebMvcConfiguration {

    @Autowired(required = true)
    private JarvisTokenDecoder jarvisTokenDecoder;



    public static void main(String[] args) {
        SpringApplication.run(Jarvis.class, args);
    }

    @Bean
    public JarvisAuthTokenResolver jarvisAuthTokenResolver() throws IOException {
        JarvisAuthTokenResolver resolver =
                new JarvisAuthTokenResolver(
                        jarvisTokenDecoder,
                        JarvisAuthToken.class,
                        JarvisTokenConstants.CLIENT_SECRET_KEY_NAME,
                        jarvisSecurity(),
                        JarvisTokenConstants.DEFAULT_AUTH_TOKEN_HEADER
        );

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

    @Value("${jarvisSecurity}")
    private String securityDefinitionJson;

    @Bean
    public JarvisSecurity jarvisSecurity() throws IOException {
        return new ObjectMapper().readValue(securityDefinitionJson, JarvisSecurity.class);
    }
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);

        registry.addViewController("/welcome");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
    */
}