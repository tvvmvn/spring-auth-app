package com.springboot.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf()
        .disable()
        // authorize 인증,증명 (증명서 검사)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/register").permitAll()
            .anyRequest().authenticated()
        // authenticate 허가,인가 (증명서 발급)
        ).formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login") // POST /login
            .defaultSuccessUrl("/home", true)
            .permitAll())
        .logout(logout -> logout
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .permitAll());

    return http.build();
  }

  // Customize the AuthenticationManager
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }
}