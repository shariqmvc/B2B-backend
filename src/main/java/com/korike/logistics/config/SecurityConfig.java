package com.korike.logistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.korike.logistics.jwt.JwtConfigurer;
import com.korike.logistics.jwt.JwtTokenProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Bean
//	CorsFilter corsFilter() {
//		CorsFilter filter = new CorsFilter();
//		return filter;
//	}
	@Autowired
    JwtTokenProvider jwtTokenProvider;
	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
//			}
//		};
//	}
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.addAllowedHeader("*");
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("*"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//@Bean
//CorsConfigurationSource corsConfigurationSource() {
//	CorsConfiguration configuration = new CorsConfiguration();
//	configuration.setAllowedHeaders(Arrays.asList("*"));
//	configuration.setAllowedOrigins(Arrays.asList("*"));
//	configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	source.registerCorsConfiguration("/**", configuration);
//	return source;
//}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off

		http.csrf().disable()
				.cors().and()
//				configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//		http.csrf().disable()
//				 .requiresChannel(channel ->
//				 channel.anyRequest().requiresSecure())
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and()
		 .authorizeRequests()
		 .antMatchers("/authUser/**","/api/consumer/services/**","/api/upload/**", "/api/partner/**", "/service-status/**").permitAll()
	//	 .antMatchers("/authUser/**","/api/consumer/**").permitAll()
		 .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/**","/swagger-ui.html").permitAll() 
		 .antMatchers("/springfox*").permitAll()
//		 .antMatchers(HttpMethod.POST, "/clients/**").permitAll()
		 .anyRequest().authenticated()
		 .and().apply(new JwtConfigurer(jwtTokenProvider));

        //@formatter:on

    }

}
