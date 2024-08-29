package org.testNodeRedApi;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestNGListener;
import org.testng.annotations.*;

import java.io.InputStream;

public class NodeRedAPITests implements ITestNGListener {
    private RequestSpecification requestSpec;

    @BeforeTest
    public void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://test-cloud.sgsconnect.one").
                setPort(1880).
                build();
    }
    @Test
    public void getLinkConfigTest(){

        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then()
                .assertThat().body("spc_identifier",hasItem("FC:0F:E7:E9:47:3F"))
                .assertThat().body("configuration_version", hasItem(29));
    }

    @Test
    public void getLinkConfigSchemaValidationTest(){
        InputStream linkConfigSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("LinkConfig.json");
        assert linkConfigSchema != null;
        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then().statusCode(200).and()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema (linkConfigSchema));
    }
}
