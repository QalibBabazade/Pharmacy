package az.babazade.pharmacy.config;

import az.babazade.pharmacy.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable();
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/user/**").permitAll()
                .antMatchers("/category/**").hasAnyRole("ADMIN","USER").antMatchers("/company/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/connector/**").hasRole("ADMIN").antMatchers("/drug/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/sales/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated().and().httpBasic();
    }



    @Bean
    public PasswordEncoder getPasswordEncoder() {
        //return new PasswordEncoder();

        return  NoOpPasswordEncoder.getInstance();
    }
}
