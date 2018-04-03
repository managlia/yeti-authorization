package com.yeti.authorization.live;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class AuthorizationLiveTest {


    @Test
    public void givenUser_whenUseBarClient_thenOkForBarResourceReadOnly() {
        final String accessToken = obtainAccessToken("dfmClientIdPassword", "foz", "foz123");

        final Response barReadResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/yeti/Users");
        assertEquals(200, barReadResponse.getStatusCode());
        assertNotNull(barReadResponse.jsonPath().get("contactId"));

    }
    private String obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().auth().preemptive().basic(clientId, "dfmSecret").and().with().params(params).when().post("http://localhost:8084/yetiauth/oauth/token");
        return response.jsonPath().getString("access_token");
    }

}
