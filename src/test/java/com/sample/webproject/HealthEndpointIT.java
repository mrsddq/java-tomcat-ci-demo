package com.sample.webproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HealthEndpointIT {
    @TempDir static Path temporaryDirectory;
    private static Tomcat tomcat;
    private static URI baseUri;
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5)).build();

    @BeforeAll
    static void startPackagedWar() throws Exception {
        tomcat = new Tomcat();
        tomcat.setBaseDir(temporaryDirectory.toString());
        tomcat.setPort(0);
        tomcat.getConnector().setProperty("address", "127.0.0.1");
        tomcat.addWebapp("/demo", Path.of("target/java-tomcat-ci-demo.war")
                .toAbsolutePath().toString());
        tomcat.start();
        baseUri = URI.create("http://127.0.0.1:" + tomcat.getConnector().getLocalPort() + "/demo/");
    }

    @AfterAll
    static void stopContainer() throws Exception {
        if (tomcat != null) {
            try { tomcat.stop(); } finally { tomcat.destroy(); }
        }
    }

    private HttpResponse<String> request(String path, String method) throws Exception {
        return CLIENT.send(HttpRequest.newBuilder(baseUri.resolve(path))
                .timeout(Duration.ofSeconds(5))
                .method(method, HttpRequest.BodyPublishers.noBody()).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void packagedHealthEndpointReturnsUncachedJson() throws Exception {
        var response = request("health", "GET");
        assertEquals(200, response.statusCode());
        assertTrue(response.headers().firstValue("content-type").orElse("")
                .startsWith("application/json"));
        assertEquals("no-store", response.headers().firstValue("cache-control").orElse(""));
        assertEquals("{\"status\":\"ok\",\"application\":\"java-tomcat-ci-demo\"}\n", response.body());
    }

    @Test
    void legacyHealthPathRemainsCompatible() throws Exception {
        assertEquals(request("health", "GET").body(), request("health.jsp", "GET").body());
    }

    @Test
    void headHasNoBodyAndPostIsRejected() throws Exception {
        var response = request("health", "HEAD");
        assertEquals(200, response.statusCode());
        assertEquals("", response.body());
        assertEquals(405, request("health", "POST").statusCode());
    }

    @Test
    void packagedLandingPageAndMissingRouteAreServedCorrectly() throws Exception {
        var response = request("", "GET");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("java-tomcat-ci-demo.war"));
        assertEquals(404, request("missing", "GET").statusCode());
    }
}
