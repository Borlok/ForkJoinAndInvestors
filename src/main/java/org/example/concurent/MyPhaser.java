package org.example.concurent;

import java.util.concurrent.Phaser;

/**
 * @author Erofeevskiy Yuriy on 05.12.2023
 */


public class MyPhaser {
    private static Phaser phaser = new Phaser();

    public static void awaitAdvance() {
        phaser.arriveAndAwaitAdvance();
    }

    public static void deregister() {
        phaser.arriveAndDeregister();
    }

    public static void register() {
        if (phaser.isTerminated())
            phaser = new Phaser(0);
        phaser.register();
    }
}
