package client;

import httpclient.Scope;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class Client {
    // Method for Scenario #1
    public static HttpResponse executePost() throws IOException, URISyntaxException {
        return Authentication.getTokenRequest(Scope.WRITE);
    }
    // Method for Scenario #2
    public static HttpResponse executePostLimited() throws IOException, URISyntaxException {
        return Authentication.getTokenRequest(Scope.READ);
    }
}
