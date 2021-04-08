package ua.knu;

import ua.knu.util.Constants;

import java.io.*;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FindByPhone implements Runnable {
    private Random random;
    private File file;

    private ReentrantReadWriteLock.ReadLock readLock;

    public FindByPhone(String fName, ReentrantReadWriteLock.ReadLock readLock) {
        random = new Random();
        file = new File(fName);

        this.readLock = readLock;
    }

    @Override
    public void run() {
        int pos;
        String phone, line;
        boolean flag = false;
        while (!Thread.currentThread().isInterrupted()) {
            readLock.lock();

            pos = random.nextInt(Constants.phones.size());
            phone = Constants.phones.get(pos);
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split(" ");
                    if (str[2].equals(phone)) {
                        System.out.println("FindByPhone thread found person with phone: " + str[0] + " " + str[1] + " " + str[2]);
                        flag = true;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!flag) System.out.println("FindByPhone thread. Not found: " + phone);
            readLock.unlock();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
