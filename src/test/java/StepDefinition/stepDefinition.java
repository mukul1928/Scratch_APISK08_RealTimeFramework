package StepDefinition;

import Utilities.CreateUserData;
import Utilities.FetchDataFromProperty;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static io.restassured.RestAssured.*;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;

public class stepDefinition {

	String base_URI = FetchDataFromProperty.readDataFromProperty().getProperty("base_uri");
	RequestSpecification req, res;
	ResponseSpecification respec;
	Response response;

	@Given("user starts sending API requests")
	public void user_starts_sending_api_requests() {
		req = new RequestSpecBuilder().setBaseUri(base_URI).setContentType(ContentType.JSON).build();
	}

	@When("user enters the payload data")
	public void user_enters_the_payload_data() throws JsonProcessingException {
		res = given().log().all().header("x-api-key","reqres-free-v1")
				.relaxedHTTPSValidation().spec(req)
				.body(CreateUserData.CreateUser());
	}

	@When("user submits the payload with an endpoint as {string}")
	public void user_submits_the_payload_with_an_endpoint_as(String endpoint) {
			respec = new ResponseSpecBuilder().build();
			response = res.when().post(endpoint).then().log().all().extract().response();
	}

	@Then("validate user gets created and corrsponding status code is {string}")
	public void validate_user_gets_created_and_corrsponding_status_code_is(String status_code) {
			long ResponseTime = response.getTime();
			if(ResponseTime>5000) {
				throw new ArithmeticException("Longer than expected response");
			}else {
				System.out.println("Response time is within threshold");
			}
			
			Integer code =  response.getStatusCode(); // This process is called as Auto boxing it's a wrapper class
			String Actual_Code = code.toString();
			Assert.assertEquals(Actual_Code, status_code);
			JsonPath js = new JsonPath(response.asString());//For validating the body we write this code
			String CreatedDate = js.getString("createdAt");
			System.out.println(CreatedDate);
	}
}
