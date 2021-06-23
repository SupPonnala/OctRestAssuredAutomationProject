package testCases;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GET_Or_Read_A_Product {

	@Test
	public void read_A_product() {
		SoftAssert softAssert = new SoftAssert();
		
		Response response =  
		given()
			.baseUri("https://techfios.com/api-prod/api/product/read.php")
			.header("Content-Type", "application/json; charset=UFT-8")
			.queryParam("id", "1474"). // usually standard way to do it.
			
		when()
			.get("/read_one.php").
			//.get("/read_one.php?id=1474"). is also okay to go with instead of line 19.
			
		then()
			.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		Assert.assertEquals(statusCode, 200);
		
		//long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		//if(responseTime<=2000) {
		//	System.out.println("response is within the range");
		//}else {
			//System.out.println("response is out the range");
		//}
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);
		
		JsonPath js = new JsonPath(responseBody);
		String productId = js.getString("id"); // using diff resource in the response section to validate
		System.out.println("Product Id: " + productId);
		//Assert.assertEquals(productId, "1474");
		softAssert.assertEquals(productId, "1473", "Procuct Id is not Matching!");
		
		String productName = js.getString("name"); //using diff resource in the response section to validate
		System.out.println("Product Name: " + productName);
		//Assert.assertEquals(productName, "1474");
		softAssert.assertEquals(productName, "HP Laptop 3.0 ", "Procuct Name is not Matching!");
		
		String productPrice = js.getString("price"); //using diff resource in the response section to validate
		System.out.println("Product Price: " + productPrice);
		//Assert.assertEquals(productPrice, "899");
		softAssert.assertEquals(productPrice, "899", "Procuct Price is not Matching!");
		
		softAssert.assertAll();
	
		
	}

}
