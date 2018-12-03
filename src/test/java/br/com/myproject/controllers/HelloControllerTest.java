package br.com.myproject.controllers;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloControllerTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server
                .getApplicationContext()
                .createBean(HttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (client != null)
            client.stop();

        if (server != null)
            server.stop();
    }

    @Test
    public void testHello() throws Exception {
        HttpRequest<String> httpRequest = HttpRequest.GET("/hello");
        String body = client.toBlocking().retrieve(httpRequest);
        Assert.assertNotNull(body);
        Assert.assertEquals(body, "Hello, World!");
    }
}