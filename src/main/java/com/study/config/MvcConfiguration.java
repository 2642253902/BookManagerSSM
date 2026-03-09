package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


@EnableWebMvc
@Configuration
@ComponentScan("com.study.controller") //扫描com.study.controller包及其子包中的组件（如@Controller注解的类），以便Spring能够自动检测并注册这些组件为Bean
public class MvcConfiguration implements WebMvcConfigurer { //实现WebMvcConfigurer接口，提供了许多方法来配置Spring MVC的行为，我们可以选择性地覆盖这些方法来定制我们的MVC配置

    //我们需要使用ThymeleafViewResolver作为视图解析器，并解析我们的HTML页面
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setOrder(1);  //设置优先级，默认是Integer.MAX_VALUE，数值越小优先级越高
        resolver.setCharacterEncoding("UTF-8");   //编码格式是重中之重
        resolver.setTemplateEngine(springTemplateEngine);  //模板引擎，默认即可
        return resolver;
    }

    //配置模板解析器
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setSuffix(".html");   //需要解析的HTML页面文件的后缀，默认是.html
        resolver.setPrefix("classpath:/templates/");  //需要解析的HTML页面文件的前缀，默认是classpath:/templates/
        resolver.setCharacterEncoding("UTF-8");   //编码格式是重中之重
        return resolver;
    }

    //配置模板引擎Bean
    @Bean
    public SpringTemplateEngine springTemplateEngine(ITemplateResolver resolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);   //模板解析器，默认即可
        return engine;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();  //启用默认的Servlet处理器，允许Spring MVC处理静态资源
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");  //配置静态资源的访问路径
    }

}
