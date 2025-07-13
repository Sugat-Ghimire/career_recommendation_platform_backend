

package com.career_recommendation_platform.career_recommendation_platform.config;
import com.career_recommendation_platform.career_recommendation_platform.security.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuth2SuccessHandler successHandler;

    public SecurityConfig(OAuth2SuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/api/public/**","/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/google")
                        .successHandler(successHandler)
                )
                .csrf(csrf -> csrf.disable()); // for now

        return http.build();
    }
}
