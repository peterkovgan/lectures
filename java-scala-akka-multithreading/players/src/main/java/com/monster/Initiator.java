package com.monster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * This is Player of Initiator kind, basically a TCP client
 * It can start before server too and will be looping waiting till server starts or timeout reached
 */
public class Initiator extends Player {

    private static final Logger logger = LoggerFactory.getLogger(Initiator.class);
    private static final long PAUSE_BETWEEN_ATTEMPTS_MSC = 200L;
    private static final String MESSAGE = "hello\n";

    Initiator(final Game game, final int bindingPort, final int timeoutMsc) {
        super(game, bindingPort, timeoutMsc);
    }

    @Override
    protected void openConnection(final Recorder recorder, final int bindingPort, final int timeout) throws IOException {
        Socket socket = connectResponder(bindingPort, timeout);
        recorder.setSocket(socket);
    }

    @Override
    protected void talk(final Recorder recorder, final DataOutputStream outToPartner, final BufferedReader inFromPartner) throws IOException {
        while (game.isInRunCondition(sent, received)) {
            sendMessage(outToPartner, MESSAGE);
            receiveMessage(inFromPartner);
        }
    }

    private Socket connectResponder(final int bindingPort, final int timeout) throws IOException {
        long connectionAttemptTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - connectionAttemptTime) < timeout) {
            Socket socket = connectedResponder(bindingPort);
            if (socket == null) {
                sleepBeforeNextAttempt();
            } else {
                return socket;
            }
        }
        throw new SocketTimeoutException("Failed open connection , timeout reached " + timeout + " msc");
    }


    private Socket connectedResponder(final int bindingPort) {
        try {
            return new Socket(bindingHost, bindingPort);
        } catch (IOException e) {
            logger.info("Failed open socket, will wait and retry");
            return null;
        }
    }

    private void sleepBeforeNextAttempt() {
        try {
            Thread.sleep(PAUSE_BETWEEN_ATTEMPTS_MSC);
        } catch (InterruptedException ex) {
            logger.error("Interrupted while sleeps ", ex);
        }
    }

}
