package client;

import httpclient.Request;
import httpclient.Scope;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

import static data.Constants.URL;

public class Client {
    public static HttpResponse executeGet(String resource) throws URISyntaxException, IOException {
        return Request
                .getRequest(URL + resource)
                .setBearerAuthentication(Authentication.getToken(Scope.READ)) // sets bearer token from request
                .executeRequest();
    }

    public static HttpResponse executePost(String resource, String body) throws URISyntaxException, IOException {
        return Request
                .postRequest(URL + resource)
                .setHeader("Content-Type", "application/json") // without it will get 415 status code
                .setBearerAuthentication(Authentication.getToken(Scope.WRITE))
                .setBody(body)
                .executeRequest();
    }
}
