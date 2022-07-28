package com.techleads.app.config.deprecated;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {
    private final Log logger = LogFactory.getLog(AppConfig.class);

//    myAccount -> secure
//            myBalance -> secure
//    myLoans -> secure
//            myCards -> secure
//    notices    -> no secure
//            contact -> no secure
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info(" inside configure(HttpSecurity http) method");

        //(1)*****configure authenticate and permit specific requests*********

        http.authorizeRequests((requests) -> {
//                requests.anyRequest().authenticated()

                    requests.antMatchers("/myAccount").hasAnyAuthority("admin")
//                    .authenticated()
                            .antMatchers("/myBalance").hasAnyAuthority("dev")
                            .antMatchers("/myLoans").authenticated()
                            .antMatchers("/myCards").authenticated()
                            .antMatchers("/notices").permitAll()
                            .antMatchers("/contact").permitAll();
                }
        );

        http.formLogin();
        http.httpBasic();

        //(2)*******configuraton to deny all the requests*********
        /*
        http.authorizeRequests((requests) -> {
            requests.anyRequest().denyAll();
        });
        http.formLogin();
        http.httpBasic();
        */


        //(3)*****configure permitAll requests*********
        /*
        http.authorizeRequests((requests)->{
            requests.anyRequest().permitAll();
        });
        */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //*****First approach********
//        auth.inMemoryAuthentication().withUser("admin").password("admin1").authorities("admin")
//                .and().withUser("madhav").password("madhav1").authorities("dev")
//                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());

        //*****2nd approach********
        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
        UserDetails user1= User.withUsername("admin").password("admin1").authorities("admin").build();
        UserDetails user2= User.withUsername("madhav").password("madhav1").authorities("dev").build();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        auth.userDetailsService(userDetailsManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}