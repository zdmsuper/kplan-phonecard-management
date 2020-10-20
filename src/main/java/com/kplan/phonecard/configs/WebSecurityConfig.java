package com.kplan.phonecard.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kplan.phonecard.security.LoginUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return new BCryptPasswordEncoder();
	}

	@Autowired
	LoginUserDetailsService userDetailsService;
//	@Bean
//	UserDetailsService detailsService() {
//		return new LoginUserDetailsService();
//	}

	/**
	 * 用于提供其他地方注入AuthenticationManager
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}

//	/*也可以不写上述configure(AuthenticationManagerBuilder auth)方法，但需要在Security配置类（WebSecurityConfig）中添加以下内容：
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//	    authenticationProvider.setUserDetailsService(detailsService());
//	    authenticationProvider.setPasswordEncoder(passwordEncoder());
//	    return authenticationProvider;
//	}*/
	private String[] antPatterns = { "/lib/**", "/ui/**" };

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(antPatterns);
		// web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		PathRequest.toStaticResources().atCommonLocations();
//		http.requestMatcher(PathRequest.toStaticResources().atCommonLocations()).authorizeRequests().
		http.authorizeRequests().and().headers().frameOptions().disable();
		http.authorizeRequests().antMatchers(antPatterns)

				// .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll().and().authorizeRequests().anyRequest().authenticated().and().formLogin()
				.usernameParameter("loginId").passwordParameter("loginPwd").loginPage("/login")
				.loginProcessingUrl("/login/doLogin").defaultSuccessUrl("/login/success", true)
				// .failureUrl("/access/fail")
				.failureForwardUrl("/login/fail").permitAll()
				// 如果配置了invalidSessionUrl,则在退出的时候不会再跳转到logoutSuccessUrl
				// .invalidSessionUrl("/login/invalidSession")
				.and().rememberMe().tokenValiditySeconds(1209600).and().logout().logoutUrl("/login/doLogout")
				.invalidateHttpSession(true).logoutSuccessUrl("/login/logout").permitAll().and().csrf().disable();

//		// loginProcessingUrl: 提交到这个路径的请求被作为登录请求,将对此请求进行登录验证(只是一个路径标识)
//		// invalidSessionUrl :提交到这个路径的请求,将视作清除session,即 退出登录请求(只是一个路径标识)
//		http.headers().and().authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//				.permitAll().anyRequest().authenticated()
//				// .fullyAuthenticated()
//				.and().formLogin().usernameParameter("loginId").passwordParameter("loginPwd").loginPage("/login")
//				.loginProcessingUrl("/login/doLogin").defaultSuccessUrl("/login/success", true)
//				// .failureUrl("/access/fail")
//				.failureForwardUrl("/login/fail").permitAll()
//				// 如果配置了invalidSessionUrl,则在退出的时候不会再跳转到logoutSuccessUrl
//				// .invalidSessionUrl("/login/invalidSession")
//				.and().rememberMe().tokenValiditySeconds(1209600).and().logout().logoutUrl("/login/doLogout")
//				.invalidateHttpSession(true).logoutSuccessUrl("/login/logout").permitAll().and().csrf().disable();
		http.sessionManagement().maximumSessions(1);
	}
}