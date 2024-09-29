package org.raghav.smartcontactmanager.Configurations;

import org.raghav.smartcontactmanager.Configurations.AuthenticationHandlers.OAuthFailureAuthenticationHandler;
import org.raghav.smartcontactmanager.Configurations.AuthenticationHandlers.OAuthSuccessAuthenticationHandler;
import org.raghav.smartcontactmanager.Implementations.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("admin123").roles("ADMIN","USER").build();
//
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
//        return inMemoryUserDetailsManager;
//    }

    private SecurityCustomUserDetailService userDetailsService;
    @Autowired
    private OAuthSuccessAuthenticationHandler oAuthSuccessAuthenticationHandler;
    @Autowired
    private OAuthFailureAuthenticationHandler oAuthFailureAuthenticationHandler;

    public SecurityConfig(SecurityCustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // configuration for authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // configured the urls to make urls private and public
        httpSecurity.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        httpSecurity.formLogin((formLogin) -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        httpSecurity.logout((logoutForm) -> {
           logoutForm.logoutUrl("/do-logout");
           logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.oauth2Login((oAuth2Login) -> {
            oAuth2Login.loginPage("/login");
            oAuth2Login.successHandler(oAuthSuccessAuthenticationHandler);
            oAuth2Login.failureHandler(oAuthFailureAuthenticationHandler);
        });

//        httpSecurity.oauth2Login(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
