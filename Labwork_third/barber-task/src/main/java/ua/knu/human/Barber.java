package ua.knu.human;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
@RequiredArgsConstructor
public class Barber implements Runnable {

    private boolean isSleeping;
    private final Semaphore barberChair;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            cutHair();
        }
    }

    void cutHair() {
        if (isSleeping) {
            log.info("The barber is sleeping");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } else {

            if (barberChair.tryAcquire()) {
                isSleeping = true;

            } else {
                log.info("The barber is cutting hair");
                barberChair.release();
            }
        }
    }

    void wakeUp() {
        if (isSleeping) {
            isSleeping = false;
            barberChair.release();
            log.info("The barber is woken up");
        }
    }
}
