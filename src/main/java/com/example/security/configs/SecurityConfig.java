package com.example.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.security.filter.CustomAuthenticationFailureHandler;
import com.example.security.filter.CustomAuthenticationSuccessHandler;
import com.example.security.services.authentication.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

        @Autowired
        private UserDetailsServiceImpl userDetailsService;       

        @Autowired
        private CustomAuthenticationSuccessHandler customLoginSuccessHandler;

        @Autowired
        private CustomAuthenticationFailureHandler customLoginFailureHandler;

        @Bean
        public SessionRegistry sessionRegistry() {
                return new SessionRegistryImpl();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/dashboard/**").hasAnyAuthority("ADMIN")
                                                .requestMatchers("/profile").hasAnyAuthority("USER", "ADMIN")
                                                .anyRequest().permitAll())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .usernameParameter("email")
                                                .passwordParameter("password")
                                                .successHandler(customLoginSuccessHandler)
                                                .failureHandler(customLoginFailureHandler)
                                                .permitAll())

                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/oauth2/authorization/google")
                                                .successHandler(customLoginSuccessHandler)
                                                .failureHandler(customLoginFailureHandler))

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .permitAll())

                                // session
                                .sessionManagement(session -> session
                                                .maximumSessions(1) // Giới hạn chỉ cho phép một session duy nhất cho
                                                                    // mỗi người dùng
                                                .expiredUrl("/login?expired=true") // Redirect đến trang login khi
                                                                                   // session hết hạn
                                );
                return http.build();
        }

        @Bean
        public AuthenticationManager authManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
                return authenticationManagerBuilder.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
