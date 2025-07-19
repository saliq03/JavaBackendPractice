package com.springsecuritypractice.securitypractice.security_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecuritypractice.securitypractice.filter.JWTFilter;
import com.springsecuritypractice.securitypractice.service.MyUserDetailsService;

@Configuration
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.
        csrf(csrf->csrf.disable())
        .authorizeHttpRequests(request->request
              .requestMatchers("signUp","signIn")
              .permitAll()
              .anyRequest().authenticated())
        // http.formLogin(Customizer.withDefaults());
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        
        


        return http.build();
    }

    @Bean 
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
       return config.getAuthenticationManager();
    }    
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1= User
    //                        .withDefaultPasswordEncoder()
    //                        .username("saliq")
    //                        .password("s@123")
    //                        .roles("user")
    //                        .build();
    //     UserDetails user2= User
    //                        .withDefaultPasswordEncoder()
    //                        .username("maroof")
    //                        .password("m@123")
    //                        .roles("user")
    //                        .build();

    //     return new InMemoryUserDetailsManager(user1,user2);
    // }

}

