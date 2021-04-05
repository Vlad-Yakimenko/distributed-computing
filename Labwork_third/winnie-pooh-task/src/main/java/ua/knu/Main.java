package ua.knu;

import ua.knu.woodman.Bear;
import ua.knu.woodman.Bee;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger honeyPot = new AtomicInteger(0);
        final CyclicBarrier barrier = new CyclicBarrier(10, new Bear(honeyPot, 10));

        for (int i = 0; i < 10; i++) {
            new Thread(new Bee(i, barrier, honeyPot, 10)).start();
            Thread.sleep(400);
        }
    }
}
