package com.devdungeon.apps.portscanner;

import java.util.ArrayList;
import java.util.List;


class PortScannerConfig {

    private List<String> targets = new ArrayList<>();
    private List<Integer> ports = new ArrayList<>();

    //PortScannerConfig() {}

    void addTarget(String target) {
        targets.add(target);
    }

    void addPort(int port) {
        ports.add(port);
    }

    List<String> getTargets() {
        return targets;
    }

    List<Integer> getPorts() {
        return ports;
    }

}
