package com.packers.movers.service.resource;

import com.jayway.restassured.http.ContentType;
import com.packers.movers.test.SystemTest;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class OrderResourceTest extends SystemTest {

    @Test
    public void test_Order_Resource() {
        String path = OrderResource.BASE_PATH + "/";

        given()
            .contentType(ContentType.JSON)
            .body(getJsonBody())
            .log().everything()
            .expect()
            .statusCode(HttpURLConnection.HTTP_OK)
            .log().everything()
            .when()
            .post(path);
    }

    public String getJsonBody() {
        return "{\n" +
            "  \"name\": \"John Trivolta\",\n" +
            "  \"phone\": \"004723568978\",\n" +
            "  \"email\": \"jt@mailenator.com\",\n" +
            "  \"special_notes\": \"please start packing on wednesday and moving on thursday and cleaning on saturday.\",\n" +
            "  \"from_address\": {\n" +
            "    \"street_name\": \"veitvetveien 2A\",\n" +
            "    \"postcode\": \"0256\",\n" +
            "    \"city\": \"Oslo\",\n" +
            "    \"country\": \"Norway\"\n" +
            "  },\n" +
            "  \"to_address\": {\n" +
            "    \"street_name\": \"veitvetveien 2B\",\n" +
            "    \"postcode\": \"0256\",\n" +
            "    \"city\": \"Oslo\",\n" +
            "    \"country\": \"Norway\"\n" +
            "  },\n" +
            "  \"services\": [\n" +
            "    {\n" +
            "      \"type\": \"packing\",\n" +
            "      \"packets_quantity\": 100,\n" +
            "      \"start_date\": \"2017-05-08T07:10:45.059Z\",\n" +
            "      \"cost_per_packet\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"moving\",\n" +
            "      \"packets_quantity\": 100,\n" +
            "      \"start_date\": \"2017-05-08T07:10:45.059Z\",\n" +
            "      \"cost_per_packet\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"cleaning\",\n" +
            "      \"cost\": 100,\n" +
            "      \"packets_quantity\": 100,\n" +
            "      \"start_date\": \"2017-05-08T07:10:45.059Z\",\n" +
            "      \"cost_per_packet\": 10\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    }

}