package com.ibm.developer.produce;

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

	@MockBean
	private ProduceRepo repo;

	@MockBean
	private ProduceService service;

	@Before
	public void before() throws Throwable {
		when(repo.findAll()).thenReturn(
				Arrays.asList(new Produce(1, "Apple", "Granny Smith", 100), 
						new Produce(2, "Apple", "Gala", 50),//
						new Produce(3, "Corn", "Sweet", 1000), //
						new Produce(4, "Pineapple", "", 300)));
		when(service.findProduceByName("Apple")).thenReturn(
				Arrays.asList(new Produce(1, "Apple", "Granny Smith", 100), 
						new Produce(2, "Apple", "Gala", 50)));
		when(service.findProduceByName("+")).thenThrow(new ClientException("Produce name must be alpha numeric!"));
		when(service.addNewProduce(new Produce(0, "Kiwi", "", 75))).thenReturn(new Produce(10, "Kiwi", "", 75));
		when(service.addNewProduce(new Produce(0, "", "", 75))).thenThrow(new ClientException("Missing required value!"));
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
				.standaloneSetup(controller)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document(getClass().getSimpleName() + "_" + testName.getMethodName())));
	}

}
