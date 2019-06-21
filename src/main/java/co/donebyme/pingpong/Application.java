package co.donebyme.pingpong;

import io.vlingo.actors.World;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        World world = World.startWithDefaults("my-cool-world");

        Ponger ponger = world.actorFor(Ponger.class, PongerActor.class);
        Pinger pinger = world.actorFor(Pinger.class, PingerActor.class, ponger);

        Thread.sleep(50000);

        world.terminate();

    }
}
