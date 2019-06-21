package co.donebyme.pingpong;

import io.vlingo.actors.Actor;

public class PongerActor extends Actor implements Ponger {
    @Override
    public void pong(Pinger pinger) {
        logger().log("PONG " + Thread.currentThread().getName());
    }
}
