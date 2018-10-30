package be.vdab.personeel.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.personeel.entities.Employee;
import be.vdab.personeel.entities.JobTitle;
import be.vdab.personeel.valueobjects.SocialSecurityNumber;

public interface EmployeeService {

	Optional<Employee> read(final long employeeId);
	
	Optional<Employee> findHighestRanking();
	
	List<Employee> findSubordinates(final Employee supervisor);
	List<Employee> findByJobTitle(final JobTitle jobTitle);
	
	void raiseSalary(final long employeeId, final BigDecimal raise);
	void saveSSN(final long employeeId, final SocialSecurityNumber number);
}
