package org.testNodeRedApi;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import java.io.InputStream;

public class NodeRedAPITests {
    private RequestSpecification requestSpec;

    @BeforeTest
    public void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://test-cloud.sgsconnect.one").
                setPort(1880).
                build();
    }
    @Test(testName = "Check SPC Config Response Data", suiteName = "Test Node-Red APIs")
    public void getSPCConfigTest(){

        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then()
                .assertThat().body("spc_identifier",hasItem("FC:0F:E7:E9:47:3F"))
                .assertThat().body("configuration_version", hasItem(29));
    }

    @Test(testName = "Test SPC Config Schema", suiteName = "Test Node-Red APIs")
    public void getSPCConfigSchemaValidationTest(){
        InputStream spcConfigSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("SPCConfig.json");
        assert spcConfigSchema != null;
        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then().statusCode(200).and()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema (spcConfigSchema));
    }
}
