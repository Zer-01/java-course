package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MagicNumber")
public class Task6 {
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65531;
    private static final Map<Integer, String> PORTS_USERS;
    private final static Logger LOGGER = LogManager.getLogger();

    static {
        PORTS_USERS = new HashMap<>();
        PORTS_USERS.put(80, "HTTP (HyperText Transfer Protocol)");
        PORTS_USERS.put(21, "FTP (File Transfer Protocol)");
        PORTS_USERS.put(25, "SMTP (Simple Mail Transfer Protocol)");
        PORTS_USERS.put(22, "SSH (Secure Shell)");
        String netBios = "NetBIOS";
        PORTS_USERS.put(137, netBios);
        PORTS_USERS.put(138, netBios);
        PORTS_USERS.put(139, netBios);
        PORTS_USERS.put(443, "HTTPS (HyperText Transfer Protocol Secure)");
        PORTS_USERS.put(53, "DNS (Domain Name System)");
        PORTS_USERS.put(3306, "MySQL Database");
        PORTS_USERS.put(5432, "PostgreSQL Database");
        PORTS_USERS.put(3389, "Remote Desktop Protocol (RDP)");
        PORTS_USERS.put(1521, "Oracle Database");
    }

    private Task6() {
    }

    public static void checkPorts() {
        LOGGER.info("Protocol  Port   Service");
        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.info("TCP   {}   {}", port, PORTS_USERS.getOrDefault(port, ""));
            }

            try {
                DatagramSocket datagramSocket = new DatagramSocket(new InetSocketAddress(port));
                datagramSocket.close();
            } catch (IOException e) {
                LOGGER.info("UDP   {}   {}", port, PORTS_USERS.getOrDefault(port, ""));
            }
        }
    }
}
