package StepDefinition;

import Utilities.CreateUserData;
import Utilities.FetchDataFromProperty;
import Utilities.GetDataExcel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;

public class stepDefinition {

	String base_URI = FetchDataFromProperty.readDataFromProperty().getProperty("base_uri");
	String base_URI2 = FetchDataFromProperty.readDataFromProperty().getProperty("gorest_uri");
	RequestSpecification req, res;
	ResponseSpecification respec;
	Response response;
	String authToken;
	String access_token;

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
	
	@Given("user hits the site of Go rest with URI")
	public void user_hits_the_site_of_go_rest_with_uri() {
		req = new RequestSpecBuilder().setBaseUri(base_URI2).setContentType(ContentType.JSON).build();
	}

	@Given("user validates himself with auth token")
	public void user_validates_himself_with_auth_token() {
		authToken = FetchDataFromProperty.readDataFromProperty().getProperty("authToken");
	}

	@Given("user passes the payload with all details")
	public void user_passes_the_payload_with_all_details() throws IOException {
	   res = given().log().all().headers("Authorization",authToken).relaxedHTTPSValidation()
	   .spec(req).body(GetDataExcel.getExcelData());
	}

	@When("user hits the server with {string}")
	public void user_hits_the_server_with(String endpoint) {
		response = res.when().post(endpoint).then().log().all().extract().response();
	}

	@Then("validate a new user is created with status code as {string}")
	public void validate_a_new_user_is_created_with_status_code_as(String status_code) {
		Integer code = response.getStatusCode();
		String actualStatusCode = code.toString();
		Assert.assertEquals(actualStatusCode, status_code);
	}

	@Then("validate the details in response body")
	public void validate_the_details_in_response_body() {
		JsonPath js = new JsonPath(response.asString());
		int id = js.get("id");
	}
	
	@Given("user hit the paypal site")
	public void user_hit_the_paypal_site() {
	   
	}

	@Given("user tries to get access token")
	public void user_tries_to_get_access_token() {
		
		RestAssured.baseURI = "https://api-m.sandbox.paypal.com";
		String client_id = "AX1JimYNtpUrYvFVtJOG_L-NS4Lfn5MbFmH2uBfhPJtxndz0o9eRCOuVDPU3IZxIfEx1L0_i7HgvWTpt";
		String client_secret_id = "EBdzETNCGESs2lDeGJc6b7_-7p2C0L4rsrFXpRbizYDsd5H9a8PIiufjIprpOcx0JjvbPO0fnjDqmZfy";

		String Response = given().log().all()
				.header("content-type", "application/x-www-form-urlencoded")
				.param("grant_type", "client_credentials")
				.auth().preemptive().basic(client_id, client_secret_id)
				.when().post("v1/oauth2/token")
				.then().log().all().extract().response().asString();
		
		
		JsonPath js = new JsonPath(Response);
		access_token = js.getString(access_token);
	}

	@Given("user fetches the actual token from access token")
	public void user_fetches_the_actual_token_from_access_token() {
		
		RestAssured.baseURI = "https://api-m.sandbox.paypal.com";
		String Res = given().log().all().queryParam("page",3)
		.queryParam("page_size",4).queryParam("total_count_required",true)
		.auth().oauth2(access_token)
		.when().get("v1/invoicing/invoices")
		.then().log().all().extract().response().asString();
		System.out.println(Res);
	}
}
