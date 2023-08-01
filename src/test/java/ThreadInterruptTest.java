import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class ThreadInterruptTest {

    CountDownLatch latch;

    @BeforeAll
    public static void initialize() {

    }

    @Test
    public void testInterrupt() {
        Thread p = new Thread(new PThread());

        latch = new CountDownLatch(1);

        p.start();

        p.interrupt();

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class PThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                latch.countDown();
            } catch (InterruptedException e) {
                System.err.println("P_THREAD HAS BEEN INTERRUPTED");
                latch.countDown();
            }
        }
    }
}
