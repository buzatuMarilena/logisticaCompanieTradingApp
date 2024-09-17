package mariTime.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/login", "/oauth2/**", "/images/**", "/css/**", "/js/**").permitAll()  // Permite accesul la login și OAuth
                                .requestMatchers("/admin/**").hasRole("ADMIN")  // Permite accesul la URL-urile care conțin /admin/ doar pentru ADMIN
                                .requestMatchers("/x").hasAnyRole("ADMIN", "USER")  // Acces pentru roluri USER și ADMIN
                                .requestMatchers("/z").hasRole("ADMIN")  // Acces doar pentru ADMIN
                                .anyRequest().authenticated()  // Orice altă cerere trebuie să fie autentificată
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)  // Redirecționează către /home după login
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // Folosește aceeași pagină de login și pentru OAuth2
                        .defaultSuccessUrl("/home", true)  // Redirecționează către homepage după login cu OAuth2
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logout.getLogoutSuccessHandler())
                        .permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
