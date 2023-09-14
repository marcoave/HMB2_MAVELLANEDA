package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity(debug = true)
@Configuration

public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()

                                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.POST,"/api/clients/current/cards").permitAll()
                                .antMatchers(HttpMethod.POST,"/api/transactions").permitAll()
                                .antMatchers(HttpMethod.POST,"/api/loans").permitAll()
                                .antMatchers(HttpMethod.GET,"/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                                .antMatchers(HttpMethod.GET,"/api/transactions").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/api/clients").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/web/accounts/current").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/api/client/current").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/api/client/current/cards").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/api/accounts/**").hasAnyAuthority("ADMIN", "CLIENT")
                                .antMatchers(HttpMethod.GET,"/h2-console/{id}").hasAuthority("ADMIN")
                                //.antMatchers(HttpMethod.GET,"/api/loans").hasAnyAuthority("ADMIN","CLIENT")
                                .antMatchers(HttpMethod.GET,"/h2-console/**").hasAuthority("ADMIN")
                                .antMatchers(HttpMethod.GET,"/rest/**").hasAnyAuthority("ADMIN");



        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");


        http.logout().
                logoutUrl("/api/logout");



        // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
       http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


        return http.build();
        }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session= request.getSession(false);
        if (session !=null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }



    }


    }
//}
/*public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/index.html", "/","h2-console","api/clients").permitAll();
        http.authorizeRequests().antMatchers("/prueba/**").hasAuthority("CLIENT");
        //http.authorizeRequests().antMatchers("/**").hasAuthority("ADMIN");

        http.formLogin()
                .usernameParameter("asd")
                .passwordParameter("pswd")
                .loginPage("/algo/login");

        http.logout().
                logoutUrl("/algo/logout");

        //http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.headers().frameOptions().disable();

        http.csrf().disable();

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session= request.getSession(false);
        if (session !=null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }


    }

}*/
