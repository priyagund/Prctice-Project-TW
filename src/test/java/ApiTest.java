import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ApiTest {

    @Test
    void updateUserDetailsTest() throws ParseException {
        RestAssured.baseURI = "https://reqres.in/api/users/";
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject updateData = new JSONObject();
        updateData.put("name", "Aarna");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(updateData.toJSONString());
        Response response = httpRequest.request(Method.PUT,"2");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JsonPath newData = response.jsonPath();
        String name = newData.get("name");
        Assert.assertEquals(name, "Aarna");
    }

    @Test
    void createUserDetailsTest() throws ParseException {
        RestAssured.baseURI = "https://reqres.in/api/users/";
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject updateData = new JSONObject();
        updateData.put("name", "priya");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(updateData.toJSONString());
        Response response = httpRequest.request(Method.POST, "4");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        JsonPath newData = response.jsonPath();
        String name = newData.get("name");
    
    }

        @Test
        void getUserDetailsTest() throws ParseException {
            //The base URI to be used
            RestAssured.baseURI = "https://reqres.in/api/users/";

            //Define the specification of request. Server is specified by baseURI above.
            RequestSpecification httpRequest = RestAssured.given();

            //Makes calls to the server using Method type.
            Response response = httpRequest.request(Method.GET, "2");
            ResponseBody body=response.getBody();
            JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
            //Checks the Status Code
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 200);
        }

    @Test
    void checkAuthorizationTest() {
        RestAssured.baseURI = "https://postman-echo.com/basic-auth";

        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET, "/");

        //Expecting unauthorized status 401
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 401);

        //Passing the Basic Auth username and password
        httpRequest.auth().preemptive().basic("postman", "password");

        //Expecting our Auth works and verifying status code
        Response responseWithAuth = httpRequest.request(Method.GET, "/");
        statusCode = responseWithAuth.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        //Verifying the response body of this API 
        JsonPath jsonValidator = responseWithAuth.jsonPath();
        Boolean authentication = jsonValidator.get("authenticated");
        Assert.assertTrue(authentication);
    }
        
    }

