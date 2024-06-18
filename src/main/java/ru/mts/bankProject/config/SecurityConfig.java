package ru.mts.bankProject.config;

import org.hibernate.engine.spi.ExceptionConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mts.bankProject.services.CustomerDetailsService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerDetailsService customerDetailsService;

    public SecurityConfig(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/error","/auth/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login") // Указываем полный путь к странице логина
                .loginProcessingUrl("/process_login") // URL для обработки логина
                .defaultSuccessUrl("/customer", true) // Успешный вход: перенаправление на /customer
                .failureUrl("/auth/login?error");// Ошибка входа: перенаправление на /auth/login с параметром ?error
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}