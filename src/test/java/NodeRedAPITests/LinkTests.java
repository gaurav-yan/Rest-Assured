package NodeRedAPITests;
import static io.restassured.RestAssured.*;
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

public class LinkTests {
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
    @Test(testName = "Test Link Config Schema", suiteName = "Test Node-Red APIs")
    public void getLinkConfigSchemaValidationTest(){
        InputStream linkConfigSchema = getClass ().getClassLoader ()
                .getResourceAsStream ("LinkConfig.json");
        assert linkConfigSchema != null;
        given().relaxedHTTPSValidation().spec(requestSpec).when().get("/link/C4:DE:E2:19:27:52/config")
                .then().statusCode(200).and()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema (linkConfigSchema));
    }

    @Test(testName = "Send Link Health data Test", suiteName = "Test Node-Red APIs")
    public void sendLinkHealthDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Health\"," +
                "\"deviceType\":\"Link\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/heartbeat")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Bobrick Dot Reading data Test", suiteName = "Test Node-Red APIs")
    public void sendBobrickDotReadingDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Reading\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"BL\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"occupancy\": \"0\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/sensor/device/distance/occupancy")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Bobrick Dot Health data Test", suiteName = "Test Node-Red APIs")
    public void sendBobrickDotHealthDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Health\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"BL\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/heartbeat")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Occupancy Dot Reading data Test", suiteName = "Test Node-Red APIs")
    public void sendOccupancyReadingDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Reading\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"SO\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"occupancy\": \"0\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/sensor/device/distance/occupancy")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Occupancy Dot Health data Test", suiteName = "Test Node-Red APIs")
    public void sendOccupancyHealthDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Health\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"SO\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/heartbeat")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Distance Dot Reading data Test", suiteName = "Test Node-Red APIs")
    public void sendDistanceReadingDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Reading\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"DT\"," +
                "\"dotId\":\"5f:c3:90:70:ee:2b\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"fillLevel\":\"80\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/sensor/device/distance/occupancy")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Distance Dot Health data Test", suiteName = "Test Node-Red APIs")
    public void sendDistanceHealthDataTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"Health\"," +
                "\"deviceType\":\"Dot\"," +
                "\"dotType\":\"DT\"," +
                "\"dotId\":\"5f:c3:90:70:ee:2b\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"batt\":\"100\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/heartbeat")
                .then().statusCode(200)
                .assertThat().body(equalTo("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Dot Config Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendDotConfigSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"DotConfigSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Dot Config Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendDotConfigFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"DotConfigFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"dotId\":\"ae:41:8e:dc:62:b8\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Config Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkConfigSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkConfigSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Config Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkConfigFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkConfigFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Connected Devices Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkConnectedDevicesSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ConnectedDeviceSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/connected")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Connected Devices Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkConnectedDevicesFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"ConnectedDeviceFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/connected")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Integration Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkIntegrationSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkIntegrationSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"linkIntgId\": \"1cebd628-8a1e-4b1f-8ddd-4df4b602d8c0\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/Integration")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Integration Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkIntegrationFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkIntegrationFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"linkIntgId\": \"1cebd628-8a1e-4b1f-8ddd-4df4b602d8c0\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/Integration")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Reboot Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkRebootSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkRebootSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Reboot Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkRebootFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkRebootFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Get SIM Info Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendGetSIMINfoSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\": \"GetSimInfoSuccess\"," +
                "\"linkId\": \"da:c5:79:63:2b:a9\"," +
                "\"SIM_IMSI\": \"404909259882104\"," +
                "\"SIM\": \"8991000921713159647F\"," +
                "\"868019066768590\": \"350123451234560\"," +
                "\"carrier\": \"Airtel\"," +
                "\"signal\": \"Good\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Get SIM Info Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendGetSIMInfoFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"GetSimInfoFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Set RTC Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkSetRTCSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SetRTCSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Set RTC Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkSetRTCFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"SetRTCFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Format SD Card Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkFormatSDSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FormatSDSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Format SD Card Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkFormatSDFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"FormatSDFail\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/link/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Boot Data(Portal) Test", suiteName = "Test Node-Red APIs")
    public void sendLinkBootDataPortalTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkBootData\"," +
                "\"bootType\":\"Portal\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"ipAddr\":\"192.168.125.35\"," +
                "\"userId\": \"91f0da28-c895-11ed-b613-00be4392b2e5\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/boot")
                .then().statusCode(200)
                .assertThat().body(containsString("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Boot Data(Manual) Test", suiteName = "Test Node-Red APIs")
    public void sendLinkBootDataManualTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkBootData\"," +
                "\"bootType\":\"Manual\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"ipAddr\":\"192.168.125.35\"," +
                "\"userId\": \"\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/device/boot")
                .then().statusCode(200)
                .assertThat().body(containsString("success")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Firmware Update Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkFirmwareUpdateSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkFirmwareSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"firmwareVer\":\"V3.0\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/firmware/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Link Firmware Update Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendLinkFirmwareUpdateFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"LinkFirmwareSuccess\"," +
                "\"linkId\":\"da:c5:79:63:2b:a9\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/firmware/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Dot Firmware Update Success Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendDotFirmwareUpdateSuccessACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"DotFirmwareSuccess\"," +
                "\"dotId\":\"5f:c3:90:70:ee:2b\"," +
                "\"firmwareVer\":\"V1.7\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/firmware/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }

    @Test(testName = "Send Dot Firmware Update Failure Acknowledgement Test", suiteName = "Test Node-Red APIs")
    public void sendDotFirmwareUpdateFailureACKTest(){
        // Build POST request body.
        String apiBody =  "{\"msgType\":\"DotFirmwareFail\"," +
                "\"dotId\":\"5f:c3:90:70:ee:2b\"," +
                "\"time\":\""+ captureTime+"\"}";

        given().relaxedHTTPSValidation().spec(requestSpec)
                // Set Content Type to Json.
                .contentType(ContentType.JSON)
                // Set Authorization to none.
                .auth().none()
                // Add Body to the post request.
                .body(apiBody)
                // Make the request.
                .when().post("/firmware/acknowledgment")
                .then().statusCode(200)
                .assertThat().body(containsString("Acknowledged")); // Verify the Success message in response.
    }
}
