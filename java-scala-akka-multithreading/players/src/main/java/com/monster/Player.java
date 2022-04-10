package com.monster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class represents a basic functionality of every kind of a Player
 * Limitation: only 'localhost' host is allowed (the rest is beyond requirement)
 *
 * <p>
 * The functionality includes:
 * - Communication flow universal description for both players in {@link #run} method
 * - Sending and receiving messages
 * - Cleanup used resources in {@link Recorder} - try-with-resources was rejected after considerations
 * <p>
 */
abstract class Player implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);
    private static final String BINDING_HOST = "localhost";
    private final int timeoutMsc;
    private final int bindingPort;
    final String bindingHost;
    int sent;
    int received;
    final Game game;

    Player(final Game game, final int bindingPort, int timeoutMsc) {
        this.bindingPort = bindingPort;
        this.timeoutMsc = timeoutMsc;
        this.bindingHost = BINDING_HOST;
        this.game = game;
    }

    @Override
    public void run() {
        Recorder recorder  = new Recorder();
        try {
            openConnection(recorder, bindingPort, timeoutMsc);
            DataOutputStream outToPartner = getOutputHandle(recorder);
            BufferedReader inFromPartner  = getInputHandle(recorder);
            talk(recorder, outToPartner, inFromPartner);
        } catch (IOException e) {
            logger.error("{} failed while running the flow ", this, e);
            recorder.reportCriticalPlayerException(e);
        } finally {
            recorder.recordStopState(sent, received);
            recorder.close();
            game.report(this, recorder);
        }
    }

    protected abstract void openConnection(final Recorder recorder, final int bindingPort, final int timeout) throws IOException;

    protected abstract void talk(final Recorder recorder, final DataOutputStream outToPartner, final BufferedReader inFromPartner) throws IOException;

    private DataOutputStream getOutputHandle(final Recorder recorder) throws IOException {
        DataOutputStream  result = new DataOutputStream(recorder.getSocket().getOutputStream());
        recorder.setOutput(result);
        return result;
    }

    private BufferedReader getInputHandle(final Recorder recorder) throws IOException, NullPointerException {
        BufferedReader result =  new BufferedReader(new InputStreamReader(recorder.getSocket().getInputStream()));
        recorder.setInput(result);
        return result;

    }

    void sendMessage(final DataOutputStream outToPartner, final String message) throws IOException {
        outToPartner.writeBytes(message);
        logger.info("{} sends to partner: {}", this, message);
        sent++;
    }

    String receiveMessage(final BufferedReader inFromPartner) throws IOException {
        String input = inFromPartner.readLine();
        logger.info("{} got from partner: {}", this, input);
        received++;
        return input;
    }

}
