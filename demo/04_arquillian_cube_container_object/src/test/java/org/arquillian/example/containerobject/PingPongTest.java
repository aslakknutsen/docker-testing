package org.arquillian.example.containerobject;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.arquillian.cube.containerobject.Cube;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

// # tag::arquillian_cube_test[]
@RunWith(Arquillian.class)
public class PingPongTest {

    @Cube //<1>
    private PingPongContainer pingPongContainer;

    @Test
    public void shouldReturnOkAsPong() throws IOException {
        String pong = ping(pingPongContainer.getConnectionURL());
        assertThat(pong, containsString("OK"));
    }

//  # end::arquillian_cube_test[]
    public String ping(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
