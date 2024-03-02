package com.randport;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PortRegistry {

    private final Set<ServerSocket> occupiedPorts;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;

    public PortRegistry() {
        this.occupiedPorts = new HashSet<>();
    }

    public void hold(ServerSocket serverSocket) {
        int port = serverSocket.getLocalPort();
        if (port < MIN_PORT || port > MAX_PORT) {
            throw new IllegalArgumentException("Illegal port value");
        }
        occupiedPorts.add(serverSocket);
    }

    public void hold(int port) {
        if (port < MIN_PORT || port > MAX_PORT) {
            throw new IllegalArgumentException("Illegal port value");
        }
        try {
            occupiedPorts.add(new ServerSocket(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void release(int port) {
        occupiedPorts.removeIf(serverSocket -> serverSocket.getLocalPort() == port);
    }

    public ServerSocket getOccupiedPort(int port) {
        return occupiedPorts.stream()
                .filter(serverSocket -> serverSocket.getLocalPort() == port)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Port not found"));
    }

    public List<String> getOccupiedPorts() {
        return occupiedPorts.stream()
                .map(serverSocket -> String.format("Port: %d, Address: %s",
                        serverSocket.getLocalPort(), serverSocket.getInetAddress()))
                .collect(Collectors.toList());
    }
}