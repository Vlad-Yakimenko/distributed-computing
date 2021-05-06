package ua.knu;

public class ReadWriteLock {
    private boolean writeLock;
    private int readersNumber;

    ReadWriteLock() {
        writeLock = false;
        readersNumber = 0;
    }

    synchronized void lockRead() {
        while (writeLock) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readersNumber++;
    }

    synchronized void unlockRead() {
        readersNumber--;
        if (readersNumber == 0)
            notifyAll();
    }

    synchronized void lockWrite() {
        while (readersNumber != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeLock = true;
    }

    synchronized void unlockWrite() {
        writeLock = false;
        notifyAll();
    }
}
