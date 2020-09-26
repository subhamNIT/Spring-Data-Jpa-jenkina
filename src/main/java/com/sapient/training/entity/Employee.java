package com.sapient.training.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "employee_sapient3")
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 20)
    private String name;   
    private String job;
    @Temporal(TemporalType.DATE) 
    private Date hiredate;
    private Double salary;    
   

    public Employee(String name) {
        this.name = name;
    }      

    public Employee( @Size(min = 3, max = 20) String name, Date hiredate,String job, Double salary) {
		super();		
		this.name = name;
		this.hiredate = hiredate;
		this.salary=salary;
		this.job=job;
	}

	    
}