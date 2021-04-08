package ua.knu;

import ua.knu.util.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddRecord implements Runnable {

    private Random random;
    private File file;

    private ReentrantReadWriteLock.WriteLock writeLock;

    public AddRecord(String fName, ReentrantReadWriteLock.WriteLock writeLock) {
        random = new Random();
        file = new File(fName);

        this.writeLock = writeLock;
    }

    @Override
    public void run() {
        int pos;
        String surname, name, phone;
        while (!Thread.currentThread().isInterrupted()) {
            writeLock.lock();

            pos = random.nextInt(Constants.surnames.size());
            surname = Constants.surnames.get(pos);
            pos = random.nextInt(Constants.names.size());
            name = Constants.names.get(pos);
            pos = random.nextInt(Constants.phones.size());
            phone = Constants.phones.get(pos);
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
                printWriter.println(surname + " " + name + " " + phone);
                System.out.println("AddPerson thread added new person: " + surname + " " + name + " " + phone);

                printWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            writeLock.unlock();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
