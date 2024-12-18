package API;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    public static RequestSpecification getRequestSpec() {
        return given().log().all()
                .baseUri("https://prodcopilot.ehrcopilotbe.com")
                .header("origin", "https://prodcopilot.ehrcopilotbe.com")
                .header("referer", "https://prodcopilot.ehrcopilotbe.com");
    }
}
