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
                .authorizeHttpRequests((requests) -> requests
                        // 1. Allow public access to specific endpoints
                        .requestMatchers("/h2-console/**", "/login", "/css/**", "/js/**").permitAll()

                        // 2. Admin only endpoints (CRUD operations on books)
                        .requestMatchers("/books/add", "/books/edit/**", "/books/delete/**").hasRole("ADMIN")

                        // 3. All other requests (view books, cart) require login
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/books", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );


        // Required for H2 Console to work with Spring Security (it uses frames)
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        // Disable CSRF specifically for H2 Console
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));


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
