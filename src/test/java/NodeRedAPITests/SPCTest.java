package NodeRedAPITests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SPCTest {
    private RequestSpecification requestSpec; //Variable for generating API request
    private String captureTime;
    @BeforeTest
    public void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://test-cloud.sgsconnect.one"). //Build request URI specifications.
                setPort(1880).
                build();
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));  //Get UTC time
        captureTime = sdf.format(new Date());  //Set capture time to yyyy-MM-dd hh:mm:ss format
    }
    @Test(testName = "Check SPC Config Response Data", suiteName = "Test Node-Red SPC APIs")
    public void getSPCConfigTest(){
        //Make get SPC config API request.
        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then()
                .assertThat().body("spc_identifier",hasItem("FC:0F:E7:E9:47:3F")) //Assertion for Device ID in the response.
                .assertThat().body("configuration_version", hasItem(29));  //Assertion for configuration version in the response.
    }
// Verify the Get SPC Config API request's response schema matches with expected schema in SPCConfig json file.
    @Test(testName = "Test SPC Config Schema", suiteName = "Test Node-Red APIs")
    public void getSPCConfigSchemaValidationTest(){
        //Converting the schema .json file to an InputStream object.
        InputStream spcConfigSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("SPCConfig.json");
        assert spcConfigSchema != null;
        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/spc/FC:0F:E7:E9:47:3F/config")
                .then().statusCode(200).and()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema (spcConfigSchema));
    }

// Send SPC Voltage data with POST request & verify success response.
    @Test(testName = "Send Device Voltage Test", suiteName = "Test Node-Red APIs")
    public void sendSPCVoltageTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"DeviceVoltage\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                " \"acVolt\":\"230\", \"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Boot data Test", suiteName = "Test Node-Red APIs")
    public void sendSPCBootDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SPCBootData\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                " \"ipAddr\":\"124:159:168:126\", \"firmwareVer\": \"V3.2\"," +
                "\"firmwareLastUpdt\": \"2024-03-04 07:11:08\", \"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Health data Test", suiteName = "Test Node-Red APIs")
    public void sendSPCHealthDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SPCHealthData\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                " \"status\":\"Online\", \"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Ping Failure data Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPingStatusDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PingStatus\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                " \"pingSer\":\"Primary\", \"url\":\"bing.com \"," +
                "\"status\": \"Fail\",\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Cycle Data Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerCycleTimeDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PowerCycleTime\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Config Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCConfigSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ConfigSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/acknowledgment")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Config Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCConfigFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ConfigFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/acknowledgment")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Factory Reset Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFactoryResetSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ResetSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Factory Reset Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFactoryResetFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ResetFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Strip Off Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerStripOffSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PowerStripOffSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Strip Off Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerStripOffFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PowerStripOffFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Strip On Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerStripOnSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PowerStripOnSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Strip On Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerStripOnFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"PowerStripOnFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Cycle Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerCycleSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"RebootSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Power Strip On Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCPowerCycleFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"RebootFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Firmware Update Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFWUpdateSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FWUpdateSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\",\n" +
                "\"firmwareVer\": \"V12.5\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Firmware Update On Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFWUpdateFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FWUpdateFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC IP Update Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCIPUpdateSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FWUpdateSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\",\n" +
                "\"firmwareVer\": \"V12.5\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC IP Update On Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCIPUpdateFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FWUpdateFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Format SD Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFormatSDSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FormatSDSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\",\n" +
                "\"firmwareVer\": \"V12.5\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Format SD Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCFormatSDFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FormatSDFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Set RTC Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCSetRTCSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SetRTCSuccess\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\",\n" +
                "\"firmwareVer\": \"V12.5\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Set RTC Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendSPCSetRTCFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SetRTCFail\", \"deviceId\":\"4b:15:da:e2:78:1c\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Diagnostic Data Test", suiteName = "Test Node-Red APIs")
    public void sendSPCDiagnosticDataTest(){
        // Build POST request body.
        String apiBody =  "{\n" +
                "\"msgType\": \"SPCDiag\",\n" +
                "\"deviceId\": \"4b:15:da:e2:78:1c\",\n" +
                "\"sd\": {\n" +
                "\"inserted\": \"1\",\n" +
                "\"fill\": \"1\",\n" +
                "\"corrupt\": \"1\",\n" +
                "\"size\": \"250\",\n" +
                "\"unit\":\"KB\"\n" +
                "},\n" +
                "\"eeprom\": {\n" +
                "\"init\": \"1\",\n" +
                "\"mac_read\": \"1\"\n" +
                "},\n" +
                "\"rtc\": {\n" +
                "\"batt\": \"1\",\n" +
                "\"reason\": \"Invalid time\"\n" +
                "},\n" +
                "\"switch\": {\n" +
                "\"status\": \"1\",\n" +
                "\"action\": \"SD Card Format\"\n" +
                "},\n" +
                "\"cable\": \"1\",\n" +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/device/data")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Success")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Diagnostic Success Acknowledgment Test", suiteName = "Test Node-Red APIs")
    public void sendSPCDiagnosticSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\": \"SPCDiagSuccess\"," +
                "\"deviceId\": \"${DevSPC}\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/acknowledgment")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send SPC Diagnostic Failure Acknowledgment Test", suiteName = "Test Node-Red APIs")
    public void sendSPCDiagnosticFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\": \"SPCDiagFail\"," +
                "\"deviceId\": \"${DevSPC}\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/spc/acknowledgment")
                .then().statusCode(200)
                .assertThat().body("message", equalTo("Acknowledged")); // Verify the Success message in response.
    }

}
