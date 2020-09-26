package com.sapient.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sapient.training.data.EmployeeRepository;
import com.sapient.training.entity.Employee;
import com.sapient.training.exception.EmployeeException;
import com.sapient.training.service.EmployeeService;
import com.sapient.training.service.EmployeeServiceImpl;


//@RunWith(SpringRunner.class)  : JUnit 4
//Junit 5 replacement
@ExtendWith(SpringExtension.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        Employee john = new Employee("john");
        john.setId(11L);

        Employee bob = new Employee("bob");
        Employee alex = new Employee("alex");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found;
		try {
			found = employeeService.getEmployeeByName(name);
			assertThat(found.getName()).isEqualTo(name);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }

    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Employee fromDb;
		try {
			fromDb = employeeService.getEmployeeByName("wrong_name");
			assertThat(fromDb).isNull();

	        verifyFindByNameIsCalledOnce("wrong_name");
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void whenValidName_thenEmployeeShouldExist() {
        boolean doesEmployeeExist;
		try {
			doesEmployeeExist = employeeService.exists("john");
			assertThat(doesEmployeeExist).isEqualTo(true);

	        verifyFindByNameIsCalledOnce("john");
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist;
		try {
			doesEmployeeExist = employeeService.exists("some_name");
			assertThat(doesEmployeeExist).isEqualTo(false);

	        verifyFindByNameIsCalledOnce("some_name");
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() {
        Employee fromDb;
		try {
			fromDb = employeeService.getEmployeeById(11L);
			assertThat(fromDb.getName()).isEqualTo("john");

	        verifyFindByIdIsCalledOnce();
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void whenInValidId_thenEmployeeShouldNotBeFound() {
        Employee fromDb;
		try {
			fromDb = employeeService.getEmployeeById(-99L);
			verifyFindByIdIsCalledOnce();
	        assertThat(fromDb).isNull();
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void given3Employees_whengetAll_thenReturn3Records() {
        Employee alex = new Employee("alex");
        Employee john = new Employee("john");
        Employee bob = new Employee("bob");

        List<Employee> allEmployees;
		try {
			allEmployees = employeeService.getAllEmployees();
			 verifyFindAllEmployeesIsCalledOnce();
		        assertThat(allEmployees).hasSize(3)
		        .extracting(Employee::getName)
		        .contains(alex.getName(), john.getName(), 
		        		bob.getName());
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(employeeRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(employeeRepository);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }
}
