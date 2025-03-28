import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PingPong {
    public static void main(String[] args) {
        Object LOCK_OBJECT = new Object();
        Thread ping = new Thread(new PingPongThread(LOCK_OBJECT, "Ping"));
        Thread pong = new Thread(new PingPongThread(LOCK_OBJECT, "Pong"));
        ping.start();
        pong.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ping.interrupt();
        pong.interrupt();
    }
}

class PingPongThread implements Runnable{

    private final Object LOCK_OBJECT;
    private final String name;

    public PingPongThread(Object LOCK_OBJECT, String name) {
        this.LOCK_OBJECT = LOCK_OBJECT;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (LOCK_OBJECT) {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println(name);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
                LOCK_OBJECT.notify();

                try {
                    LOCK_OBJECT.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

class PingPongTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private final Object LOCK_OBJECT = new Object();

    @BeforeEach
    public void setUp() {
        // Reset turn flag and capture system out
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testPingPongAlternation() throws InterruptedException {
        Thread ping = new Thread(new PingPongThread(LOCK_OBJECT, "Ping"));
        Thread pong = new Thread(new PingPongThread(LOCK_OBJECT, "Pong"));

        ping.start();
        pong.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ping.interrupt();
        pong.interrupt();

        // Wait for threads to complete
        ping.join();
        pong.join();

        // Get output and split into lines
        String output = outputStream.toString().trim();
        String[] lines = output.split(System.lineSeparator());

        // Verify alternation
        for (int i = 0; i < lines.length - 1; i++) {
            assertNotEquals(lines[i], lines[i+1],
                    "Adjacent lines should not be the same: Threads should alternate");
        }

        // Verify correct messages
        assertTrue(output.contains("Ping"), "Output should contain Ping");
        assertTrue(output.contains("Pong"), "Output should contain Pong");
    }

    @Test
    public void testOutputPattern() throws InterruptedException {
        Thread ping = new Thread(new PingPongThread(LOCK_OBJECT, "Ping"));
        Thread pong = new Thread(new PingPongThread(LOCK_OBJECT, "Pong"));

        ping.start();
        pong.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ping.interrupt();
        pong.interrupt();

        // Wait for threads to complete
        ping.join();
        pong.join();

        String output = outputStream.toString().trim();

        // Create a regex pattern to match alternating Ping and Pong
        Pattern pattern = Pattern.compile("^(Ping\n?Pong\n?)+$");
        assertTrue(pattern.matcher(output.replace(System.lineSeparator(), "\n")).matches(),
                "Output should follow Ping-Pong alternation pattern");
    }

    @Test
    public void testRunDuration() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        Thread ping = new Thread(new PingPongThread(LOCK_OBJECT, "Ping"));
        Thread pong = new Thread(new PingPongThread(LOCK_OBJECT, "Pong"));

        ping.start();
        pong.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ping.interrupt();
        pong.interrupt();

        // Wait for threads to complete
        ping.join();
        pong.join();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Allow a small margin of error
        assertTrue(duration >= 5000 && duration < 5500,
                "Threads should run for approximately 5 seconds");
    }

    @Test
    public void tearDown() {
        // Restore original system out
        System.setOut(originalOut);
    }
}


