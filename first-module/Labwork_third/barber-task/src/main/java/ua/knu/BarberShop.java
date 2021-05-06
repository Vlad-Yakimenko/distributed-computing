package ua.knu;

import lombok.SneakyThrows;
import ua.knu.human.Barber;
import ua.knu.human.Client;

import java.util.concurrent.Semaphore;

public class BarberShop implements Runnable {

    private final Semaphore barberChair = new Semaphore(1);
    private final Semaphore waitingChairs = new Semaphore(3);

    @Override
    @SneakyThrows
    @SuppressWarnings({"java:S2189", "InfiniteLoopStatement", "BusyWait"})
    public void run() {
        final Barber barber = new Barber(barberChair);
        new Thread(barber).start();

        long i = 0L;
        while (true) {
            Thread.sleep(5000);
            new Thread(new Client(i, barber, barberChair, waitingChairs)).start();
            i++;
        }
    }
}
