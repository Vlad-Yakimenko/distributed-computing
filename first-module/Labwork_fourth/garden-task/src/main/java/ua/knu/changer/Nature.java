package ua.knu.changer;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ua.knu.GardenSimulator;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Nature implements Runnable {

    private GardenSimulator gardenSimulator;
    private ReentrantReadWriteLock.WriteLock writeLock;
    private Random random = new Random();

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            writeLock.lock();

            try {
                IntStream.range(0, 10)
                        .forEach(y -> gardenSimulator.changePlantState(
                                random.nextInt(10),
                                random.nextInt(10),
                                random.nextInt(3)));

                log.info("Nature made its work");

            } finally {
                writeLock.unlock();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
