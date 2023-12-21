package qtriptest.APITests;
import io.restassured.http.ContentType;
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
import java.util.UUID;


public class testCase_API_01 {

    public static RequestSpecification request;
    public static JsonPath jsonPathEvaluator;
    public static String basurl = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1";
    static Response response1;
   public static String userName ="testUser@gmail.com";
   public static  String Password ="testUser123";

   @Test(description = "Verify new user can be registered and login using APIs of QTrip", groups = {"API TESTING"})
    public static void TestCase01() throws InterruptedException {

     String UserName = userName + UUID.randomUUID().toString();
     String payLoadRegister="{\"email\":\"'"+ UserName +"'\",\"password\":\"'"+ Password +"'\",\"confirmpassword\":\"'"+ Password +"'\"}";
        request = RestAssured.given();
        request.contentType(ContentType.JSON);
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
		boolean actualresloginsuccess = jsonPathEvaluator.get("success");
        Assert.assertTrue(actualresloginsuccess);
        String validtoken =jsonPathEvaluator.get("data.token");
        String userid = jsonPathEvaluator.get("data.id");
        Assert.assertNotNull(validtoken);
        Assert.assertNotNull(userid);		
    }
    
   
}
