package testCases;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DELETE_Or_Delete_A_Product {

	SoftAssert softAssert = new SoftAssert();

	@Test
	public void delete_A_product() {

		HashMap payload = new HashMap();
		payload.put("id", "1498");
		payload.put("name", "Automation basic 3.0");
		payload.put("price", "150");
		payload.put("description", "Best book you can buy!");
		payload.put("category_id", "6");

		Response response = 
				given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8")
				.body(payload).
				when()
				.delete("/delete.php").
				then()
				.extract().response();

		// diff types of assertions
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		softAssert.assertEquals(statusCode, 200, "Status code not matching");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);

		JsonPath js = new JsonPath(responseBody);
		String deleteMessage = js.getString("message");
		System.out.println("Delete Message: " + deleteMessage);
		softAssert.assertEquals(deleteMessage, "Product was deleted.", "Product was not Deleted!");

		softAssert.assertAll();
	}

	/*
	 * @Test public void read_A_product() { SoftAssert softAssert = new
	 * SoftAssert();
	 * 
	 * Response response = given()
	 * .baseUri("https://techfios.com/api-prod/api/product") .header("Content-Type",
	 * "application/json; charset=UFT-8") .queryParam("id", "1500"). // usually
	 * standard way to do it when() .get("/read_one.php").
	 * //.get("/read_one.php?id=1474"). is also okay to go with instead of line 19.
	 * 
	 * then() .extract().response();
	 * 
	 * int statusCode = response.getStatusCode(); System.out.println("Status Code: "
	 * + statusCode); Assert.assertEquals(statusCode, 200);
	 * 
	 * //long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
	 * //if(responseTime<=2000) { //
	 * System.out.println("response is within the range"); //}else {
	 * //System.out.println("response is out the range"); //}
	 * 
	 * String responseBody = response.getBody().asString();
	 * System.out.println("Response Body: " + responseBody);
	 * 
	 * // System.out.println("Response:" + response.asString());
	 * 
	 * JsonPath js = new JsonPath(responseBody); String productId =
	 * js.getString("id"); System.out.println("Product Id: " + productId);
	 * softAssert.assertEquals(productId, "1500");
	 * 
	 * String productName = js.getString("name");
	 * System.out.println("Product Name: " + productName);
	 * softAssert.assertEquals(productName, "Automation basic 3.0");
	 * 
	 * String productPrice = js.getString("price");
	 * System.out.println("Product Price: " + productPrice);
	 * softAssert.assertEquals(productPrice, "150");
	 * 
	 * softAssert.assertAll();
	 * 
	 * 
	 * }
	 */

}
