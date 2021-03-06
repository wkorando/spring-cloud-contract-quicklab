package com.ibm.developer.quicklab;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ibm.developer.quicklab.Produce;
import com.ibm.developer.quicklab.ProduceController;
import com.ibm.developer.quicklab.ProduceRepo;
import com.ibm.developer.quicklab.ProduceService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ContractsBaseClass {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Rule
	public TestName testName = new TestName();
	
	@Autowired
	private ProduceController controller;
	@Autowired
	private ProduceService service;

	@MockBean
	private ProduceRepo repo;

	@Before
	public void before() throws Throwable {
		when(repo.findAll()).thenReturn(
				Arrays.asList(new Produce(1, "Apple", "Granny Smith", 100), 
						new Produce(2, "Apple", "Gala", 50),//
						new Produce(3, "Corn", "Sweet", 1000), //
						new Produce(4, "Pineapple", "", 300)));
		when(repo.findByName("Apple")).thenReturn(
				Arrays.asList(new Produce(1, "Apple", "Granny Smith", 100), 
						new Produce(2, "Apple", "Gala", 50)));
		when(repo.save(new Produce(0, "Kiwi", "", 75))).thenReturn(new Produce(10, "Kiwi", "", 75));
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
				.standaloneSetup(controller)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document(getClass().getSimpleName() + "_" + testName.getMethodName())));
	}

}
