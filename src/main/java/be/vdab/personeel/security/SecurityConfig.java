package be.vdab.personeel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String AUTH_PRESIDENT = "President";
//	private static final String AUTH_EMEA = "Sale Manager (EMEA)";
//	private static final String AUTH_APAC = "Sales Manager (APAC)";
//	private static final String AUTH_NA = "Sales Manager (NA)";
//	private static final String AUTH_SALES = "Sales Rep";
//	private static final String AUTH_MARKETING_VP = "VP Marketing";
//	private static final String AUTH_SALES_VP = "VP Sales";
//	
//	private static final String USERS_BY_USERNAME
//	= "SELECT email AS username, paswoord AS password " +
//			"FROM werknemers WHERE email = ?";
//	private static final String AUTHORITIES_BY_USERNAME
//	= "SELECTwerknemers.email AS username, jobtitels.naam AS authorities " +
//			"FROM werknemers INNER JOIN jobtitels " +
//			"ON jobtitels.id = werknemers.jobtitelid " +
//			"WHERE werknemers.email = ?";
	
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
//	@Bean
//	public JdbcDaoImpl jdbcDaoImpl(final DataSource dataSource) {
//		final JdbcDaoImpl impl = new JdbcDaoImpl();
//		
//		impl.setDataSource(dataSource);
//		impl.setUsersByUsernameQuery(USERS_BY_USERNAME);
//		impl.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
//		
//		return impl;
//	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider
		= new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		
		return authProvider;
	}
	
	@Override
	public void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	public void configure(final WebSecurity web) {
		web.ignoring()
			.antMatchers("/resources/**", "/static/**")
			.mvcMatchers("/images/**")
			.mvcMatchers("/css/**")
			.mvcMatchers("/js/**");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/login").permitAll().and()
			.logout();
		
		http.authorizeRequests()
			.mvcMatchers("/employees/*/raise/**").hasAuthority(AUTH_PRESIDENT)
			.mvcMatchers("/employees/**").authenticated()
			.mvcMatchers("/", "/titles/**").permitAll()
			.mvcMatchers("/**").authenticated();
	}
}
