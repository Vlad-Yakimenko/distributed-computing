package ua.knu;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.changer.Gardener;
import ua.knu.changer.Nature;
import ua.knu.monitor.ConsoleMonitor;
import ua.knu.monitor.FileMonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        val gardenSimulator = new GardenSimulator();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        val writeLock = reentrantReadWriteLock.writeLock();
        val readLock = reentrantReadWriteLock.readLock();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(new Gardener(gardenSimulator, writeLock));
        executorService.submit(new Nature(gardenSimulator, writeLock));
        executorService.submit(new ConsoleMonitor(gardenSimulator, readLock));
        executorService.submit(new FileMonitor(gardenSimulator, readLock));

        Thread.sleep(15000);

        executorService.shutdownNow();
    }
}
