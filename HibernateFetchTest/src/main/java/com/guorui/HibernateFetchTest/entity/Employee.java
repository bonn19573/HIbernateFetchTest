package com.guorui.HibernateFetchTest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.NaturalId;

@Entity
@NamedEntityGraph(name="employee.projects",attributeNodes=@NamedAttributeNode("projects"))
@FetchProfile(
	    name = "employee.projects",
	    fetchOverrides = {
	        @FetchProfile.FetchOverride(
	            entity = Employee.class,
	            association = "projects",
	            mode = FetchMode.JOIN
	        )
	    }
	)
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@NaturalId
	private String name;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToMany(mappedBy = "employees",cascade=CascadeType.ALL)
	private List<Project> projects = new ArrayList<Project>();

	public Employee() {
	}

	public Employee(String name) {
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

	public Department getDepartment() {
		return department;
	}

	protected void setDepartment(Department department) {
		this.department = department;
	}
	
	public void addDepartment(Department department) {
		this.department = department;
		if (!department.getEmployees().contains(this)) {
			department.getEmployees().add(this);
		}
	}


	public void removeDepartment() {
		if (this.department != null) {
			this.department = null;
			this.department.getEmployees().remove(this);
		}
	}

	public List<Project> getProjects() {
		return projects;
	}

	protected void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project){
		if(!this.projects.contains(project)){
			this.projects.add(project);
			project.getEmployees().add(this);
		}
	}
	
	public void removeProject(Project project){
		if(this.projects.contains(project)){
			this.projects.remove(project);
			project.getEmployees().remove(this);
		}
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department.getName() + ", projects=" + projects + "]";
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
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
