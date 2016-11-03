/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.adshub.util;

import java.util.concurrent.ThreadLocalRandom;
import net.wolf.hub.util.AccurateSleep;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Volchkov Andrey 
 */
public class AccurateSleepTest {
    
    public AccurateSleepTest() {
    }
    
    /**
     * Test of sleep method, of class AccurateSleep.
     */
    @Test
    public void testSleep() throws Exception {
        System.out.println("sleep");
        long milisDuration = 25L;

        for (int i=0; i < 100; i++) {
            accurateSleep(milisDuration);
            sleep(milisDuration);
        }
    }
    
    public void accurateSleep(long millis) throws InterruptedException {
        long startSleep = System.nanoTime();
        AccurateSleep.sleep(millis);
        long elapsed = System.nanoTime() - startSleep;
        System.out.printf("AccurateSleep.sleep on %d milis real take %d milis. %n", millis, TimeUnit.NANOSECONDS.toMillis(elapsed));
    }
    
    public void sleep( long millis) throws InterruptedException {
        long startSleep = System.nanoTime();
        Thread.sleep(millis);
        long elapsed = System.nanoTime() - startSleep;
        System.out.printf("Thread.sleep on %d milis real take %d milis. %n", millis, TimeUnit.NANOSECONDS.toMillis(elapsed));
    }
}
