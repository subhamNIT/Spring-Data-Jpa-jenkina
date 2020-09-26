package com.sapient.training.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sapient.training.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	public Employee findByName(String name);
	public List<Employee> findByJobAndSalary(String job,Double salary);	
	
//	@Query("select e from Employee e where e.job= :pjob AND e.salary > :psal")
//	public List<Employee> 
//			findByJobAndGreaterThanSalary(@Param("pjob") String job,
//											@Param("psal") Double salary);
	
	@Query("SELECT e FROM Employee e WHERE e.job = ?1 AND e.salary > ?2")
    List<Employee> findByJobAndSalaryGreaterThan(String job, Double salary);
}
