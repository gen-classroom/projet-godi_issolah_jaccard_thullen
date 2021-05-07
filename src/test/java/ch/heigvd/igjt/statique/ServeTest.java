package ch.heigvd.igjt.statique;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServeTest {
    @Test
    public void test() throws InterruptedException, IOException {
        var t = new Thread(() -> {
            new CommandLine(new Main()).execute("serve", "./testFiles");
        });
        t.start();
        Thread.sleep(100);

        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();
        //This request should work (response code = 200)
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/home.html")).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        //This request should get a 404 error
        request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080")).build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        t.stop();
    }
}
