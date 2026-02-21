package csd230.lab1.config;

import csd230.lab1.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private final CustomUserDetailsService userDetailsService;


    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public static + login + h2
                        .requestMatchers("/h2-console/**", "/login", "/css/**", "/js/**").permitAll()

                        // Swagger/OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // ✅ REST API OPEN (all verbs)
                        .requestMatchers("/api/rest/**").permitAll()

                        .requestMatchers("/error").permitAll()

                        // Admin-only Thymeleaf pages
                        .requestMatchers("/books/add", "/books/edit/**", "/books/delete/**").hasRole("ADMIN")

                        // Everything else needs login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/books", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        // H2 console frames
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // ✅ Don’t disable CSRF globally; just ignore it for API + H2
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/rest/**"));

        // ✅ Optional: you can REMOVE httpBasic entirely for the lab
        // (it’s not needed if /api/rest/** is permitAll)
        // http.httpBasic(withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // FIX: Pass userDetailsService to the constructor
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
