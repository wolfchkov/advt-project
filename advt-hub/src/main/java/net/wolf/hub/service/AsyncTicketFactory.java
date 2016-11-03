/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

/**
 *
 * @author Volchkov Andrey
 */
@Component
public class AsyncTicketFactory {
    
    private final AtomicLong atomicLong;

    public AsyncTicketFactory() {
        this.atomicLong = new AtomicLong(1);
    }
    
    public Long getNextTicket() {
        return atomicLong.getAndIncrement();
    }
    
}
