package ua.knu.util;

public class Changed {
    private volatile boolean changed = true;

    public synchronized boolean getChanged() {
        return changed;
    }

    public synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }
}
