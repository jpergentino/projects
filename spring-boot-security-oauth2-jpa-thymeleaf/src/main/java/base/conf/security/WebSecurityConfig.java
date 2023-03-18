package base.conf.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import base.conf.security.oauth.CustomOAuth2User;
import base.conf.security.oauth.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private static final String LOGIN_ERROR_URL = "/login?loginError=true";
	private static final String LOGIN_PROCESSING_URL = "/login_user";
	private static final String LOGIN_MAPPING = "/login";
	private static final String LOGIN_OAUTH_MAPPING = "/oauth/login";
	
	private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
    private CustomOAuth2UserService oauthUserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
	        	.antMatchers("/actuator/**").permitAll() // To access actuator endpoints
	        	.antMatchers("/rest/**").permitAll() // To access REST endpoints
	        	.antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/**").permitAll() // To access Swagger UI
	        	.antMatchers("/login*", "/error", "/favicon.ico", "/static/**", "/h2-console/**").permitAll()
	        	.antMatchers("/oauth/**").permitAll() // Used by OAuth2 Authentication
	        	.anyRequest().authenticated()
	        .and()
	        .formLogin()
	        	.loginPage(LOGIN_MAPPING)
	        	.loginProcessingUrl(LOGIN_PROCESSING_URL)
	        	.defaultSuccessUrl("/", true)
//	        	.failureUrl(LOGIN_ERROR_URL) // Alternative to failureHandler
	        	.failureHandler((request, response, exception) -> {
	        		logger.error("Error during login {}", exception);
	        		request.getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
	        	})
	        	.usernameParameter("login")    // IMPORTANT: Define HTML form field using 'name' attribute instead of 'id'
	        	.passwordParameter("password") // IMPORTANT: Define HTML form field using 'name' attribute instead of 'id'
	        .and()
	        	.logout()
		        	.logoutUrl("/logout")
		        	.deleteCookies("JSESSIONID")
	//	        	.logoutSuccessUrl(LOGIN_MAPPING) // Alternative to logoutSuccessHandler
		        	.logoutSuccessHandler(logoutSuccessHandler())
		        	.invalidateHttpSession(true)
	        .and()
	        .exceptionHandling()
		        .accessDeniedHandler(accessDeniedHandler())
		        .authenticationEntryPoint((request, response, authException) -> {
		        	logger.error("Access denied: {}", request.getRequestURI());
		        	request.getRequestDispatcher(LOGIN_MAPPING).forward(request, response);
		        })
	        .and()
	        .headers().frameOptions().sameOrigin()
	        .and()
	        .oauth2Login()
	        	.loginPage(LOGIN_OAUTH_MAPPING)
	        	.userInfoEndpoint()
	        		.userService(oauthUserService)
	        	.and()
	        	.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
						
						CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
						
						logger.info("Authenticated user {}.", oauthUser.getName());
						
						response.sendRedirect("/");
						
					}
				});
        

		return http.build();
	}
	
    private AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
	        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        	
	        	String user = auth != null ? auth.getName() : "<unknown>";

				logger.error("The user "+ user  +" is trying to access "+ request.getRequestURI());
				
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied to "+ request.getRequestURI());
			}
		};
	}
	
	
	private LogoutSuccessHandler logoutSuccessHandler() {
		logger.debug("Configuring logoutSuccessHandler()");
		return new SimpleUrlLogoutSuccessHandler() {
			
			private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	         
	        @Override
	        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	        	
	        	Object user = "<Non auth user>";
	        	if (authentication != null) {
	        		user = authentication.getPrincipal();
	        	}
	        	
	        	logger.info("Logout by {}", user);
	        	request.setAttribute("logout", true);
	            
	            response.sendRedirect(LOGIN_MAPPING);
	            
	        }
	    };
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		
		UserDetails user = userBuilder.username("user").password("123").roles("USER").build();
		UserDetails admin= userBuilder.username("admin").password("123").roles("ADMIN","USER").build();
		
		return new InMemoryUserDetailsManager(admin, user);
	}
	
}