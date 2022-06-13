package com.abhishek.springsecurityjune9th.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.abhishek.springsecurityjune9th.config.jwt.AuthTokenFilter;
import com.abhishek.springsecurityjune9th.config.jwt.JwtAuthenticationEntryPoint;
import com.abhishek.springsecurityjune9th.service.CustomerServiceImpl;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	@Autowired
	CustomerServiceImpl customerService;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.userDetailsService(customerService)
		.passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		// default
		
		/**
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.and()
		.httpBasic();
		
		*/
		// customize
		
		/**
		http.authorizeRequests()
		.antMatchers("/welcome/**").permitAll()
		.antMatchers("/customer/**").authenticated()
		.antMatchers("/h2-console/**").authenticated()
		.and()
		.formLogin()
		.and()
		.httpBasic();
		*/
		
		// deny all
		
		/**
		http.authorizeRequests()
		.anyRequest().denyAll()
		.and()
		.formLogin()
		.and()
		.httpBasic();
		*/
		
		http.csrf().disable();
		
		// to display h2-console
		
		http.headers().frameOptions().disable();
		
		// authorization based
		
		http.authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.antMatchers("/welcome/**").hasAuthority("read")
		.antMatchers("/customer/**").hasAuthority("admin")
		.antMatchers("/h2-console/**").hasAnyAuthority("db_admin", "admin")
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.formLogin()
		.and()
		.httpBasic();
		
		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
//		http.addFilterAfter(customHeaderAuthFilter(), BasicAuthenticationFilter.class);
	}

	/**
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
		.withUser("axess1").password("12345").authorities("admin")
		.and()
		.withUser("axess2").password("12345").authorities("read")
		.and()
		.withUser("axess3").password("12345").authorities("db_admin")
		.and()
		.passwordEncoder(NoOpPasswordEncoder.getInstance());
		
	}
	*/
	
	/**
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		
		UserDetails user1 = User.withUsername("axess1@xyz.com")
				.password("12345").authorities("admin").build();
		
		UserDetails user2 = User.withUsername("axess2@xyz.com")
				.password("12345").authorities("read").build();
		
		UserDetails user3 = User.withUsername("axess3@xyz.com")
				.password("12345").authorities("db_admin").build();
		
		
		userDetailsService.createUser(user1);
		userDetailsService.createUser(user2);
		userDetailsService.createUser(user3);
		
		auth.userDetailsService(userDetailsService);
		
	}
	*/
	
	/**
	@Bean
	public UserDetailsService userDetailsService(DataSource datasource) {
		
		return new JdbcUserDetailsManager(datasource);
	}
	*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public CustomHeaderAuthFilter customHeaderAuthFilter() {
		return new CustomHeaderAuthFilter();
	}
	

	

}


































