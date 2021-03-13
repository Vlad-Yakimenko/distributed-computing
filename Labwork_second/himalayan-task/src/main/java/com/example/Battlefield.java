package com.example;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.RecursiveTask;

@Slf4j
@AllArgsConstructor
public class Battlefield extends RecursiveTask<Monk> {

    private final List<Monk> firstMonastery;
    private final List<Monk> secondMonastery;

    private final int start;
    private final int end;

    @Override
    protected Monk compute() {
        if (end - start == 0) {
            Monk firstMonk = firstMonastery.get(start);
            Monk secondMonk = secondMonastery.get(start);

            log.info("Fight: {} and {}", firstMonk, secondMonk);
            return Monk.max(firstMonk, secondMonk);
        }

        Battlefield leftHalf = new Battlefield(firstMonastery, secondMonastery, start, (end + start) / 2);
        leftHalf.fork();

        Battlefield rightHalf = new Battlefield(firstMonastery, secondMonastery, (end + start) / 2 + 1, end);
        rightHalf.fork();

        Monk leftHalfWinner = leftHalf.join();
        Monk rightHalfWinner = rightHalf.join();
        log.info("Fight: {} and {}", leftHalfWinner, rightHalfWinner);

        return Monk.max(leftHalfWinner, rightHalfWinner);
    }
}
