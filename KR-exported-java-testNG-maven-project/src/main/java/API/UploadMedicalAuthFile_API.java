package API;

import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;


public class UploadMedicalAuthFile_API {

    Response response ;
    public Response uploadMedicalAuthFile(String token , File authFile , String portal)
    {
        response =  ApiUtils.getRequestSpec()
                .header("content-type", "multipart/form-data")
                .header("authcode",  token)
                .multiPart("portal", portal)
                .multiPart(authFile)
                .post("/api/v1/medicalAuthorizations/upload");

        Assert.assertEquals(response.statusCode(), 200, "Verify status code is 200");

        return  response ;
    }




}
