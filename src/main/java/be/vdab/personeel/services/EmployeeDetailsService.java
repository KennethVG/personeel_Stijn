package be.vdab.personeel.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.personeel.entities.Employee;
import be.vdab.personeel.repositories.EmployeeRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
@Primary
public class EmployeeDetailsService implements UserDetailsService {

	private static final Logger LOGGER
	= LoggerFactory.getLogger(EmployeeDetailsService.class);
	
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeDetailsService(EmployeeRepository employeeRepository) { super();
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(final String emailAddress)
	throws UsernameNotFoundException {
		final Optional<Employee> employee
		= employeeRepository.findByEmailAddress(emailAddress);
		
		if (!employee.isPresent()) {
			LOGGER.error("SECURITY: Unable to load employee with " +
					"e-mailaddress " + emailAddress);
			return null;
		}
		
		List<GrantedAuthority> auth = AuthorityUtils
				.commaSeparatedStringToAuthorityList(
						employee.get().getJobTitle().getName());
		
		String password = employee.get().getPassword();
		
		password = password.substring("{bcrypt}".length());
		
		
		LOGGER.debug("PASSWORD: " + password);
		
		return new User(emailAddress, password, auth);
	}

}
