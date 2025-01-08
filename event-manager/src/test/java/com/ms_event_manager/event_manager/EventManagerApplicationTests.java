package com.ms_event_manager.event_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.mongodb.assertions.Assertions.assertNotNull;

@SpringBootTest
class EventManagerApplicationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void testConnection() {//ok its working
		assertNotNull(mongoTemplate.getDb());
	}

}
