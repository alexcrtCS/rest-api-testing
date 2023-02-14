package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static data.Constants.ZIPCODES_EXPAND_RESOURCE;
import static data.Constants.ZIPCODES_RESOURCE;

public class ZipCodeClient {
    private static HttpResponse response;

    public int getResponseStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public List<String> getZipCodesList() throws IOException, URISyntaxException {
        response = executeGetZipCodes();
        return getResponseAsList();
    }

    public List<String> addToZipCodesList(List<String> zipCodesList) throws IOException, URISyntaxException {
        response = executePostZipCodes(zipCodesList.toString());
        return getResponseAsList();
    }

    private static HttpResponse executeGetZipCodes() throws IOException, URISyntaxException {
        return Client.executeGet(ZIPCODES_RESOURCE);
    }

    private static HttpResponse executePostZipCodes(String requestBody) throws IOException, URISyntaxException {
        return Client.executePost(ZIPCODES_EXPAND_RESOURCE, requestBody);
    }

    private static List<String> getResponseAsList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(mapper.readValue(response.getEntity().getContent(), String[].class))
                .collect(Collectors.toList());
    }
}
