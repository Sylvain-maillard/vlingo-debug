package co.donebyme.pingpong;

import io.vlingo.actors.Actor;
import io.vlingo.actors.Stoppable;
import io.vlingo.actors.testkit.TestState;
import io.vlingo.common.Cancellable;
import io.vlingo.common.Scheduled;

public class PingerActor extends Actor implements Pinger, Scheduled<Void> {

    private int pinged;
    private final Ponger ponger;
    private final Cancellable cancellable;

    public PingerActor(Ponger ponger) {
        this.ponger = ponger;

        cancellable = this.scheduler().schedule(this.selfAs(Scheduled.class), null, 0, 1000);
    }

    @Override
    public void intervalSignal(Scheduled<Void> scheduled, Void aVoid) {
        logger().log("scheduled task fire !!!");
        if (this.pinged >= 10) {
            selfAs(Stoppable.class).stop();
        } else {
            selfAs(Pinger.class).ping();
        }
    }

    @Override
    public void stop() {
        cancellable.cancel();
        logger().log("Killed ! in " + this.stage().name());
        super.stop();
        System.out.println("Killed.");
    }

    @Override
    public void ping() {
        pinged++;
        logger().log("Ping !!!! #" + pinged + " -> " + Thread.currentThread().getName());
        ponger.pong(selfAs(Pinger.class));
    }

     public TestState viewTestState() {
        return new TestState().putValue("pinged", pinged);
    }
}
