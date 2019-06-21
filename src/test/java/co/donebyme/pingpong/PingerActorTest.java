package co.donebyme.pingpong;

import io.vlingo.actors.World;
import io.vlingo.actors.testkit.TestActor;
import io.vlingo.actors.testkit.TestWorld;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PingerActorTest {

    private World world;

    @Before
    public void buildWorld() {
        world = World.startWithDefaults("my-world");
    }

    @After
    public void destroyWorld() {
        world.terminate();
    }

    @Test
    public void ping_pong_test() throws InterruptedException {
        Ponger ponger = world.actorFor(Ponger.class, PongerActor.class);
        Pinger pinger = world.actorFor(Pinger.class, PingerActor.class, ponger);

        pinger.ping();

        Thread.sleep(20000);

        Pinger pinger1 = world.actorFor(Pinger.class, PingerActor.class, ponger);
        pinger1.ping();

        Thread.sleep(20000);
    }
}
