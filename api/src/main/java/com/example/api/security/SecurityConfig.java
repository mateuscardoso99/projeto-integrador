package com.example.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Autowired
    private AuthService authService;
    
    @Bean
    public TokenFilter authenticationJwtTokenFilter() {
        return new TokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(authService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    //@Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(v -> v.configurationSource(corsConfigurationSource()))
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint)) //classe que trata as exceções de autenticação
            .authorizeHttpRequests((request) -> {
                    request.requestMatchers("/usuario/login").permitAll()
                            .requestMatchers("/usuario/cadastro").permitAll()
                            .requestMatchers("/batch/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/partida/categorias").permitAll()
                            .requestMatchers(HttpMethod.POST, "/categoria/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/categoria/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/categoria/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/categoria/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/questao/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/questao/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/questao/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/questao/**").hasAuthority("ADMIN")
                            .anyRequest().authenticated();
                }
            )
            //.securityMatcher(r -> java.util.stream.Stream.of(r.getCookies()).anyMatch(f -> f.getName().equals("jwt")))
            .addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(getDaoAuthProvider());
        return http.build();
    }

    /*@Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(
            HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(
                SessionCreationPolicy.NEVER).and()
            .authorizeHttpRequests(securedRequests)
            // checking for "Authorization" header
            .securityMatcher(request ->
                request.getHeader("Authorization") != null)
            .httpBasic(basic ->
                basic.authenticationEntryPoint(
                    (request, response, exp)->
                        response.setStatus(401)))
            .build();
    }

    //se a request não tem o header authorization, então usa esse método pra gerenciar a autenticação, se tem usa o decima
    @Bean
    public SecurityFilterChain formFilterChain(
            HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(securedRequests)
            .formLogin(form ->
                form.loginPage("/login")
                    .failureUrl("/login?error"))
            .logout(logout ->
                logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID"))
            .build();
    }*/

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:4200");//adiciona as origens permitidas
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.setAllowedHeaders(List.of(
                "X-Requested-With", "Content-Type",
                "Authorization", "Origin", "Accept",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
