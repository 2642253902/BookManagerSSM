package com.study.initialize;

import com.study.config.MainConfiguration;
import com.study.config.MvcConfiguration;
import com.study.config.SecurityConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {  //继承AbstractAnnotationConfigDispatcherServletInitializer类，Spring会自动检测到这个类，并在应用启动时进行DispatcherServlet的初始化工作


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MvcConfiguration.class, SecurityConfiguration.class, MainConfiguration.class}; //根配置类，包含了Spring MVC的配置和Spring Security的配置
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];        //Servlet配置类，包含了DispatcherServlet的配置，这里我们不需要单独的Servlet配置类，所以返回一个空数组
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};  //DispatcherServlet的映射路径，表示所有的请求都由DispatcherServlet处理
    }
}
