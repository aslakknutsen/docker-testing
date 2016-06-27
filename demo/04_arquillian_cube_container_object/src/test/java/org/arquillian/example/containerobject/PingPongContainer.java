package org.arquillian.example.containerobject;

import java.net.MalformedURLException;
import java.net.URL;

import org.arquillian.cube.HostIp;
import org.arquillian.cube.containerobject.Cube;
import org.arquillian.cube.containerobject.CubeDockerFile;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.docker.DockerDescriptor;

// # tag::arquillian_cube_cube[]
@Cube(value = "pingpong", portBinding = "5000->8080/tcp")
public class PingPongContainer {

    @HostIp
    private String dockerHost;

    @CubeDockerFile
    public static Archive<?> createContainer() {
        String dockerDescriptor = 
                Descriptors.create(DockerDescriptor.class)
                    .from("jonmorehouse/ping-pong")
                    .exportAsString();
        
        return ShrinkWrap.create(GenericArchive.class)
                .add(new StringAsset(dockerDescriptor), "Dockerfile");
    }
    
    public URL getConnectionURL() {
        try {
            return new URL("http://" + getDockerHost() + ":" + getConnectionPort());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

 // # end::arquillian_cube_cube[]
    
    public int getConnectionPort() {
        return 5000;
    }

    public String getDockerHost() {
        return this.dockerHost;
    }
}
