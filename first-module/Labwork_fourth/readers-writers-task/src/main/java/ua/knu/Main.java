package ua.knu;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        String fName = "save_data.txt";
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

        Thread addPerson = new Thread(new AddRecord(fName, writeLock));
        Thread deletePerson = new Thread(new DeleteRecord(fName, writeLock));
        Thread findByPhone = new Thread(new FindByPhone(fName, readLock));
        Thread findBySurname = new Thread(new FindBySurname(fName, readLock));


        findByPhone.start();
        findBySurname.start();
        addPerson.start();
        deletePerson.start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addPerson.interrupt();
        deletePerson.interrupt();
        findByPhone.interrupt();
        findBySurname.interrupt();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            addPerson.interrupt();
            deletePerson.interrupt();
            findByPhone.interrupt();
            findBySurname.interrupt();
        }, "Shutdown-thread"));
    }
}
