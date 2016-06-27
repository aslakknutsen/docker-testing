package org.arquillian.example.containerobject;

import org.apache.commons.io.IOUtils;
import org.arquillian.cube.containerobject.Cube;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class PingPongTest {

    @Cube
    private PingPongContainer pingPongContainer;

    @Test
    public void shouldReturnOkAsPong() throws IOException {
        String pong = ping(pingPongContainer.getConnectionURL());
        assertThat(pong, containsString("OK"));
    }

    public String ping(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        final String response = readResponse(con);

        con.disconnect();
        return response;
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            return IOUtils.toString(in);
        }
    }
}
