package com.randport;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PortUtil {
    private static final int MAX_PORT = 65535;

    public static ServerSocket rangePort(int begin, int end) {
        if (begin <= 0 || end > MAX_PORT) {
            throw new RuntimeException("Illegal port number");
        }
        int numPorts = end - begin + 1;
        int[] ports = new int[numPorts];
        for (int i = 0; i < numPorts; i++) {
            ports[i] = begin + i;
        }

        shuffleArray(ports);

        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ignored) {
            }
        }
        throw new RuntimeException("All ports in the range are in use");
    }

    public static Set<ServerSocket> rangePorts(int begin, int end, int amount) {
        if (begin <= 0 || end > MAX_PORT || amount > end - begin) {
            throw new RuntimeException("Illegal port number");
        }
        Set<ServerSocket> sockets = new HashSet<>();
        int numPorts = end - begin + 1;
        int[] ports = new int[numPorts];
        for (int i = 0; i < numPorts; i++) {
            ports[i] = begin + i;
        }
        shuffleArray(ports);
        for (int i = 0; i < amount; i++) {
            try {
                sockets.add(new ServerSocket(ports[i]));
            } catch (IOException ignored) {
            }
        }
        return sockets;
    }

    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public static ServerSocket randomPort() {
        int[] ports = new int[MAX_PORT];
        for (int i = 0; i < MAX_PORT; i++) {
            ports[i] = i + 1;
        }
        shuffleArray(ports);
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ignored) {
            }
        }
        throw new RuntimeException("All possible ports are in use");
    }
}
