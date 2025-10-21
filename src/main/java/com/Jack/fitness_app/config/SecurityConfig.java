package com.Jack.fitness_app.config;

import com.Jack.fitness_app.security.CustomUserDetailsService;
import com.Jack.fitness_app.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with our configuration
                .cors(Customizer.withDefaults())
                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // Stateless session; no cookies
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Public vs protected endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("OPTIONS", "/**").permitAll()  // Allow all OPTIONS requests for CORS
                        .requestMatchers("/api/auth/**",
                                "/api/oneTimeSession/addOneTimeSession",
                                "/api/oneTimeSession/viewOneTimeSessionList",
                                "/api/oneTimeSession/viewOneTimeSession/{id}",
                                "/api/regularClass/addRegularClass",
                                "/api/regularClass/viewRegularClassList",
                                "/api/regularClass/viewRegularClass/{id}",
                                "/api/regularClass/enrolRegularClass/{id}",
                                "/api/oneTimeSession/enrolSession/{id}"
                                ).permitAll()  // login, register, verify
                        .anyRequest().authenticated()
                )

                // Disable default login form and basic auth
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // Register custom DAO authentication provider
                .authenticationProvider(authenticationProvider())

                // Add JWT filter before Spring’s built-in username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

