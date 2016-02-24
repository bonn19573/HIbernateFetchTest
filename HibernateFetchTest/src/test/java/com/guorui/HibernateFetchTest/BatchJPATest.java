package com.guorui.HibernateFetchTest;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.junit.Test;

import com.guorui.HibernateFetchTest.entity.Project;

public class BatchJPATest extends AbstractJPATest {

	@Test
	public void testBatchInsert() {
		int batchSize = 50;

		for (int i = 0; i < 5000; i++) {
			Project project = new Project("project" + i);
			entityManager.persist(project);
			if (++i % batchSize == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}

	}
	
	@Test
	public void testScroll(){
		Session session = entityManager.unwrap(Session.class);
		ScrollableResults scrollableResults = session.createQuery("select p from Project p").setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		while(scrollableResults.next()){
			Project project = (Project) scrollableResults.get(0);
			project.setName(project.getName().startsWith("pro")?project.getName()+"aa":project.getName());
			session.save(project);
			System.out.println(project);
			if(++count % 50 == 0){
				session.flush();
				session.clear();
			}
		}
		
		scrollableResults.close();
	}

}
