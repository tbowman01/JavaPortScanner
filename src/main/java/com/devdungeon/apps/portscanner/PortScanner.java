package com.devdungeon.apps.portscanner;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


class PortScanner {

    private final static Logger logger = Logger.getLogger(Main.class);

    private PortScannerConfig config;

    PortScanner(PortScannerConfig config) {
        this.config = config;
    }

    // explode ip range
    // explode port range

    void printConfig() {
        logger.info("Targets: " + config.getTargets());
        logger.info("Ports: " + config.getPorts());
    }

    void checkPort(String target, Integer port) {
        Integer timeout = 3000;
        Thread thread = new Thread(() -> {
            try {
                Socket clientSocket = new Socket();
                clientSocket.connect(new InetSocketAddress(target, port), timeout);
                logger.info("[+] Open\t" + port.toString() + "\t" + target);
            } catch (SocketTimeoutException ex) {
                // Time out or closed
            } catch (IOException e) {
                // Some other exception
            }
        });
        thread.start();
    }

    void scan() {
        for (String target : config.getTargets()) {
            logger.info("[*] Now scanning target: " + target);
            if (config.getPorts().size() == 0) {
                for (Integer port = 1; port <= 65535; port++) {
                    checkPort(target, port);
                }
            } else {
                for (Integer port : config.getPorts()) {
                    checkPort(target, port);
                }
            }
        }
    }

}
