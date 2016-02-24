package com.guorui.HibernateFetchTest;

import org.junit.Test;

import com.guorui.HibernateFetchTest.entity.Department;
import com.guorui.HibernateFetchTest.entity.Employee;
import com.guorui.HibernateFetchTest.entity.Project;

public class FetchTest extends AbstractTest {
	
	@Test
	public void testSave(){
		Department department = new Department("development");
		
		Employee employee = new Employee("Guo Rui");
		Employee employee2 = new Employee("haha");
		department.addEmployee(employee);
		department.addEmployee(employee2);

		session.save(department);
		
		Project project = new Project("PROMIS");
		Project project2 = new Project("Matrix");
		Project project3 = new Project("Survey");
		employee.addProject(project);
		employee.addProject(project2);
		
		employee2.addProject(project);
		employee2.addProject(project3);
		
		session.save(employee);
		session.save(employee2);
	}
	
	@Test
	public void testNoFetch(){
		Employee employee = (Employee) session.createQuery("select e from Employee e where e.name = :name").setParameter("name", "Guo Rui").uniqueResult();
		
		System.out.println(employee);
	}
	
	@Test 
	public void testJoinFetch(){
		String queryString = "select e from Employee e left join fetch e.projects where e.name = :name";
		Employee employee = (Employee) session.createQuery(queryString).setParameter("name", "Guo Rui").uniqueResult();
		
		System.out.println(employee);
	}
	

	@Test
	public void testFetchProfile(){
		
		session.enableFetchProfile( "employee.projects" );
		Employee employee = session.bySimpleNaturalId( Employee.class ).load( "Guo Rui" );
		
		System.out.println(employee);
	}
}
