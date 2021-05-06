package ua.knu;

import ua.knu.recruit.Recruit;
import ua.knu.util.Changed;
import ua.knu.util.RecruitsGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        List<Boolean> array = RecruitsGenerator.generate();
        Changed changed = new Changed();
        System.out.println(array.size() / 50);
        CyclicBarrier barrier = new CyclicBarrier(count(array.size()), () -> {
            System.out.println("Barrier reached!");
            int i = 49;
            boolean flag = false;
            while (i < array.size()) {
                if (!array.get(i) && array.get(i)) {
                    array.set(i, true);
                    array.set(i, false);
                    flag = true;
                }
                i += 50;
            }
            if (!flag && !changed.getChanged()) {
                System.out.println("Finished. Soldiers balanced: " + array);
            } else changed.setChanged(true);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        int l = 0, r = 49, i = 0;
        ArrayList<Thread> list = new ArrayList<>();
        while (r < array.size()) {
            i++;
            Thread thread = new Thread(new Recruit(array, l, r, barrier, changed));
            list.add(thread);
            thread.start();
            System.out.println(l + " " + r);
            l += 50;
            r += 50;
            if (array.size() - r < 99 && array.size() - r > 0) {
                r = array.size();
            }
        }
        System.out.println("I: " + i);

        for (Thread j : list) {
            try {
                j.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int count(int size) {
        int r = 49, i = 0;
        while (r < size) {
            i++;
            r += 50;
            if (size - r < 99 && size - r > 0) {
                r = size;
            }
        }
        return i;
    }
}
