package com.api.deso.config;

import com.api.deso.config.jwt.JwtAuthenticationFilter;
import com.api.deso.config.jwt.JwtAuthorizationFilter;
import com.api.deso.handler.OAuth2SuccessHandler;
import com.api.deso.repository.UserRepository;
import com.api.deso.handler.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                        .cors().and()
                        .httpBasic().disable()
                        .formLogin().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // @CrossOrigin(인증X)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .antMatchers(HttpMethod.PATCH, "/**/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**/*").permitAll()
                .antMatchers("/api/v1/user/**")
                .hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/api/v1/manager/**")
                .hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
                .antMatchers("/api/v1/admin/**")
                .hasRole("ROLE_ADMIN")
                .anyRequest().permitAll();

        http.oauth2Login()
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(successHandler)
                .permitAll();

        http.httpBasic();

        http.logout()
                .deleteCookies("JSESSIONID");
    }
}
