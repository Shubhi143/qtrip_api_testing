package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class testCase_API_03 {

    public static RequestSpecification request;
    public static JsonPath jsonPathEvaluator;
    public static String basurl = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1";
    static Response response1;
    public Map requestHeaders;
    public static String userName ="testUser";
    public static  String Password ="testUser123";

    @Test(description = "Verify new user can be registered and login using APIs of QTrip", groups = {"API TESTING"})
    public static void TestCase03() throws InterruptedException {

     String UserName = userName + UUID.randomUUID()+"@mail.com".toString();
     System.out.println("username is  "+UserName);


     String payLoadRegister="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\",\"confirmpassword\":\"'"+ Password +"'\"}";
        request = RestAssured.given().contentType(ContentType.JSON);
        response1 =  request.body(payLoadRegister).post(basurl+"/register");
        int statuscodeActual1=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual1);
        Assert.assertEquals(statuscodeActual1, 201);
        String payLoadLogin="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\"}";
        response1 =  request.body(payLoadLogin).post(basurl+"/login");
        int statuscodeActual2=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual2);
        Assert.assertEquals(statuscodeActual1, 201);
        jsonPathEvaluator= response1.jsonPath();
		String actualresloginsuccess = jsonPathEvaluator.get("success").toString();
        Assert.assertEquals(actualresloginsuccess,"true");
        String validtoken =jsonPathEvaluator.get("data.token").toString();
        String userid = jsonPathEvaluator.get("data.id").toString();
        System.out.println("valid token "+validtoken);
        System.out.println("valid userid "+userid);
        Assert.assertNotNull(validtoken);
        Assert.assertNotNull(userid);	
        String bearertoken="Bearer " +validtoken;
        System.out.println("bearer token is   "+bearertoken);
        String paylReser = "{\"userId\":\""+ userid +"\",\"name\":\"testuser\",\"date\":\"2023-11-08\",\"person\":\"1\",\"adventure\":\"2447910730\"}";
        System.out.println("the payload : "+paylReser);
        request = RestAssured.given().contentType(ContentType.JSON);
        request.header("Authorization",bearertoken);
        response1 = request.body(paylReser).post(basurl+"/reservations/new");
        int statuscodeActual3=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual3);
        Assert.assertEquals(statuscodeActual3, 200);
        request = RestAssured.given().contentType(ContentType.JSON);
        request.baseUri(basurl+"/reservations");
        request.header("Authorization",bearertoken);
        request.queryParam("id", userid);
        response1=request.get();
        int statuscodeActual4=response1.getStatusCode();
        System.out.println("the status code is for get reservation is "+statuscodeActual4);
        Assert.assertEquals(statuscodeActual4, 200);
        jsonPathEvaluator= response1.jsonPath();
        String adventureName = jsonPathEvaluator.get("[0].adventureName").toString();
        Assert.assertEquals(adventureName,"Niaboytown");

    }
    

}
