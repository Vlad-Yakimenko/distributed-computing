package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class Main {

    public static void main(String[] args) {
        final int monksNumber = 30;

        List<Monk> firstMonastery = new ArrayList<>();
        List<Monk> secondMonastery = new ArrayList<>();

        for (int i = 0; i < monksNumber; i++) {
            firstMonastery.add(new Monk("UN"));
            secondMonastery.add(new Monk("IN"));
        }

        Battlefield battlefield = new Battlefield(firstMonastery, secondMonastery, 0, monksNumber - 1);
        ForkJoinPool pool = new ForkJoinPool();
        Monk winner = pool.invoke(battlefield);
        log.info("Winner: {}", winner);
    }
}
