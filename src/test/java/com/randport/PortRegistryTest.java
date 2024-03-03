package com.randport;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PortRegistryTest {
    private PortRegistry portRegistry;


    @Before
    public void setup() {
        portRegistry = new PortRegistry();
    }

    @After
    public void dest() {
        portRegistry.clear();
    }

    @Test
    public void testHoldServerSocket() {
        portRegistry.hold(1001);
        assertNotNull(portRegistry.getOccupiedPort(1001));
    }

    @Test
    public void testHoldPort() {
        int port = 8081;
        portRegistry.hold(port);
        ServerSocket occupiedPort = portRegistry.getOccupiedPort(port);
        assertNotNull(occupiedPort);
        assertEquals(port, occupiedPort.getLocalPort());
    }

    @Test(expected = RuntimeException.class)
    public void release() {
        portRegistry.hold(8080);
        portRegistry.release(8080);
        portRegistry.getOccupiedPort(8080);
    }

    @Test
    public void getOccupiedPort() {
        portRegistry.hold(7071);
        assertNotNull(portRegistry.getOccupiedPort(7071));
    }

    @Test
    public void getOccupiedPorts() {
        portRegistry.hold(6061);
        List<String> occupiedPorts = portRegistry.getOccupiedPorts();
        assertEquals(1, occupiedPorts.size());
        String expected = String.format("Port: %d, Address: %s",
                6061, "0.0.0.0/0.0.0.0");
        assertEquals(expected, occupiedPorts.get(0));
    }
}
