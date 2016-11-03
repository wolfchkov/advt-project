/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.serv.util;

import java.util.concurrent.TimeUnit;

/**
 * Утлитираный класс для точного "засыпния" потока
 * @author Volchkov Andrey
 */
public class AccurateSleep {

    private static long SLEEP_THRESHOLD = TimeUnit.MILLISECONDS.toNanos(10);
    private static long SPIN_YIEL_THRESHOLD = TimeUnit.MILLISECONDS.toNanos(1);

    /**
     * За счет того, что ближе к концу периода "сна" начинаем спинится, точность 
     * очень сильно возрастает. 
     * Грузит проц!
     * На линухе шедуллер ядра получше виндового,по этому можно SLEEP_THRESHOLD уменьшить
    */
    public static void sleep(long milisDuration) throws InterruptedException {
        long timeLeft = TimeUnit.MILLISECONDS.toNanos(milisDuration);
        final long end = System.nanoTime() + timeLeft;
        do {

            if (timeLeft > SLEEP_THRESHOLD) {
                Thread.sleep(1);
            } else if (timeLeft > SPIN_YIEL_THRESHOLD) {
                Thread.yield();
            }

            timeLeft = end - System.nanoTime();

            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

        } while (timeLeft > 0);

    }
}
