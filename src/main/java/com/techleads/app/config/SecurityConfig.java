package com.techleads.app.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Log logger = LogFactory.getLog(SecurityConfig.class);

//    myAccount -> secure
//            myBalance -> secure
//    myLoans -> secure
//            myCards -> secure
//    notices    -> no secure
//            contact -> no secure
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("current inside configure(HttpSecurity http) method");

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

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("current inside configure(AuthenticationManagerBuilder auth) method");
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
*/


    //*******3rd approach***********
   /* @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        //used the spring provided tables
        // users (username, password, enabled) values (?,?,?)
        // authorities (username, authority) values (?,?)
        //from this class JdbcUserDetailsManager

        *//*SELECT * FROM easy_bank.authorities;CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `authority` varchar(45) NOT NULL,
        PRIMARY KEY (`id`)
) *//*

*//*        CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` int NOT NULL,
                PRIMARY KEY (`id`)
) *//*



        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
}
