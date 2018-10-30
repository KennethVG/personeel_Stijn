package be.vdab.personeel.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import be.vdab.personeel.entities.Employee;
import be.vdab.personeel.services.EmployeeService;
import org.springframework.stereotype.Component;

@Component
public class IdToEmployeeConverter implements Converter<Long, Employee> {

    private final EmployeeService employeeService;

    @Autowired
    public IdToEmployeeConverter(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee convert(Long source) {
        return employeeService.read(source).get();
    }
}
