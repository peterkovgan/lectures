package com.monster;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.SocketTimeoutException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PlayersTest {

    private static final int BINDING_PORT = 1234;
    private static final int SLEEP_TIME = 2000;
    private static final int CONNECTION_TIMEOUT_MSC = 10000;
    private static final int CONNECTION_TIMEOUT_MSC_SHORT = 100;


    //--Validations

    @Test(expected = InvalidParameterException.class)
    public void testValidatesNoArguments() {
        Game.main(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void testValidatesTooManyArguments() {
        Game.main(new String[]{"BOTH", "1234", "PETER"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullMode() {
        new Game(null, BINDING_PORT);
    }

    @Test(expected = InvalidParameterException.class)
    public void testNegativePort() {
        new Game(Mode.BOTH, -1);
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidMode() {
        Game.main(new String[]{"INVALID", "1234"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidStringPort() {
        Game.main(new String[]{Mode.BOTH.name(), "B234"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidNotInRangeLargePort() {
        Game.main(new String[]{Mode.BOTH.name(), "999234"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidNotInRangeSmallPort() {
        Game.main(new String[]{Mode.BOTH.name(), "10"});
    }


    //----Lifecycle

    @Test
    public void testBothInOneVmCompletedGracefully() {
        Game game = new Game(Mode.BOTH, BINDING_PORT);
        game.play();
        Assert.assertTrue(game.isStopConditionValid());
    }

    @Test
    public void testBothInOneVmCompletedGracefullyWhenClientStartsFirstAndLoopsTillServerStarts() {
        Game game = new Game(Mode.BOTH, BINDING_PORT);
        Thread initiatorThread = startInitiator(game, CONNECTION_TIMEOUT_MSC);
        makeAPause();
        Thread responderThread = startResponder(game, CONNECTION_TIMEOUT_MSC);
        joinThread(initiatorThread);
        joinThread(responderThread);
        Assert.assertTrue(game.isStopConditionValid());
    }

    @Test
    public void testInitiatorFailsOnTimeoutWhenResponderIsAbsent() {
        Game game = new Game(Mode.BOTH, BINDING_PORT);
        Initiator initiator = new Initiator(game, BINDING_PORT, CONNECTION_TIMEOUT_MSC_SHORT);
        Thread initiatorThread = new Thread(initiator);
        initiatorThread.start();
        joinThread(initiatorThread);
        List<Throwable> failures = game.getInitiatorRecorder().getExceptions();
        Assert.assertTrue(containsException(failures, SocketTimeoutException.class));
    }

    @Test
    public void testResponderFailsOnTimeoutWhenInitiatorIsAbsent() {
        Game game = new Game(Mode.BOTH, BINDING_PORT);
        Responder responder = new Responder(game, BINDING_PORT, CONNECTION_TIMEOUT_MSC_SHORT);
        Thread responderThread = new Thread(responder);
        responderThread.start();
        joinThread(responderThread);
        List<Throwable> failures = game.getResponderRecorder().getExceptions();
        Assert.assertTrue(containsException(failures, SocketTimeoutException.class));

    }

    private boolean containsException(List<Throwable> list, Class<?> exception) {
        for (Throwable t : list) {
            if (exception.isInstance(t)) {
                return true;
            }
        }
        return false;
    }


    private void joinThread(Thread initiatorThread) {
        try {
            initiatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeAPause() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread startResponder(Game game, int timeout) {
        Responder responder = new Responder(game, BINDING_PORT, timeout);
        Thread responderThread = new Thread(responder);
        responderThread.start();
        return responderThread;
    }

    private Thread startInitiator(Game game, int timeout) {
        Initiator initiator = new Initiator(game, BINDING_PORT, timeout);
        Thread initiatorThread = new Thread(initiator);
        initiatorThread.start();
        return initiatorThread;
    }


}
