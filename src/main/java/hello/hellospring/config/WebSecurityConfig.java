package hello.hellospring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                    .antMatchers("/", "/login", "/autoLogin").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .successForwardUrl("/loginSuccess")
                    .failureForwardUrl("/loginFail")
                    .permitAll()
                    .and()
                .logout()
                    .invalidateHttpSession(true)
                    .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select name, password, enable "
                        + "from MEMBER "
                        + "where name = ?")
                .authoritiesByUsernameQuery("select name, role "
                        + "from MEMBER "
                        + "where name = ?");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
