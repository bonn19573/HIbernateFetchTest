package com.guorui.HibernateFetchTest;

import java.util.Collections;

import org.junit.Test;

import com.guorui.HibernateFetchTest.entity.Employee;

public class FetchJPATest extends AbstractJPATest {
	@Test
	public void testJoinByJPAEntityGraph(){
		Long id = 13L;

		
		Employee employee = entityManager.find(Employee.class, id, 
				Collections.singletonMap(
				        "javax.persistence.fetchgraph",
				        (Object)entityManager.getEntityGraph( "employee.projects" )
				    ));
		
		System.out.println(employee);
	}
}
