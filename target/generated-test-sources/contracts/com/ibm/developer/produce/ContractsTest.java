package com.ibm.developer.produce;

import com.ibm.developer.produce.ContractsBaseClass;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;

public class ContractsTest extends ContractsBaseClass {

	@Test
	public void validate_add_produce() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json;charset=UTF-8")
					.body("{\"name\":\"Kiwi\",\"subName\":\"\",\"quantity\":75}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/api/v1/produce");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['subName']").isEqualTo("");
			assertThatJson(parsedJson).field("['name']").isEqualTo("Kiwi");
			assertThatJson(parsedJson).field("['quantity']").isEqualTo(75);
		// and:
			assertThat(parsedJson.read("$.id", String.class)).matches("[0-9]+");
	}

	@Test
	public void validate_add_produce_bad_client_data() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"name\":\"\",\"subName\":\"\",\"quantity\":75}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/api/v1/produce");

		// then:
			assertThat(response.statusCode()).isEqualTo(400);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['errorMessage']").isEqualTo("Missing required value!");
	}

	@Test
	public void validate_find_all_produce() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();

		// when:
			ResponseOptions response = given().spec(request)
					.get("/api/v1/produce");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(100);
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(1);
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(1000);
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Pineapple");
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(4);
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(3);
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(2);
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Apple");
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("Granny Smith");
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("");
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(50);
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(300);
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Corn");
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("Sweet");
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("Gala");
	}

	@Test
	public void validate_find_produce_by_name() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();

		// when:
			ResponseOptions response = given().spec(request)
					.get("/api/v1/produce/Apple");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(100);
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(1);
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("Granny Smith");
			assertThatJson(parsedJson).array().contains("['quantity']").isEqualTo(50);
			assertThatJson(parsedJson).array().contains("['subName']").isEqualTo("Gala");
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo(2);
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Apple");
	}

}
