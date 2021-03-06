package com.guorui.HibernateFetchTest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;


@Entity
public class Project {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NaturalId(mutable=true)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "employee_project", joinColumns = @JoinColumn(name = "project_id") , inverseJoinColumns = @JoinColumn(name = "employee_id") )
	private List<Employee> employees = new ArrayList<Employee>();

	public Project() {
	}

	public Project(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	protected void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee){
		if(!this.employees.contains(employee)){
			this.employees.add(employee);
			employee.getProjects().add(this);
		}
	}
	
	public void removeEmployee(Employee employee){
		if(this.employees.contains(employee)){
			this.employees.remove(employee);
			employee.getProjects().remove(this);
		}
	}
	

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
