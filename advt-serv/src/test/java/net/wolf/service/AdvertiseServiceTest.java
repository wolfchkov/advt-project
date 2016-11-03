/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.service;

import org.apache.commons.math3.distribution.ParetoDistribution;
import org.junit.Test;

/**
 *
 * @author Volchkov Andrey 
 */
public class AdvertiseServiceTest {
    
    public AdvertiseServiceTest() {
    }
    

    /**
     * 
     */
    @Test
    public void testDelayDistribution() {
        System.out.println("testDelayDistribution");
        
        ParetoDistribution distribution = new ParetoDistribution(20d, 1d);
        for (int i = 0; i < 1000; ++i) {
            System.out.println("distr => " + distribution.sample());
        }
    }
}
