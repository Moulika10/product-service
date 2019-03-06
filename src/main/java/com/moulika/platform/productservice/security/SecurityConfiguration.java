package com.moulika.platform.productservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
        web.ignoring().antMatchers("/ping");
        web.ignoring().antMatchers("/actuator/**");
        web.ignoring().antMatchers("/v2/api-docs");
    }

    /**
     * Custom overridden method for configuring based on HttpSecurity parameter
     *
     * @param http HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .authorizeRequests()
                /**
                 * Uncomment this line when we you have OAuthServer
                 */
//            .antMatchers( "/v1/**").hasAuthority("openId");
                /**
                 * Comment this line when we you have OAuthServer
                 * as no oauth Server permitting all
                 */
            .antMatchers( "/v1/**").permitAll();

//        http.addFilterBefore(
//                new JwtAuthenticationTokenFilter(
//                        new JwtTokenVerifier()),
//                BasicAuthenticationFilter.class);
    }

}
