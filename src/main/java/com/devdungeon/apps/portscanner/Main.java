package com.devdungeon.apps.portscanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author NanoDano <nanodano@devdungeon.com>
 */
public class Main {

    // rocksaw lib
    private static Options options = new Options();
    private static CommandLine cmd;
    private final static Logger logger = Logger.getLogger(Main.class);

    private static void addCLIOptions() {
        options.addOption("t", "targets", true, "the IP or hostname(s) to scan. Can provide multiple -t entries");
        options.addOption("p", "ports", true, "the ports to scan. Can provide multiple -p entries. Omit to scan all ports.");
        options.addOption("h", "help", false, "print this help message");
    }

    private static void parseCLIOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);

        // Handle help
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "PortScanner", options);
            System.exit(0);
        }
    }

    private static PortScannerConfig populatePortScannerConfig() {
        String[] targets = cmd.getOptionValues("t");
        String[] ports = cmd.getOptionValues("p");

        PortScannerConfig portScannerConfig = new PortScannerConfig();

        if (targets != null) {
            for (String target: targets) {
                portScannerConfig.addTarget(target);
            }
        } else {
            // no targets provided
        }
        if (ports != null) {
            for (String port: ports) {
                portScannerConfig.addPort(Integer.parseInt(port));
            }
        } else {
            // no ports provided
        }

        return portScannerConfig;

    }

    public static void main(String[] args) {
        addCLIOptions();
        try {
            parseCLIOptions(args);
        } catch (ParseException e) {
            logger.error("[-] Error parsing command line arguments: " + e.getMessage());
            logger.error("[-] Use -h or --help to print usage.");
            System.exit(1);
        }

        PortScannerConfig portScannerConfig = populatePortScannerConfig();

        logger.info("[*] Initializing port scanner...");
        PortScanner portScanner = new PortScanner(portScannerConfig);
        portScanner.printConfig();

        logger.info("[*] Starting scan.");
        portScanner.scan();
    }

}
