package com.sun.config;

import com.sun.sunmall.service.SunUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SunUserDetailService sunUserDetailService;



    /**
     * 方法实现说明:用于构建用户认证组件,需要传递userDetailsService和密码加密器
     * @author:smlz
     * @param auth
     * @return:
     * @exception:
     * @date:2019/12/25 14:31
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sunUserDetailService).passwordEncoder(passwordEncoder());
    }


    /**
     * 设置前台静态资源不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String[] args) {
        String oldPassword = "$2a$10$eo6qnvKKq8cOkf7XkRFMwe29pA1YoE4tEbA5tK820JvkUu1ZIIOIW";
        String oldPasswordtest = new BCryptPasswordEncoder().encode("test");
        String newPassword = new BCryptPasswordEncoder().encode("123456");
        String old = "test";
        System.out.println(newPassword);
        System.out.println(oldPasswordtest);
        BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
        if(!encode.matches(oldPassword,newPassword)){
            System.out.println(oldPassword);
            System.out.println(newPassword);
        }
        System.out.println("===========================================");
        if(!encode.matches(old,oldPassword)){
            System.out.println(new BCryptPasswordEncoder().encode(old));
            System.out.println(oldPassword);
        }
    }

}
