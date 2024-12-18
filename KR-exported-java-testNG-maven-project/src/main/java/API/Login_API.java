package API;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class Login_API {
    public String login(String email, String password) {
        String payload = getLoginPayload(email, password);
        Response response = ApiUtils.getRequestSpec()
                .header("content-type", "application/json")
                .body(payload)
                .post("/api/v1/copilot/physician/login");
        Assert.assertEquals(response.statusCode(), 200, "Verify status code is 200");
        return response.jsonPath().getString("token");
    }




    public static String getLoginPayload(String email, String password) {
        return "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";
    }
}
