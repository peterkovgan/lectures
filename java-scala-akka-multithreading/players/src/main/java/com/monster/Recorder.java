package com.monster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Serves 2 purposes:
 * <p>
 * <p>
 * 1.
 * Cleanup:
 * Collects objects of type {@link Closeable} to later close them
 * Note: try-with-resources for this purpose
 * was seriously considered in {@link Player}  and rejected for several reasons
 * <p>
 * 2.
 * Record:
 * Records execution results to help controlling utilities or tests
 */
public class Recorder implements Closeable {
    private final static Logger logger = LoggerFactory.getLogger(Recorder.class);
    private Socket socket;
    private ServerSocket serverSocket;
    private DataOutputStream output;
    private BufferedReader input;
    private List<Throwable> exceptions;
    private int sent;
    private int received;

    //For use by Initiator
    Recorder() {
        exceptions = new ArrayList<>();
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }

    void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    Socket getSocket() {
        return socket;
    }

    @Override
    public void close() {
        closeSafely(output);
        closeSafely(input);
        closeSafely(socket);
        closeSafely(serverSocket);
    }

    private void closeSafely(Closeable resource) {
        if (socket != null) {
            logger.info("Will attempt to close resource " + resource);
            try {
                socket.close();
            } catch (IOException e) {
                logger.info("closing the resource failed");
            }
        }
    }


    void setOutput(DataOutputStream output) {
        this.output = output;
    }

    void setInput(BufferedReader input) {
        this.input = input;
    }

    void reportCriticalPlayerException(IOException e) {
        exceptions.add(e);
    }

    void recordStopState(int sent, int received) {
        this.sent = sent;
        this.received = received;
    }

    List<Throwable> getExceptions() {
        return exceptions;
    }

    int getSent() {
        return sent;
    }

    int getReceived() {
        return received;
    }
}
