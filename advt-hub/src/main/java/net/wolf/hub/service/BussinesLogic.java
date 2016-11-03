/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

import net.wolf.hub.util.AccurateSleep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Наш сервис, выполняющий бизнес работу
 * @author Volchkov Andrey
 */
@Service
public class BussinesLogic {
    
    private long bussinesLogicTime;

    @Value("${timing.bussines}")
    public void setBussinesLogicTime(long bussinesLogicTime) {
        this.bussinesLogicTime = bussinesLogicTime;
    }
        
    public void doLogic() {
        try {
            //Thread.sleep(bussinesLogicTime);
            AccurateSleep.sleep(bussinesLogicTime);
        } catch (InterruptedException ex) {
            //а кто это сделал? о_О
        }
    }
}
