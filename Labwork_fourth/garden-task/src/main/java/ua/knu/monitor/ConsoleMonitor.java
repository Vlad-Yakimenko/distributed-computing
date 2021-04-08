package ua.knu.monitor;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import ua.knu.GardenSimulator;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ConsoleMonitor implements Runnable {

    private GardenSimulator gardenSimulator;
    private ReentrantReadWriteLock.ReadLock readLock;

    @Override
    @SuppressWarnings({"BusyWait", "SuspiciousNameCombination"})
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            readLock.lock();

            try {
                StringBuilder gardenRepresentation = new StringBuilder("***-BEGIN OF GARDEN-***\n");
                IntStream.range(0, 10).forEach(x -> {
                    IntStream.range(0, 10).forEach(y -> gardenRepresentation.append(gardenSimulator.getPlant(Pair.of(x, y))));
                    gardenRepresentation.append('\n');
                });
                System.out.println(gardenRepresentation.append("***-END OF GARDEN-***\n"));

            } finally {
                readLock.unlock();
            }

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
