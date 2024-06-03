package com.rayadi.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    final AuthenticationProvider authenticationProvider;
    final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // New version of springboot
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**",
                                                "/api/v1/books",
                                                "/api/v1/books/new",
                                            "/api/v1/books/{bookId}",
                                        "/api/v1/books/filter",
                                        "/api/v1/categories")
                                .permitAll()

                                /*.requestMatchers("/api/v1/management/**")
                                .hasAnyRole(ADMIN.name(), MANAGER.name())

                                .requestMatchers(GET,"/api/v1/management/**")
                                .hasAnyAuthority(ADMIN_READ.name(),MANAGER_READ.name())

                                .requestMatchers(POST,"/api/v1/management/**")
                                .hasAnyAuthority(ADMIN_CREATE.name(),MANAGER_CREATE.name())

                                .requestMatchers(PUT,"/api/v1/management/**")
                                .hasAnyAuthority(ADMIN_UPDATE.name(),MANAGER_UPDATE.name())

                                .requestMatchers(DELETE,"/api/v1/management/**")
                                .hasAnyAuthority(ADMIN_DELETE.name(),MANAGER_DELETE.name())

                                .requestMatchers("/api/v1/admin/**")
                                .hasRole(ADMIN.name())

                                .requestMatchers(GET,"/api/v1/admin/**")
                                .hasAuthority(ADMIN_READ.name())

                                .requestMatchers(POST,"/api/v1/admin/**")
                                .hasAuthority(ADMIN_CREATE.name())

                                .requestMatchers(PUT,"/api/v1/admin/**")
                                .hasAuthority(ADMIN_UPDATE.name())

                                .requestMatchers(DELETE,"/api/v1/admin/**")
                                .hasAuthority(ADMIN_DELETE.name())
                                */

                                .anyRequest()
                                .authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*.logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        ((request, response, authentication) ->
                                                SecurityContextHolder.clearContext())))*/


        // Old version of springboot
                /*.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                        ((request, response, authentication) -> SecurityContextHolder.clearContext()))
                */
        ;
        return http.build();
    }
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        //config.setAllowedOrigins(List.of("http://localhost:3000"));
        //config.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));
        //config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        return new CorsFilter(source);
    }

}
