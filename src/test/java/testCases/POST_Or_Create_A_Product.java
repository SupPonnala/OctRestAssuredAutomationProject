package testCases;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class POST_Or_Create_A_Product {

	@Test
	public void create_A_product() {
		SoftAssert softAssert = new SoftAssert();

		HashMap payload = new HashMap();
		payload.put("name", "Automation Basic 1.0");
		payload.put("price", "100");
		payload.put("description", "Best book you can buy!");
		payload.put("category_id", "6");

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").body(payload).when().post("/create.php")
				.then().extract().response();
		// diff types of assertions
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		softAssert.assertEquals(statusCode, 201);

		String responseHeader = response.header("Content-Type");
		softAssert.assertEquals(responseHeader, "application/json; charset=UTF-8");

		String responseContentLength = response.header("Content-Length");
		softAssert.assertEquals(responseContentLength, "34");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);

		JsonPath js = new JsonPath(responseBody);
		String successMessage = js.getString("message");
		System.out.println("Success Message: " + successMessage);
		softAssert.assertEquals(successMessage, "Product was created.", "Product was not created!");

		softAssert.assertAll();
	}

	@Test
	public void read_A_product() {
		SoftAssert softAssert = new SoftAssert();

		Response response = given().baseUri("https://techfios.com/api-prod/api/product/read.php")
				.header("Content-Type", "application/json; charset=UFT-8").queryParam("id", "1808"). 

				when().get("/read_one.php").
			
				then().extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);

		JsonPath js = new JsonPath(responseBody);
		String productId = js.getString("id"); 
		System.out.println("Product Id: " + productId);
		softAssert.assertEquals(productId, "1808", "Procuct Id is not Matching!");

		String productName = js.getString("name");
		System.out.println("Product Name: " + productName);
		softAssert.assertEquals(productName, "Automation Basic 1.0", "Procuct Name is not Matching!");

		String productPrice = js.getString("price"); 
		System.out.println("Product Price: " + productPrice);
		softAssert.assertEquals(productPrice, "100", "Procuct Price is not Matching!");

		softAssert.assertAll();

	}

}
