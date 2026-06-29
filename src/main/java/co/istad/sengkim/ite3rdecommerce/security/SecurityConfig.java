package co.istad.sengkim.ite3rdecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        //TODO : STEP
        //1. CRSF -> Disable
        http.csrf(token->token.disable());

        //2.Disable form login
        http.formLogin(form->form.disable());

        //3. Set security mechanism - HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        //4. Set REST API to Stateless
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //5. Config endpoints
        //Anonymous, Authenticated, Authorization
        http.authorizeHttpRequests(endpoints->endpoints
                .requestMatchers("/api/v1/files/**").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }
}
