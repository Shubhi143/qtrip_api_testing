package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {

    public static RequestSpecification request;
    public static JsonPath jsonPathEvaluator;
    public static String basurl = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1";
    static Response response1;
    public static String userName ="testUser";
    public static  String Password ="testUser123";

    @Test(description = "Verify new user can be registered and login using APIs of QTrip", groups = {"API TESTING"})
    public static void TestCase04() throws InterruptedException {

     String UserName = userName + UUID.randomUUID()+"@mail.com".toString();
     System.out.println("username is  "+UserName);
     String regUserName = UserName;

     String payLoadRegister="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\",\"confirmpassword\":\"'"+ Password +"'\"}";
        request = RestAssured.given().contentType(ContentType.JSON);
        response1 =  request.body(payLoadRegister).post(basurl+"/register");
        int statuscodeActual1=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual1);
        Assert.assertEquals(statuscodeActual1, 201);
        String newpayLoadRegister =  payLoadRegister.replace("UserName", "regUserName");  
        response1 =request.body(newpayLoadRegister).post(basurl+"/register");
        int statuscodeActual2=response1.getStatusCode();
        System.out.println("the status code is"+statuscodeActual2);
        Assert.assertEquals(statuscodeActual2, 400);
    }

    }

  

