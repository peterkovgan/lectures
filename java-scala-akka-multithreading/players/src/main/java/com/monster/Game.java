package com.monster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a starting point
 * The object of this class initiates players
 * (Players will run in separate threads)
 * Here made validations of input parameters
 * <p>
 * see {@link #play()} for the flow description!
 * <p>
 * Players could be initiated in different order
 * Players could be initiated in different VMs
 * <p>
 * The Game controls the stop condition for both Players
 */
public final class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final int MIN_PORT = 1024;
    private static final int MAX_PORT = 65353;
    private static final int MAX_MESSAGES = 10;
    private static final int CONNECTION_TIMEOUT_MSC = 120000;
    private final Mode mode;
    //Note, I assume it works on localhost even on separate VMs, till other required
    //Thus only port is variable
    private final int bindingPort;
    private Recorder initiatorRecorder;
    private Recorder responderRecorder;

    Game(final Mode mode, final int bindingPort) {
        if (mode == null) {
            throw new InvalidParameterException("mode must not be null");
        }
        if (isPortRangeValid(bindingPort)) {
            throw new InvalidParameterException("the port " + bindingPort + " is not in range");
        }
        this.mode = mode;
        this.bindingPort = bindingPort;
    }

    public static void main(String[] args) {
        validate(args);
        Mode mode = Mode.valueOf(args[0]);
        int bindingPort = Integer.parseInt(args[1]);
        Game game = new Game(mode, bindingPort);
        game.play();
    }

    void play() {
        Thread responderThread = optionallyStartResponder();
        Thread initiatorThread = optionallyStartInitiator();
        waitTillCompletes(responderThread);
        waitTillCompletes(initiatorThread);
        logger.info("The game is over in amazing grace...:)");
    }

    boolean isInRunCondition(int sent, int received) {
        return (sent < MAX_MESSAGES && received < MAX_MESSAGES);
    }

    private static void validate(final String[] args) {
        StringBuilder errors = new StringBuilder();
        validateArgumentsNumber(args, errors);
        if (!errors.toString().isEmpty()) {
            printUsage(errors);
            throw new InvalidParameterException(errors.toString());
        }
        validateMode(args[0], errors);
        validateBindingPort(args[1], errors);
        if (!errors.toString().isEmpty()) {
            printUsage(errors);
            throw new InvalidParameterException(errors.toString());
        }
    }

    private static void validateArgumentsNumber(final String[] args, final StringBuilder errors) {
        if (args == null || args.length != 2) {
            errors.append("Invalid number of arguments\n\r");
        }
    }

    private static void validateBindingPort(final String arg, final StringBuilder errors) {
        if (arg == null || arg.isEmpty()) {
            errors.append("Invalid binding port\n\r");
        } else {
            try {
                int port = Integer.parseInt(arg);
                if (isPortRangeValid(port)) {
                    errors.append("Invalid binding port: not in range\n\r");
                }
            } catch (NumberFormatException e) {
                errors.append("Invalid binding port: not numeric\n\r");
            }
        }
    }

    private static boolean isPortRangeValid(int port) {
        return port < MIN_PORT || port > MAX_PORT;
    }

    private static void validateMode(final String arg, final StringBuilder errors) {
        try {
            Mode.valueOf(arg);
        } catch (IllegalArgumentException | NullPointerException e) {
            errors.append("Invalid mode\n\r");
        }
    }

    private static void printUsage(final StringBuilder errors) {
        String usage = "USAGE: java -jar <jar name> <mode=BOTH|RESPONDER|INITIATOR> <port in range from " + MIN_PORT + " to " + MAX_PORT + "> \n\r" +
                "works only on localhost\n\r" +
                "Example:java -jar players.jar BOTH 1234\n\r" +
                "Found following issues:" +
                errors.toString();
        logger.error(usage);
    }


    private void waitTillCompletes(final Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.info("Interrupted on join", e);
            }
        }
    }

    private Thread optionallyStartInitiator() {
        if (mode == Mode.BOTH || mode == Mode.INITIATOR) {
            Initiator initiator = new Initiator(this, bindingPort, CONNECTION_TIMEOUT_MSC);
            Thread initiatorThread = new Thread(initiator);
            initiatorThread.start();
            return initiatorThread;
        } else {
            return null;
        }
    }

    private Thread optionallyStartResponder() {
        if (mode == Mode.BOTH || mode == Mode.RESPONDER) {
            Responder responder = new Responder(this, bindingPort, CONNECTION_TIMEOUT_MSC);
            Thread responderThread = new Thread(responder);
            responderThread.start();
            return responderThread;
        } else {
            return null;
        }
    }


    void report(final Player player, final Recorder recorder) {
        if (player instanceof Initiator) {
            this.initiatorRecorder = recorder;
        } else if (player instanceof Responder) {
            this.responderRecorder = recorder;
        }
    }

    public Recorder getInitiatorRecorder() {
        return initiatorRecorder;
    }

    public Recorder getResponderRecorder() {
        return responderRecorder;
    }

    boolean isStopConditionValid(){
        return initiatorRecorder!=null && initiatorRecorder.getSent()==MAX_MESSAGES && initiatorRecorder.getReceived()==MAX_MESSAGES
                && responderRecorder!=null && responderRecorder.getSent()==MAX_MESSAGES && responderRecorder.getReceived()==MAX_MESSAGES;
    }
}
