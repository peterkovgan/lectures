package com.monster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * This is Player of Responder kind, basically a TCP server
 * It will open 'connection acceptor' socket and wait for client connection till reached timeout
 * I will not separate messaging in another thread, so only 1 client could be accepted (it is what required)
 * It will reply on Initiator requests in the synchronous manner (with the obligatory acknowledge behind the TCP curtain)
 * So the same rule (10+10) of suspension will be applied , as in Initiator without any risk for the 'business'.
 */
public class Responder extends Player {

    private final static Logger logger = LoggerFactory.getLogger(Responder.class);

    Responder(final Game game, final int bindingPort, final int timeoutMsc) {
        super(game, bindingPort, timeoutMsc);
        logger.info("Responder created");
    }

    @Override
    protected void openConnection(final Recorder recorder, final int bindingPort, final int timeout) throws IOException {
        ServerSocket serverAcceptorSocket = new ServerSocket(bindingPort);
        recorder.setServerSocket(serverAcceptorSocket);
        serverAcceptorSocket.setSoTimeout(timeout);
        Socket socket = serverAcceptorSocket.accept();
        recorder.setSocket(socket);
        logger.info("Responder accepted client connection");
    }

    @Override
    protected void talk(final Recorder recorder, final DataOutputStream outToPartner, final BufferedReader inFromPartner) throws IOException {
        do {
            String messageIn = receiveMessage(inFromPartner);
            String messageOut = messageIn + " " + sent + "\n";
            sendMessage(outToPartner, messageOut);
        } while (game.isInRunCondition(sent, received));
    }
}
