package com.example.helloworld.service;


import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Data
@Component
public class SalesforceAuthenticator {


    private String accessToken;
    private String instanceUrl;


    private abstract static class TypedMap implements Map<String, String> {}

    public SalesforceAuthenticator() {
        authenticate();
    }

    public void authenticate() {

        final String baseUrl = "https://login.salesforce.com/services/oauth2/token";


        RestTemplate restTemplate = new RestTemplate();
        Map<?, ?> response = restTemplate.postForObject(
                baseUrl,
                getParams(),
                Map.class
        );

        assert response != null;

        accessToken = (String) response.get("access_token");
        instanceUrl = (String) response.get("instance_url");
    }

    private MultiValueMap<String, String> getParams() {

        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("username", "");
        params.add("password", "");
        params.add("client_secret", "");
        params.add("client_id", "");
        params.add("grant_type","password");

        return params;
    }

}

