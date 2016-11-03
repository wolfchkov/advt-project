/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.hub.service;

/**
 *
 * @author Volchkov Andrey 
 */
public class AsyncRequestNotFound extends Exception{
 
    private final Long ticket;

    public AsyncRequestNotFound(Long ticket) {
        super("Async request with ticket " + ticket + " not found!");
        this.ticket = ticket;        
    }

    public Long getTicket() {
        return ticket;
    }
    
}
