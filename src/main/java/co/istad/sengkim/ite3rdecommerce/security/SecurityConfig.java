package co.istad.sengkim.ite3rdecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        //TODO : STEP
        //1. CRSF -> Disable
        //CRSF use to prevent the attacker ( From submiting Form Data )
        // If i enable it i can't submit the data because it's always need the credentials
        http.csrf(token->token.disable());

        //2.Disable form login
        http.formLogin(form->form.disable());

        //3.Security Mechanism - OAuth2 & JWT
        http.oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));

        //4. Set REST API to Stateless
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //5. Config endpoints
        //Anonymous, Authenticated, Authorization
        http.authorizeHttpRequests(endpoints->endpoints
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/files/**").permitAll()

                .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .requestMatchers("/scalar/**").permitAll()

                .anyRequest().authenticated());

        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthroiteiesConverter = jwt -> {
            Map<String,Collection> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            return roles.stream()
                    .map(role->new SimpleGrantedAuthority("ROLE_"+role))
                    .collect(Collectors.toList());
        };
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthroiteiesConverter);
        return jwtAuthenticationConverter;
    }
}
