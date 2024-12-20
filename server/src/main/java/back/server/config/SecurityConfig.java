package back.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import back.server.security.JwtTokenValidator;

@Configuration
// @EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/get_all").permitAll()
                        .requestMatchers("/history/set_one_history_node").permitAll()
                        .requestMatchers("/auth/sign_up").permitAll()
                        .requestMatchers("/auth/log_in").permitAll()
                        .requestMatchers("/ws-endpoint").permitAll()
                        .requestMatchers("/ws-endpoint/**").permitAll()
                        .requestMatchers("/topic/citizen").permitAll()
                        .requestMatchers("/", "/static/**", "/index.html", "/favicon.ico", "/manifest.json").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
                .cors((cors) -> cors.disable())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager(
    //         AuthenticationConfiguration configuration) throws Exception {
    //     return configuration.getAuthenticationManager();
    // }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails userDetails = User.withDefaultPasswordEncoder()
    //             .username("user")
    //             .password("password")
    //             .authorities("ROLE_USER")
    //             .build();

    //     return new InMemoryUserDetailsManager(userDetails);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
