import client.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Auth;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // Example of how to check the response for token details (will delete class after review)
        HttpResponse response = Client.executePost();
        Auth auth = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                auth = objectMapper.readValue(EntityUtils.toString(response.getEntity()), Auth.class);
            } finally {
                inputStream.close();
            }
        }
        System.out.println(auth);
    }
}
