package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;
public class testCase_API_02 {

    public static RequestSpecification request;
    public static JsonPath jsonPathEvaluator;
    public static String basurl = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1";
    static Response response1;
    public static String userName ="testUser@gmail.com";
    public static  String Password ="testUser123";

    @Test(description = "Verify that the search City API Returns the correct number of results", groups = {"API TESTING"})
    public static void TestCase02() throws InterruptedException {

     String UserName = userName + UUID.randomUUID().toString();
     String payLoadRegister="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\",\"confirmpassword\":\"'"+ Password +"'\"}";
        request = RestAssured.given().contentType(ContentType.JSON);
        response1 = request.body(payLoadRegister).post(basurl+"/register");
        int statuscodeActual1=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual1);
        Assert.assertEquals(statuscodeActual1, 201);
        String payLoadLogin="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\"}";
        response1 = request.body(payLoadLogin).post(basurl+"/login");
        int statuscodeActual2=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual2);
        Assert.assertEquals(statuscodeActual2, 201);
        jsonPathEvaluator= response1.jsonPath();
		boolean actualresloginsuccess = jsonPathEvaluator.get("success");
        Assert.assertTrue(actualresloginsuccess);
        String validtoken =jsonPathEvaluator.get("data.token").toString();
        String userid = jsonPathEvaluator.get("data.id").toString();
        response1=request.get(basurl+"/cities?q=beng");
        int statuscodeActual3=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual3);
        Assert.assertEquals(statuscodeActual3, 200);
        jsonPathEvaluator= response1.jsonPath();
        String schema = response1.getContentType().toString();
        System.out.println("the schema is"+schema);
        String description =jsonPathEvaluator.get("[0].description").toString();
        System.out.println("the description is"+description);
        Assert.assertEquals(description, "100+ Places");


       
    }



}
