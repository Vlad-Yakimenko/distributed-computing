package com.example.ensign;

public abstract class Ensign implements Runnable {

    private final Thread thread;

    protected Ensign() {
        this.thread = new Thread(this);
        thread.start();
    }

    public void finishPlunder() {
        this.thread.interrupt();
    }
}
