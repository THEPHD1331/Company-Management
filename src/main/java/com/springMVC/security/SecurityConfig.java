package com.springMVC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

   @Bean
   public UserDetailsManager detailsManager(DataSource dataSource){

       JdbcUserDetailsManager userDetailsManager= new JdbcUserDetailsManager(dataSource);
           // userDetailsManager.setUsersByUsernameQuery("");   For custom tables
           // userDetailsManager.setAuthoritiesByUsernameQuery("");

       return userDetailsManager;
   }

    @Bean // Security config for Custom login form (Overriding default login provided by Spring)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configure->
                configure     // Restricted URLs based on roles
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/admins/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Any request must be authenticated

        ).formLogin(form->
                form.loginPage("/showLoginPage") //Show our custom form for login page
                    .loginProcessingUrl("/authenticateTheUser") // Login form should POST data to this URL (Id and password)
                        .permitAll() // Allows everyone to see the login page

        ).logout(logout-> logout.permitAll()
        ).exceptionHandling(configurer-> configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }

//    @Bean // Role based user access control (No database)
//    public InMemoryUserDetailsManager detailsManager(){
//        UserDetails sahil= User.builder()
//                .username("sahil")
//                .password("{noop}sahil123")
//                .roles("EMPLOYEE").build();
//
//        UserDetails karan= User.builder()
//                .username("karan")
//                .password("{noop}karan123")
//                .roles("EMPLOYEE","MANAGER").build();
//
//        UserDetails paras= User.builder()
//                .username("paras")
//                .password("{noop}paras123")
//                .roles("EMPLOYEE","MANAGER","ADMIN").build();
//
//        return new InMemoryUserDetailsManager(sahil, karan, paras);
//    }
}
