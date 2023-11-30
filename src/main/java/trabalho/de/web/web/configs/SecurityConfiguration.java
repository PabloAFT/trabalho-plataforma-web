package trabalho.de.web.web.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import trabalho.de.web.web.user.UserService;

@EnableWebSecurity()
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // .cors(withDefaults())
                .csrf((csrf) -> csrf.disable())

                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/index.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public/**").permitAll()

                        .anyRequest().authenticated()

                )
                .formLogin(login -> login.loginPage("/login.html"))

                .securityContext((securityContext) -> securityContext
                        .requireExplicitSave(false)
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(),
                                new HttpSessionSecurityContextRepository())))

        // .httpBasic(Customizer.withDefaults()).logout(Customizer.withDefaults())
        // .formLogin(Customizer.withDefaults())
        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserService userDetailService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
