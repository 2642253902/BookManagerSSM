package com.study.config;


import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用BCryptPasswordEncoder作为密码加密器，BCrypt是一种强哈希算法，适合用于密码存储
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {     //配置持久化令牌存储库，使用JdbcTokenRepositoryImpl来存储remember-me令牌到数据库中
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);//设置数据源，Spring会自动注入dataSource Bean
        //tokenRepository.setCreateTableOnStartup(true); //在应用启动时自动创建remember_me_token表，如果表已经存在则不会重复创建
        return tokenRepository;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, PersistentTokenRepository repository) throws Exception {
        return security
                .authorizeHttpRequests(registry -> {    //配置请求授权规则
                    registry.requestMatchers("/static/**").permitAll(); //允许访问静态资源
                    registry.anyRequest().authenticated(); //所有请求都需要认证
                })
                .formLogin(configurer -> {  //配置表单登录
                    configurer.loginPage("/login");
                    configurer.loginProcessingUrl("/doLogin"); //处理登录请求的URL，默认是/login
                    configurer.defaultSuccessUrl("/");  //登录成功后默认跳转的URL，默认是/
                    configurer.permitAll();  //允许所有用户访问登录页面和处理登录请求的URL
                })
                .csrf(configurer -> {
                    configurer.disable();  //禁用CSRF保护，注意：在生产环境中应该启用CSRF保护以防止跨站请求伪造攻击
                })
                .rememberMe(configurer -> {
                    configurer.tokenRepository(repository);  //使用持久化令牌存储库来存储remember-me令牌，默认是InMemoryTokenRepositoryImpl
                    configurer.tokenValiditySeconds(7 * 24 * 60 * 60); //设置remember-me令牌的有效期，单位是秒，这里设置为7天

                })
                .logout(configurer -> {
                    configurer.logoutUrl("/doLogout");  //处理注销请求的URL，默认是/logout
                    configurer.logoutSuccessUrl("/login");  //注销成功后默认跳转的URL，默认是/login
                    configurer.permitAll();  //允许所有用户访问注销请求的URL
                })
                .build();

    }

}
