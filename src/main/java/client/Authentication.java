package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Auth;
import httpclient.Request;
import httpclient.Scope;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

import static data.Constants.*;

public class Authentication {
    public static String getToken(Scope scope) throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpResponse response = getTokenRequest(scope);
        /* ObjectMapper's readValue API takes response body & based on Auth class
         will return value of 'access_token' json property as String */
        return mapper.readValue(response.getEntity().getContent(), Auth.class).getAccessToken();
    }

    public static HttpResponse getTokenRequest(Scope scope) throws URISyntaxException, IOException {
        return Request
                .postRequest(URL + TOKEN_RESOURCE)
                .setHeader("Content-Type", "application/json")
                .setParameter("grant_type", "client_credentials")
                .setParameter("scope", scope.name().toLowerCase())
                .setBasicAuthentication(USERNAME, PASSWORD)
                .executeRequest();
    }
}
