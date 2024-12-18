package API;

import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;

public class UploadMedicalAuthRecord_API {

    Response response ;
    public Response uploadMedicalAuthRecord(String token , File authFile , String authID)
    {
        response =  ApiUtils.getRequestSpec()
                .header("content-type", "multipart/form-data")
                .header("authcode",  token)
                .multiPart("attachment" , authFile , "application/pdf")
                .put("/api/v1/medicalAuthorizations/"+authID+"/attachment/upload");
        Assert.assertEquals(response.statusCode(), 200, "Verify status code is 200");

        return  response ;
    }
}
